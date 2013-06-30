package webServer;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import model.Filme;
import util.EstrategiaExclusaoJSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.FilmeDAO;

@Path("/filme")
public class WSFilme {

	@GET
	@Produces("application/json")
	public String retornarTodosOsFilmes(){
		List<Filme> filmes = FilmeDAO.listarTodosOsFilmes();
		Gson gson = new GsonBuilder().setExclusionStrategies( new EstrategiaExclusaoJSON() ).create();
   	 	String json = gson.toJson(filmes);
   	 	return json;
	}
	
	@GET
	@Path("/{idFilme}")
	@Produces("application/json")
	public String retornarUsuarioPorId(@PathParam("idFilme") String idFilme){
		Filme filme = FilmeDAO.retornarFilme(idFilme);
		Gson gson = new GsonBuilder().setExclusionStrategies( new EstrategiaExclusaoJSON() ).create();
		String json = gson.toJson(filme);
		return json;
	}
	
	@POST
	@Consumes("application/json")
    public void adicionar(Filme filme){
		FilmeDAO.adicionar(filme);
	}
	
	@DELETE
	@Path("/{idFilme}")
	@Produces("application/json")
	public void remove(@PathParam("idFilme") String idFilme){
		FilmeDAO.remover(idFilme);
	}

	@PUT
	@Consumes("application/json")
    @Produces("application/json")
	public Filme atualizar(Filme filme){
		return FilmeDAO.atualizar(filme);
	}
}

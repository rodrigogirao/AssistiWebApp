package webServer;

import java.io.IOException;
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
import model.Usuario;
import util.ApiKey;
import util.EstrategiaExclusaoJSON;
import util.FilmeAPI;
import util.RequisicaoHTTP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.FilmeDAO;
import dao.UsuarioDAO;

@Path("/filme")
public class WSFilme {
	
	private static final String DESCRICAO_FILME = "http://api.themoviedb.org/3/movie/";

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
	public String retornarFilmePorId(@PathParam("idFilme") String idFilme){
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
	
	@GET @Path("/usuario/{idUsuario}")
	@Produces("application/json")
	public String retornarFilmesDoUsuario(@PathParam("idUsuario") String idUsuario){
		List<Filme> filmes = FilmeDAO.retornarFilmesDeUmUsuario(idUsuario);
		Gson gson = new GsonBuilder().setExclusionStrategies( new EstrategiaExclusaoJSON() ).create();
   	 	String json = gson.toJson(filmes);
   	 	return json;
	}
	
	@GET @Path("/{idFilme}/usuario/{idUsuario}")
	@Produces("application/json")
	public void adicionarFilmeAoUsuario(@PathParam("idFilme") String idFilme, @PathParam("idUsuario") String idUsuario) throws IOException{
		Filme filme = FilmeDAO.retornarFilme(idFilme);
		if(filme == null){
			RequisicaoHTTP http = new RequisicaoHTTP();
			String retornoJson = "";
			
				retornoJson = http.getUrl(DESCRICAO_FILME+idFilme, ApiKey.getApikey());
				System.out.println(retornoJson);
			
			Gson gson = new GsonBuilder().setExclusionStrategies( new EstrategiaExclusaoJSON() ).create();
			FilmeAPI filmeAPI = gson.fromJson(retornoJson, FilmeAPI.class);
			
			Filme filmeNovo = new Filme();
			filmeNovo.setId(filmeAPI.getId());
			filmeNovo.setNome(filmeAPI.getOriginal_title());
			filmeNovo.setSinopse(filmeAPI.getOverview());
			filmeNovo.setDataDeLancamento(filmeAPI.getRelease_date());
			filmeNovo.setPontuacao(filmeAPI.getPopularity());
			
			FilmeDAO.adicionar(filmeNovo);
			
			System.out.println(filmeAPI);			
		}
		FilmeDAO.adicionarFilmeAoUsuario(idFilme, idUsuario);
	}
}

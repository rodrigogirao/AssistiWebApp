package webServer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.hibernate.exception.ConstraintViolationException;

import model.Filme;
import model.Filme_Usuario;
import model.Usuario;
import util.ApiKey;
import util.Blob;
import util.EstrategiaExclusaoJSON;
import util.FilmeAPI;
import util.RequisicaoHTTP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.windowsazure.services.core.storage.StorageException;

import dao.FilmeDAO;
import dao.FilmeUsuarioDAO;
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
	
	@POST @Path("/{idFilme}/usuario/{idUsuario}")
	@Produces("application/json")
	public void adicionarFilmeAoUsuario(@PathParam("idFilme") String idFilme, @PathParam("idUsuario") String idUsuario) throws IOException, InvalidKeyException, URISyntaxException, StorageException{
		try{
			Filme filme = FilmeDAO.retornarFilme(idFilme);
			if(filme == null){
				RequisicaoHTTP http = new RequisicaoHTTP();
				String retornoJson = "";
				
					retornoJson = http.getUrl(DESCRICAO_FILME+idFilme, ApiKey.getApikey()+"&language=pt");
					System.out.println(retornoJson);
				
				Gson gson = new GsonBuilder().setExclusionStrategies( new EstrategiaExclusaoJSON() ).create();
				FilmeAPI filmeAPI = gson.fromJson(retornoJson, FilmeAPI.class);
				
				Filme filmeNovo = new Filme();
				filmeNovo.setId(filmeAPI.getId());
				filmeNovo.setNome(filmeAPI.getOriginal_title());
				filmeNovo.setSinopse(filmeAPI.getOverview());
				filmeNovo.setDataDeLancamento(filmeAPI.getRelease_date());
				filmeNovo.setPontuacao(filmeAPI.getPopularity());
				//SO PRA TESTAR MUDAR DEPOIS
				filmeNovo.setClassificacao(filmeAPI.getPoster_path());
				
				FilmeDAO.adicionar(filmeNovo);
				
				Blob blob = new Blob();
				
				String caminho = filmeAPI.getPoster_path().substring(0);
				System.out.println(caminho);
				
				//blob.upload("pequeno", "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w92/", caminho);
				//blob.upload("medio", "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w500/", caminho);
				//blob.upload("grande", "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/original/", caminho);
				
				System.out.println(filmeAPI);
			}
			FilmeDAO.adicionarFilmeAoUsuario(idFilme, idUsuario);
			System.out.println("Filme adicionado");
		}catch (ConstraintViolationException e) {
			System.out.println("Nada");
		}
	}
	
	@DELETE @Path("/{idFilme}/usuario/{idUsuario}")
	public void deletarFilmeDoUsuario(@PathParam("idFilme") String idFilme, @PathParam("idUsuario") String idUsuario){
		FilmeDAO.removerFilmeDoUsuario(idFilme, idUsuario);
		System.out.println("Filme removido");
	}
	@GET @Path("/usuario/{idUsuario}/seguindo/{idFilme}")
	public String verificarUsuarioSegueFilme(@PathParam("idUsuario") String idUsuario,@PathParam("idFilme") String idFilme){
		Filme_Usuario filmeUsuario = FilmeUsuarioDAO.retornarFilmeUsuario(idFilme, idUsuario);
		if (filmeUsuario==null){
			return "false";
		}else{
			return "true";
		}
	}
	
	
		
}

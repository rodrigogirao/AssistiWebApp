package webServer;


import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import util.ApiKey;
import util.EstrategiaExclusaoJSON;
import util.FilmeAPI;
import util.RequisicaoHTTP;
import util.ResultadosAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/feed")
public class WSFeed {
	
	private static final String FILMES_POPULARES = "http://api.themoviedb.org/3/movie/popular";
	private static final String DESCRICAO_FILME = "http://api.themoviedb.org/3/movie/";
	private static final String FILMES_EM_CARTAZ = "http://api.themoviedb.org/3/movie/now_playing";
	private static final String PROXIMOS_LANCAMENTOS = "http://api.themoviedb.org/3/movie/upcoming";
	private static final String PESQUISAR_NOME_FILME = "http://api.themoviedb.org/3/search/movie";
	
	@GET
	@Produces("application/json")
	public String listarFilmesPopulares() throws IOException{
		RequisicaoHTTP http = new RequisicaoHTTP();
        String retornoJson = http.getUrl(FILMES_POPULARES, ApiKey.getApikey());
 
        return retornoJson;
	}
	
	@GET
	@Path("/filme/{idFilme}")
	@Produces("application/json")
	public String retornarFilmePorId(@PathParam("idFilme") String idFilme) throws IOException{
		
		RequisicaoHTTP http = new RequisicaoHTTP();
		String retornoJson = http.getUrl(DESCRICAO_FILME+idFilme, ApiKey.getApikey());
		
		System.out.println(retornoJson);
		
		return retornoJson;
	}

	@GET
	@Path("/cartaz")
	@Produces("application/json")
	public String listarFilmesEmCartaz() throws IOException{
		RequisicaoHTTP http = new RequisicaoHTTP();
		String retornoJson = http.getUrl(FILMES_EM_CARTAZ, ApiKey.getApikey());
				
		return retornoJson;
	}
	
	@GET
	@Path("/lancamentos")
	@Produces("application/json")
	public String listarFilmesProximosLancamentos() throws IOException{
		RequisicaoHTTP http = new RequisicaoHTTP();
		String retornoJson = http.getUrl(PROXIMOS_LANCAMENTOS, ApiKey.getApikey());
		
		return retornoJson;
	}
	
	@GET
	@Path("/pesquisa/{pesquisa}")
	@Produces("application/json")
	public String pesquisarPorFilme(@PathParam("pesquisa") String pesquisa) throws IOException{
		RequisicaoHTTP http = new RequisicaoHTTP();
        String retornoJson = http.getUrl(PESQUISAR_NOME_FILME, ApiKey.getApikey()+"&query="+pesquisa);
        return retornoJson;
	}
}

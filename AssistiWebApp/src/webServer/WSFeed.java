package webServer;


import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import util.ApiKey;
import util.RequisicaoHTTP;

@Path("/feed")
public class WSFeed {
	
	private static final String FILMES_POPULARES = "http://api.themoviedb.org/3/movie/popular";
	private static final String DESCRICAO_FILME = "http://api.themoviedb.org/3/movie/";
	
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

}

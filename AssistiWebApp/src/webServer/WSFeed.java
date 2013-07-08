package webServer;


import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import util.ApiKey;
import util.RequisicaoHTTP;

@Path("/feed")
public class WSFeed {
	
	private static final String FILMES_POPULARES = "http://api.themoviedb.org/3/movie/popular";
	
	@GET
	@Produces("application/json")
	public String listarFilmesPopulares() throws IOException{
		RequisicaoHTTP http = new RequisicaoHTTP();
        String retornoJson = http.getUrl(FILMES_POPULARES, ApiKey.getApikey());
 
        return retornoJson;
	}

}

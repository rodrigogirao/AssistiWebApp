package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class RequisicaoHTTP {
	
	public String getUrl(String url, String chave) throws IOException {
	      URL api = new URL(url+"?api_key="+chave);
	      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(api.openStream()));
	 
	      String retornoJson;
	 
	      StringBuilder builder = new StringBuilder();
	      while ((retornoJson = bufferedReader.readLine()) != null)
	        builder.append(retornoJson);
	 
	      bufferedReader.close();
	 
	      return builder.toString();
	    }

}

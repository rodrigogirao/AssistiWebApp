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

import util.EstrategiaExclusaoJSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Usuario;

import dao.UsuarioDAO;

@Path("/usuario")
public class WSUsuario {
	
	@GET
	@Produces("application/json")
	public String retornarTodosUsuarios(){
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = (List<Usuario>) UsuarioDAO.listarTodosOsUsuarios();
		Gson gson = new GsonBuilder().setExclusionStrategies( new EstrategiaExclusaoJSON() ).create();
   	 	String json = gson.toJson(usuarios);
   	 	return json;
	}
	
	@GET
	@Path("/{idUsuario}")
	@Produces("application/json")
	public String retornarUsuarioPorId(@PathParam("idUsuario") String idUsuario){
		Usuario usuario = UsuarioDAO.retornarUsuario(idUsuario);
		Gson gson = new GsonBuilder().setExclusionStrategies( new EstrategiaExclusaoJSON() ).create();
		String json = gson.toJson(usuario);
		return json;
	}
	
	@POST
	@Consumes("application/json")
    public void adicionar(Usuario usuario){
		UsuarioDAO.adicionar(usuario);
	}
	
	@DELETE
	@Path("/{idUsuario}")
	@Produces("application/json")
	public void remove(@PathParam("idUsuario") String idUsuario){
		UsuarioDAO.remover(idUsuario);
	}

	@PUT
	@Consumes("application/json")
    @Produces("application/json")
	public Usuario atualizar(Usuario usuario){
		return UsuarioDAO.atualizar(usuario);
	}
}

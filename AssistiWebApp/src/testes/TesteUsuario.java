package testes;

import static org.junit.Assert.*;

import model.Usuario;

import org.junit.Test;

import dao.UsuarioDAO;

public class TesteUsuario {

	@Test
	public void cadastrarUsuarioERetornarUsuarioCadastrado() {
		
		Usuario usuario = new Usuario();
		usuario.setNome("Leticia Fernandes");
		usuario.setLogin("leticiamara");
		usuario.setSenha("1234");
		usuario.setEmail("leticiamara.fernandes@gmail.com");
		
		assertTrue(UsuarioDAO.adicionar(usuario));
	}
	
	@Test
	public void listarUsuarioCadastradoNoBanco(){
		
		Usuario usuario = new Usuario();
		usuario.setNome("Rodrigo Girao");
		usuario.setLogin("rodrigogirao");
		usuario.setSenha("1234");
		usuario.setEmail("rodrigogirao7@gmail.com");
		
		UsuarioDAO.adicionar(usuario);
		Usuario usuarioCarregado = UsuarioDAO.retornarUsuario(usuario.getId()+"");
		
		assertEquals(usuarioCarregado.getLogin(), usuario.getLogin());
		
	}

}

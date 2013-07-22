package dao;

import java.util.List;

import model.Usuario;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import persistencia.PreparaSessao;

public class UsuarioDAO {
	
	private static Session sessao;

	@SuppressWarnings("finally")
	public static boolean adicionar(Usuario usuario){

		boolean inserir = false;
		sessao = (Session) PreparaSessao.pegarSessao();

		try{
			Transaction trasaction = sessao.beginTransaction();
			sessao.save(usuario);
			trasaction.commit();
			inserir = true;

		} catch (HibernateException e) {
			System.out.println("Excecao Hibernate: " + e.getMessage() + " :: "
					+ e.toString());
		}
		finally{
			sessao.close();
			return inserir;
		}
	}

	public static List<Usuario> listarTodosOsUsuarios() {

		sessao = PreparaSessao.pegarSessao();
		Criteria criteria = sessao.createCriteria(Usuario.class);

		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = criteria.list();
		sessao.close();
		return usuarios;
	}

	@SuppressWarnings("finally")
	public static boolean remover(String idUsuario) {

		long id = Long.parseLong(idUsuario);
		boolean deletar = false;
		sessao = (Session) PreparaSessao.pegarSessao();

		try{
			Transaction trasaction = sessao.beginTransaction();
			Usuario usuarioCarregado = (Usuario) sessao.load(Usuario.class, id);
			sessao.delete(usuarioCarregado);
			trasaction.commit();
			deletar = true;
		}catch (HibernateException e) {
			System.out.println(e);
		}finally{
			sessao.close();
			return deletar;
		}

	}

	public static Usuario atualizar(Usuario novoUsuario) {

		sessao = (Session) PreparaSessao.pegarSessao();

		Usuario usuarioCarregado = null;

		try{
			Transaction trasaction = sessao.beginTransaction();
			usuarioCarregado = (Usuario) sessao.load(Usuario.class, novoUsuario.getId());
			usuarioCarregado = novoUsuario;
			sessao.update(usuarioCarregado);
			trasaction.commit();

		}catch (HibernateException e) {
			System.out.println(e);  
		}
		sessao.close();
		return usuarioCarregado;
	}

	public static Usuario retornarUsuario(String idUsuario) {
		Usuario usuario = null;
		try{
			long id = Long.parseLong(idUsuario);
			
			sessao = PreparaSessao.pegarSessao();
			Criteria criteria = sessao.createCriteria(Usuario.class)
					.add(Restrictions.eq("id", id));
			usuario = (Usuario) criteria.uniqueResult();
			//sessao.close();
		}catch (HibernateException e) {
			// TODO: handle exception
		}
		return usuario;
		
	}

	public static Usuario retornarUsuarioPorLogin(String login) {
		
		sessao = PreparaSessao.pegarSessao();
		Criteria criteria = sessao.createCriteria(Usuario.class)
				.add(Restrictions.eq("login", login));
		Usuario usuario = (Usuario) criteria.uniqueResult();
		sessao.close();
		
		return usuario;
	}


}

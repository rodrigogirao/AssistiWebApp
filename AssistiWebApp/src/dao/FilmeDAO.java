package dao;

import java.util.ArrayList;
import java.util.List;

import model.Filme;
import model.Filme_Usuario;
import model.Usuario;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import persistencia.PreparaSessao;

public class FilmeDAO {

	private static Session sessao;

	@SuppressWarnings("finally")
	public static boolean adicionar(Filme filme){

		boolean inserir = false;
		sessao = (Session) PreparaSessao.pegarSessao();

		try{
			Transaction trasaction = sessao.beginTransaction();
			sessao.save(filme);
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

	public static List<Filme> listarTodosOsFilmes() {

		sessao = PreparaSessao.pegarSessao();
		Criteria criteria = sessao.createCriteria(Filme.class);

		@SuppressWarnings("unchecked")
		List<Filme> filmes = (List<Filme>) criteria.list();
		sessao.close();
		return filmes;
	}

	@SuppressWarnings("finally")
	public static boolean remover(String id_filme) {

		long id = Long.parseLong(id_filme);
		boolean deletar = false;
		sessao = (Session) PreparaSessao.pegarSessao();

		try{
			Transaction trasaction = sessao.beginTransaction();
			Filme filmeCarregado = (Filme) sessao.load(Filme.class, id);
			sessao.delete(filmeCarregado);
			trasaction.commit();
			deletar = true;
		}catch (HibernateException e) {
			System.out.println(e);
		}finally{
			sessao.close();
			return deletar;
		}

	}

	public static Filme atualizar(Filme novoFilme) {

		sessao = (Session) PreparaSessao.pegarSessao();

		Filme filmeCarregado = null;

		try{
			Transaction trasaction = sessao.beginTransaction();
			filmeCarregado = (Filme) sessao.load(Filme.class, novoFilme.getId());
			filmeCarregado = novoFilme;
			sessao.update(filmeCarregado);
			trasaction.commit();

		}catch (HibernateException e) {
			System.out.println(e);  
		}
		sessao.close();
		return filmeCarregado;
	}

	public static Filme retornarFilme(String idFilme) {
		
		Filme filme = null;
		
		try{
			long id = Long.parseLong(idFilme);
			sessao = PreparaSessao.pegarSessao();
			Criteria criteria = sessao.createCriteria(Filme.class)
					.add(Restrictions.eq("id", id));
			filme = (Filme) criteria.uniqueResult();
		}catch (HibernateException e) {
		}
		//sessao.close();
		return filme;
	}
	
	public static List<Filme> retornarFilmesDeUmUsuario(String idUsuario) {
        sessao = (Session) PreparaSessao.pegarSessao();
        long id = Long.parseLong(idUsuario);  

        @SuppressWarnings("unchecked")
		List<Filme_Usuario> filmesUsuario =  sessao.createCriteria(Filme_Usuario.class).createAlias("usuario", "u").add(Restrictions.eq("u.id", id)).list();
        List<Filme> filmes = new ArrayList<Filme>();
        for (Filme_Usuario filmeUsuario : filmesUsuario) {
			Filme filme = filmeUsuario.getFilme();
			filmes.add(filme);
		}
        sessao.close();
        return filmes;
	}
	
	public static void adicionarFilmeAoUsuario(String idFilme, String idUsuario){
		Filme filme = FilmeDAO.retornarFilme(idFilme);
        Usuario usuario = UsuarioDAO.retornarUsuario(idUsuario);
        
        Filme_Usuario filmeUsuario = new Filme_Usuario();
        filmeUsuario.setFilme(filme);
        filmeUsuario.setUsuario(usuario);
        String idComposto = ""+filme.getId()+""+usuario.getId();
        filmeUsuario.setId(Long.parseLong(idComposto));
        FilmeUsuarioDAO.adicionar(filmeUsuario);
	}
	
	public static void removerFilmeDoUsuario(String idFilme, String idUsuario){
		FilmeUsuarioDAO.remover(idFilme+idUsuario);
	}
	
}

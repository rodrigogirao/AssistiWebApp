package dao;

import java.util.HashSet;

import model.Filme;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

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

	public static HashSet<Filme> listarTodosOsFilmes() {

		sessao = PreparaSessao.pegarSessao();
		Criteria criteria = sessao.createCriteria(Filme.class);

		@SuppressWarnings("unchecked")
		HashSet<Filme> filmes = (HashSet<Filme>) criteria.list();
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

}

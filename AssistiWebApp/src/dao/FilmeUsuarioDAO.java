package dao;

import model.Filme_Usuario;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import persistencia.PreparaSessao;

public class FilmeUsuarioDAO {
	
	private static Session sessao;

	public static void adicionar(Filme_Usuario filmeUsuario){
		sessao = (Session) PreparaSessao.pegarSessao();
		try{
			Transaction trasaction = sessao.beginTransaction();
			sessao.save(filmeUsuario);
			trasaction.commit();
		} catch (HibernateException e) {
			System.out.println("Excecao Hibernate: " + e.getMessage() + " :: "
					+ e.toString());
		}finally{
			sessao.close();
		}
	}

	public static void remover(String idFilmeUsuario) {
		
		long id = Long.parseLong(idFilmeUsuario);
		sessao = (Session) PreparaSessao.pegarSessao();

		try{
			Transaction trasaction = sessao.beginTransaction();
			Filme_Usuario filmeCarregado = (Filme_Usuario) sessao.load(Filme_Usuario.class, id);
			sessao.delete(filmeCarregado);
			trasaction.commit();
		}catch (HibernateException e) {
			System.out.println(e);
		}finally{
			sessao.close();
		}
	}

}

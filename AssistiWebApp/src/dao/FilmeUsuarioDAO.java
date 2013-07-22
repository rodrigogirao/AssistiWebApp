package dao;

import model.Filme_Usuario;
import model.Usuario;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import com.sun.xml.internal.ws.encoding.fastinfoset.FastInfosetMIMETypes;

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
	
	@SuppressWarnings("finally")
	public static Filme_Usuario retornarFilmeUsuario(String idFilme, String idUsuario){
		String idFilmeUsuario = idFilme+idUsuario;
		long id = Long.parseLong(idFilmeUsuario);
		System.out.println("ID: "+id);
		sessao = (Session) PreparaSessao.pegarSessao();
		Filme_Usuario filmeUsuario = null;
		try{
			Transaction trasaction = sessao.beginTransaction();
			Criteria criteria = sessao.createCriteria(Filme_Usuario.class)
					.add(Restrictions.eq("id", id));
			filmeUsuario = (Filme_Usuario) criteria.uniqueResult();
			trasaction.commit();			
		}catch (HibernateException e) {
			System.out.println(e);
		}finally{
			sessao.close();
			return filmeUsuario;
		}
	}

}

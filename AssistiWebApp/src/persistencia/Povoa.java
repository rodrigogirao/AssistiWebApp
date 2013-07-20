package persistencia;

import dao.FilmeDAO;
import model.Filme;

public class Povoa {
	
	public static void main(String[] args) {
		//CriaTabelas.reiniciaBanco();
//		Filme filme = new Filme();
//		filme.setNome("Teste");
//		
//		FilmeDAO.adicionar(filme);
		
		FilmeDAO.retornarFilme("3");
		FilmeDAO.adicionarFilmeAoUsuario("3", "1");
	}

}

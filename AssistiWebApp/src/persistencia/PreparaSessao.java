package persistencia;

import org.hibernate.classic.Session;

public class PreparaSessao {

        private static Session session = CriaTabelas.prepararSessao();

        public static Session pegarSessao(){
                if ( session == null  ) {
                        session = CriaTabelas.prepararSessao(); 
                } else if ( !session.isOpen() ) {
                        session = session.getSessionFactory().openSession();
                }
                return session;
        }
}

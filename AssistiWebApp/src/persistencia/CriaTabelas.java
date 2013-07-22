package persistencia;

import java.util.ArrayList;
import java.util.List;

import model.Filme;
import model.Filme_Usuario;
import model.Usuario;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class CriaTabelas 
{
        public static void exportarEsquema(List<Class<? extends Object>> classes)
        {
                AnnotationConfiguration annotConfig = adicionaClassesConfiguracao(classes);
                
                SchemaExport se = new SchemaExport(annotConfig);
                se.create(true, true);
        }

        private static AnnotationConfiguration adicionaClassesConfiguracao(List<Class<? extends Object>> classes) 
        {
                AnnotationConfiguration annotConfig = new AnnotationConfiguration();
                
                for (Class<?> classe : classes) {
                        annotConfig.addAnnotatedClass(classe);
        }
        return annotConfig;
}

        public static Session prepararSessao() 
        {
                AnnotationConfiguration annotConfig = adicionaClassesConfiguracao(initialize());
                SessionFactory sf = annotConfig.buildSessionFactory();
                Session session = sf.openSession();
                return session;
        }

        public static void reiniciaBanco() 
        {
                exportarEsquema(initialize());
        }

         private static List<Class<? extends Object>> initialize()
        {
                List<Class<? extends Object>> classes = new ArrayList<Class<? extends Object>>();
                          
                classes.add(Filme.class);
                classes.add(Usuario.class);
                classes.add(Filme_Usuario.class);
                
                return classes;
        }
}
package cropcert.user;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import cropcert.user.farmer.FarmerModule;
import cropcert.user.signup.SignupModule;

public class UserServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		
		Injector injector = Guice.createInjector(new JerseyServletModule() {
			@Override
			protected void configureServlets() {
				
				Configuration configuration = new Configuration();
				
				try {
					for (Class<?> cls : getEntityClassesFromPackage("cropcert")) {
						configuration.addAnnotatedClass(cls);
					}
				} catch (ClassNotFoundException | IOException | URISyntaxException e) {
					e.printStackTrace();
				}
				
				configuration = configuration.configure();
				SessionFactory sessionFactory = configuration.buildSessionFactory();
				
				bind(SessionFactory.class).toInstance(sessionFactory);
				bind(ObjectMapper.class).in(Scopes.SINGLETON);
				
				serve("/*").with(GuiceContainer.class);
			}
		}, new UserModule(), new FarmerModule(), new SignupModule());
		
		return injector; 
	}
	
	private static List<Class<?>> getEntityClassesFromPackage(String packageName)
			throws ClassNotFoundException, IOException, URISyntaxException {
		List<String> classNames = getClassNamesFromPackage(packageName);
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (String className : classNames) {
			Class<?> cls = Class.forName(className);
			Annotation[] annotations = cls.getAnnotations();

			for (Annotation annotation : annotations) {
				if (annotation instanceof javax.persistence.Entity) {
					System.out.println("Mapping Entity : " + cls.getCanonicalName());
					classes.add(cls);
				}
			}
		}

		return classes;
	}

	private static ArrayList<String> getClassNamesFromPackage(final String packageName)
			throws IOException, URISyntaxException, ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ArrayList<String> names = new ArrayList<String>();

		URL packageURL = classLoader.getResource(packageName);

		URI uri = new URI(packageURL.toString());
		File folder = new File(uri.getPath());

		Files.find(Paths.get(folder.getAbsolutePath()), 999, (p, bfa) -> bfa.isRegularFile()).forEach(file -> {
			String name = file.toFile().getAbsolutePath().replaceAll(folder.getAbsolutePath() + File.separatorChar, "").replace(File.separatorChar,
					'.');
			if (name.indexOf('.') != -1) {
				name = packageName + '.' + name.substring(0, name.lastIndexOf('.'));
				names.add(name);
			}
		});

		return names;
	}

}

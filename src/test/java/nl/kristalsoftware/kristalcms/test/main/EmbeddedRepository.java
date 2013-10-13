package nl.kristalsoftware.kristalcms.test.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.jcr.ImportUUIDBehavior;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.modeshape.common.collection.Problems;
import org.modeshape.jcr.ConfigurationException;
import org.modeshape.jcr.ModeShapeEngine;
import org.modeshape.jcr.NoSuchRepositoryException;
import org.modeshape.jcr.RepositoryConfiguration;
import org.pmw.tinylog.Logger;

public class EmbeddedRepository {
	
	private ModeShapeEngine engine = null;
	private RepositoryConfiguration config = null;
	
	public EmbeddedRepository() {
		engine = new ModeShapeEngine();
	}
	
	private void config() throws Exception {
		URL url = EmbeddedRepository.class.getClassLoader().getResource("kristalcms-repository-config.json");
		Logger.info("Path: " + url.getPath());
		config = RepositoryConfiguration.read(url);
		Problems problems = config.validate();
		if (problems.hasErrors()) {
			throw new Exception();
		}

	}
	
	public boolean startEngine() {
		boolean retVal = false;
		try {
			this.config();
			engine.start();
			retVal = true;
		} catch (Exception e) {
			Logger.error(e.getMessage());
		}
		return retVal;
	}
	
	public void stopEngine() {
		engine.shutdown();
	}
	
	public Repository deploy() {
		Repository repository = null;
		try {
			repository = engine.deploy(config);
		} catch (ConfigurationException e) {
			Logger.error(e.getMessage());
		} catch (RepositoryException e) {
			Logger.error(e.getMessage());
		}
		return repository;
	}
	
	//public void undeploy(String repositoryName) throws NoSuchRepositoryException {
	//	//engine.shutdownRepository(repositoryName);
	//	engine.shutdown();
	//}

	public String getRepositoryName() {
		return config.getName();
	}
	
	public Repository getRepository(String reponame) throws NoSuchRepositoryException {
		return engine.getRepository(reponame);
	}
	
	public void importXML(Session session, String xmlFileName) {
		ClassLoader loader = this.getClass().getClassLoader();
		InputStream is = loader.getResourceAsStream(xmlFileName);
		if (is != null) {
			try {
				session.importXML("/", is, ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW);
				session.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println(xmlFileName + " not found.");
		}
	}
	
	public void exportDocumentXML(Session session, String baseNodePath, String filePath) {
		try {
			session.exportDocumentView(baseNodePath, new FileOutputStream(new File(filePath)), false, false);
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exportSystemXML(Session session, String baseNodePath, String filePath) {
		try {
			session.exportSystemView(baseNodePath, new FileOutputStream(new File(filePath)), false, false);
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

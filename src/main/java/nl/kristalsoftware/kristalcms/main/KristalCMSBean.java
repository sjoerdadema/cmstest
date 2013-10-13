package nl.kristalsoftware.kristalcms.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jcr.ImportUUIDBehavior;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.pmw.tinylog.Logger;

@ManagedBean
@ApplicationScoped
public class KristalCMSBean {

	@Resource(mappedName="java:/jcr/kristalcms")
	private javax.jcr.Repository repository;
	
	public KristalCMSBean() {
		Logger.debug("KristalCMS constructor");
	}
	
	@PostConstruct
	public void init() {
		Logger.debug(repository.getClass().getName());
		Session session = null;
		try {
			session = this.createSession();
			Node rootNode = session.getRootNode();
			if (!rootNode.hasNode("config")) {
				this.importXML(session, "config.xml");
			}
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.logout();
			}
		}
	}
	
	public Session createSession() throws LoginException, RepositoryException {
		Session session = null;
		if (repository != null) {
			session = repository.login();
		}
		else {
			throw new RepositoryException();
		}
		return session;
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
			System.out.println("The file " + xmlFileName + " not found.");
		}
	}
	
	public void exportXML(Session session) {
		try {
			//session.exportSystemView("/productgroups", new FileOutputStream(new File("/Users/sjoerd/Documents/tmp/productgroups.xml")), false, false);
			session.exportSystemView("/config", new FileOutputStream(new File("/Users/sjoerd/Documents/tmp/config.xml")), false, false);
			//session.exportSystemView("/orders", new FileOutputStream(new File("/Users/sjoerd/Documents/tmp/orders.xml")), false, false);
			//session.exportSystemView("/countries", new FileOutputStream(new File("/Users/sjoerd/Documents/tmp/dealers.xml")), false, false);
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}
	
}

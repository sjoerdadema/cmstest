package nl.kristalsoftware.kristalcms.main;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public interface BeanController<T> {

	void createData(Session session) throws PathNotFoundException, RepositoryException;

	T getData(Session session, String path) throws PathNotFoundException, RepositoryException;

}

package nl.kristalsoftware.kristalcms.main;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import nl.kristalsoftware.kristalcms.utils.JCRUtils;
import nl.kristalsoftware.kristalcms.utils.JCRUtilsImpl;

import org.pmw.tinylog.Logger;

public class BeanControllerImpl<T> implements BeanController<T> {

	private final JCRUtils<T> jcrUtils;
	
	public BeanControllerImpl(T bean) {
		jcrUtils = new JCRUtilsImpl<T>(bean);
	}
	
	public void createData(Session session) throws PathNotFoundException, RepositoryException {
		String parentPath = jcrUtils.getParentPath("self");
		if (parentPath != null) {
			Logger.debug("Parentpath: " + parentPath);
		}
		else {
			throw new PathNotFoundException();
		}
		String nodename = jcrUtils.getNodename("nodename");
		if (nodename != null) {
			Logger.debug("Nodename: " + nodename);
			Node newNode = jcrUtils.createNode(session, parentPath, nodename);
			jcrUtils.createProperties(newNode);
		}
		else {
			throw new PathNotFoundException();
		}
		
	}

	public T getData(Session session, String path) throws PathNotFoundException, RepositoryException {
		T data = null;
		Node node = jcrUtils.getNode(session, path);
		try {
			Logger.debug("Nodename: " + node.getName());
			jcrUtils.setNodename(node);
			jcrUtils.setPropertieFields(node);
			data = jcrUtils.getData();
		} catch (SecurityException e) {
			Logger.error(e.getMessage());
		} catch (NoSuchFieldException e) {
			Logger.error(e.getMessage());
		}
		return data;
	}

}

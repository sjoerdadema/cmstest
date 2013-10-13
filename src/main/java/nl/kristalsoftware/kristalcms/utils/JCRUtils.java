package nl.kristalsoftware.kristalcms.utils;

import java.lang.reflect.Field;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public interface JCRUtils<T> {

	String getParentPath(String selfLinkFieldName);

	String getNodename(String nodeFieldName);

	Node createNode(Session session, String parentPath, String nodename) throws PathNotFoundException, RepositoryException;
	
	void createNode(Session session, String parentPath, String nodename, String nodeType);
	
	void createProperties(Node node);

	void createProperty(Node node, Field f);

	Node getNode(Session session, String path) throws PathNotFoundException, RepositoryException;

	void setNodename(Node node) throws SecurityException, NoSuchFieldException, RepositoryException;

	T getData();

	void setPropertieFields(Node node);
	
	void setPropertyField(Node node, Field f) throws PathNotFoundException, RepositoryException;

}

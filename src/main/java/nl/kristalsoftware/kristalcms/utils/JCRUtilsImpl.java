package nl.kristalsoftware.kristalcms.utils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import nl.kristalsoftware.kristalcms.annotation.JcrContentProperty;
import nl.kristalsoftware.kristalcms.jaxb.Link;
import nl.kristalsoftware.kristalcms.utils.reflection.BeanReflectionUtils;
import nl.kristalsoftware.kristalcms.utils.reflection.BeanReflectionUtilsImpl;

import org.apache.commons.lang3.StringUtils;
import org.pmw.tinylog.Logger;

public class JCRUtilsImpl<T> implements JCRUtils<T> {

	private final BeanReflectionUtils utils;
	private final T bean;

	public JCRUtilsImpl(T bean) {
		this.bean = bean;
		utils = new BeanReflectionUtilsImpl();		
	}
	
	public String getParentPath(String selfLinkFieldName) {
		String parentPath = null;
		Field self;
		try {
			self = bean.getClass().getDeclaredField(selfLinkFieldName);
			Logger.debug(self.getType().getSimpleName());
			if (self != null && self.getType().getSimpleName().equals("Link")) {
				Link link = utils.getFieldValueWithGetter(self, bean);
				if (link != null) {
					parentPath = StringUtils.substringBeforeLast(link.getHref(), "/");
					Logger.debug(link.getHref() + " " + link.getRel());
				}
				else {
					Logger.error("link is null");
				}
			}
		} catch (SecurityException e) {
			Logger.error(e.getMessage());
		} catch (NoSuchFieldException e) {
			Logger.error(e.getMessage());
		}
		return parentPath;
	}

	public String getNodename(String nodeFieldName) {
		String nodename = null;
		try {
			Field nodenameField = bean.getClass().getDeclaredField(nodeFieldName);
			Logger.debug(nodenameField.getType().getSimpleName());
			if (nodenameField != null && nodenameField.getType().getSimpleName().equals("String")) {
				nodename = utils.getFieldValueWithGetter(nodenameField, bean);
			}
		} catch (SecurityException e) {
			Logger.error(e.getMessage());
		} catch (NoSuchFieldException e) {
			Logger.error(e.getMessage());
		}
		return nodename;		
	}
	
	public Node createNode(Session session, String parentPath, String nodename) throws PathNotFoundException, RepositoryException {
		Node parentNode = session.getNode(parentPath);
		return parentNode.addNode(nodename);
	}
	
	public void createNode(Session session, String parentPath, String nodename, String nodeType) {
		// TODO Auto-generated method stub
		
	}

	public void createProperties(Node node) {
		List<Field> annotatedFields = utils.getAnnotatedFields(bean, JcrContentProperty.class);
		Iterator<Field> fieldIter = annotatedFields.iterator();
		while(fieldIter.hasNext()) {
			createProperty(node, fieldIter.next());
		}
	}

	public void createProperty(Node node, Field f) {
		String value = "test";
		Logger.debug("Field: " + f.getName());
		try {
			if (f.getType().getSimpleName().equals("String")) {
				value = utils.getFieldValueWithGetter(f, bean);
				Logger.debug("Value: " + value);
			}
			node.setProperty(f.getName(), value);
		} catch (ValueFormatException e) {
			e.printStackTrace();
		} catch (VersionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPropertyField(Node node, Field f) throws PathNotFoundException, RepositoryException {
		Logger.debug("Field: " + f.getName());
		Property p = node.getProperty(f.getName());
		if (f.getType().getSimpleName().equals("String")) {
			utils.setFieldValueWithSetter(f, p.getString(), bean);
		}
	}
	
	public void createJCRProperty() {
		
	}

	public Node getNode(Session session, String path) throws PathNotFoundException, RepositoryException {
		return session.getNode(path);
	}

	public void setNodename(Node node) throws SecurityException, NoSuchFieldException, RepositoryException {
		Field f = bean.getClass().getDeclaredField("nodename");
		utils.setFieldValueWithSetter(f, node.getName(), bean);
	}

	public T getData() {
		return bean;
	}

	public void setPropertieFields(Node node) {
		List<Field> annotatedFields = utils.getAnnotatedFields(bean, JcrContentProperty.class);
		Iterator<Field> fieldIter = annotatedFields.iterator();
		while(fieldIter.hasNext()) {
			try {
				setPropertyField(node, fieldIter.next());
			} catch (PathNotFoundException e) {
				Logger.error(e.getMessage());
			} catch (RepositoryException e) {
				Logger.error(e.getMessage());
			}
		}
	}
	

}

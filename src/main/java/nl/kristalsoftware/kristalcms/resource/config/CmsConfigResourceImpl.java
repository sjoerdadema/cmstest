package nl.kristalsoftware.kristalcms.resource.config;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import nl.kristalsoftware.kristalcms.data.config.ConfigData;
import nl.kristalsoftware.kristalcms.main.BeanController;
import nl.kristalsoftware.kristalcms.main.BeanControllerImpl;
import nl.kristalsoftware.kristalcms.main.KristalCMSBean;
import nl.kristalsoftware.kristalcms.main.LinkData;

import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;
import org.jboss.resteasy.links.LinkResources;
import org.pmw.tinylog.Logger;

public class CmsConfigResourceImpl implements CmsConfigResource {

	public ConfigData getConfigData(@Context UriInfo uriInfo)
			throws PathNotFoundException, RepositoryException {
		ConfigData data = new ConfigData();
		return data;
	}

	
	/*
	@Inject
	private KristalCMSBean repoBean;
	
	public CmsConfigResourceImpl() {}
	
	@GET
	public ConfigData getConfigData(@Context UriInfo uriInfo) throws PathNotFoundException, RepositoryException {
		Logger.debug("getConfigData method with path: " + uriInfo.getPath());
		if (uriInfo.getPath().isEmpty()) {
			throw new PathNotFoundException();
		}
		ConfigData configdata = new ConfigData();
		Session session = repoBean.createSession();
		Node node = session.getNode(uriInfo.getPath());
		NodeIterator nodeIter = node.getNodes();
		while (nodeIter.hasNext()) {
			String path = nodeIter.nextNode().getPath();
			if (!path.startsWith("/jcr")) {
				Logger.debug(path);
				BeanController<LinkData> beanController = new BeanControllerImpl<LinkData>(new LinkData());
				LinkData linkdata = beanController.getData(session, path);
				Logger.debug("LinkData nodename: " + linkdata.getNodename());
				configdata.getLinkList().add(linkdata);
			}
		}
		return configdata;
	}
	*/
	
}

package nl.kristalsoftware.kristalcms.resource.config;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import nl.kristalsoftware.kristalcms.data.config.ConfigData;

import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;

@Produces({"application/json", "application/xml"})
@Consumes({"application/json", "application/xml"})
@Path("/config")
public interface CmsConfigResource {

	@AddLinks
	@LinkResource
	@GET
	public ConfigData getConfigData(@Context UriInfo uriInfo) throws PathNotFoundException, RepositoryException;
	
}

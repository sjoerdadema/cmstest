package nl.kristalsoftware.kristalcms.resource.config;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import nl.kristalsoftware.kristalcms.data.config.ConfigData;
import nl.kristalsoftware.kristalcms.data.config.TemplateData;

import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;
import org.jboss.resteasy.links.LinkResources;

@Produces({"application/json", "application/xml"})
@Consumes({"application/json", "application/xml"})
@Path("/config")
public interface CmsTemplatesResource {

	@AddLinks
	@LinkResources({
		@LinkResource(value = ConfigData.class, rel = "templates"),
		//@LinkResource(value = TemplateData.class)
	})
	@GET
	@Path("templates")
	public Collection<TemplateData> getTemplates(@Context UriInfo uriInfo);
	
	@AddLinks
	@LinkResource
	@GET
	@Path("templates/{id}")
	public TemplateData getTemplate(@Context UriInfo uriInfo, @PathParam("id") String id);
	
	/*
	@LinkResource
	@POST
	@Path("test/templates")
	public void addTemplateData(@Context UriInfo uriInfo, TemplateData data);
	*/
}

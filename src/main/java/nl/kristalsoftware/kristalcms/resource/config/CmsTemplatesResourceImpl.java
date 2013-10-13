package nl.kristalsoftware.kristalcms.resource.config;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import nl.kristalsoftware.kristalcms.data.config.TemplateData;

public class CmsTemplatesResourceImpl implements CmsTemplatesResource {

	public Collection<TemplateData> getTemplates(@Context UriInfo uriInfo) {
		TemplateData homeTemplate = new TemplateData();
		homeTemplate.setNodename("home");
		TemplateData defaultTemplate = new TemplateData();
		defaultTemplate.setNodename("default");
		Collection<TemplateData> templateList = new ArrayList<TemplateData>();
		templateList.add(homeTemplate);
		templateList.add(defaultTemplate);
		return templateList;
	}

	public TemplateData getTemplate(@Context UriInfo uriInfo,
			@PathParam("id") String id) {
		TemplateData templ = new TemplateData();
		templ.setNodename(id);
		return templ;
	}

}

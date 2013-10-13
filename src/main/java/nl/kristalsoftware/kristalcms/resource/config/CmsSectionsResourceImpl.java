package nl.kristalsoftware.kristalcms.resource.config;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import nl.kristalsoftware.kristalcms.data.config.SectionData;

public class CmsSectionsResourceImpl implements CmsSectionsResource {

	public Collection<SectionData> getSections(@Context UriInfo uriInfo) {
		SectionData headerSection = new SectionData();
		headerSection.setNodename("header");
		SectionData footerSection = new SectionData();
		footerSection.setNodename("footer");
		Collection<SectionData> sectionList = new ArrayList<SectionData>();
		sectionList.add(headerSection);
		sectionList.add(footerSection);
		return sectionList;
	}

	public SectionData getSection(@Context UriInfo uriInfo,
			@PathParam("id") String id) {
		SectionData section = new SectionData();
		section.setNodename(id);
		return section;
	}

}

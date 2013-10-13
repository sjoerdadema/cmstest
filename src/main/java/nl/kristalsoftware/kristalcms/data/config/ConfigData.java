package nl.kristalsoftware.kristalcms.data.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.providers.NoJackson;
import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;
import org.jboss.resteasy.links.RESTServiceDiscovery;

@NoJackson
@Mapped(namespaceMap = @XmlNsMap(jsonName = "atom", namespace = "http://www.w3.org/2005/Atom"))
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="config")
public class ConfigData {

	//@XmlID
    private final String nodename = "config";

    //@XmlElement(name = "atom:link")
    
	@XmlElementRef
	private RESTServiceDiscovery rest;

	public String getNodename() {
		return nodename;
	}

	/*
    private final String nodename = "config";

    @XmlElement(name = "link")
    private List<LinkData> linkList = new ArrayList<LinkData>();
    
	@XmlElementRef
	private RESTServiceDiscovery rest;

	public String getNodename() {
		return nodename;
	}

	public List<LinkData> getLinkList() {
		return linkList;
	}
	
	public void setLinkList(List<LinkData> linkList) {
		this.linkList = linkList;
	}
	*/
	
}

package nl.kristalsoftware.kristalcms.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
@XmlRootElement(name="link")
public class LinkData {

	@XmlID
	@XmlAttribute(name = "id")
	private String nodename = "";

	@XmlElementRef
	private RESTServiceDiscovery rest;

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	
}

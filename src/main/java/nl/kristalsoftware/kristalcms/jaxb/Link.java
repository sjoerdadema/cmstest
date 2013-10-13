package nl.kristalsoftware.kristalcms.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"rel", "href"})
@XmlRootElement(name="link")
public class Link {
	
	@XmlAttribute(required = true)
	private String rel = "";
	
	@XmlAttribute(required = true)
	private String href = "";
	
	public Link() {
	}
	
	public Link(String rel) {
		this.rel = rel;
	}

	public Link(String rel, String href) {
		this.rel = rel;
		this.href = href;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}	

}

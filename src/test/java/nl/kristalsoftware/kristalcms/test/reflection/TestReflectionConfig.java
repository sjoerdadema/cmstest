package nl.kristalsoftware.kristalcms.test.reflection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import nl.kristalsoftware.kristalcms.data.config.ConfigData;
import nl.kristalsoftware.kristalcms.data.config.TemplatesData;
import nl.kristalsoftware.kristalcms.test.main.EmbeddedRepository;
import nl.kristalsoftware.kristalcms.utils.JCRUtils;
import nl.kristalsoftware.kristalcms.utils.JCRUtilsImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.modeshape.jcr.NoSuchRepositoryException;
import org.pmw.tinylog.Logger;

public class TestReflectionConfig {

	//private final String REPO_NAME="My Repository";
	private final EmbeddedRepository embeddedRepository;
	private Repository repository = null;
	private Session session = null;
	
	public TestReflectionConfig() {
		super();
		embeddedRepository = new EmbeddedRepository();
		//if (embeddedRepository.startEngine()) {
		//	repository = embeddedRepository.deploy();
		//}
	}
	
	@Before
	public void before() throws LoginException, NoSuchWorkspaceException, RepositoryException {
		if (embeddedRepository.startEngine()) {
			repository = embeddedRepository.deploy();
			if (repository != null) {
				session = repository.login("default");
				String repositoryName = embeddedRepository.getRepositoryName();
				Logger.info(repositoryName);
				embeddedRepository.importXML(session, "testconfig.xml");
			}
			else {
				throw new RepositoryException();
			}
		}
	}
	
	@After
	public void after() throws NoSuchRepositoryException {
		embeddedRepository.stopEngine();
	}
	
	@Test
	public void configContainerShouldExist() throws Exception {
		Node node = session.getNode("/config");
		assertNotNull("node is null", node);
		assertThat("nodename is not config", node.getName(), is("config"));
	}

	@Test
	public void findAndValidateConfigNode() throws PathNotFoundException, RepositoryException, SecurityException, NoSuchFieldException {
		ConfigData configdata = new ConfigData();
		JCRUtils<ConfigData> jcrUtils = new JCRUtilsImpl<ConfigData>(configdata);
		Node configNode = jcrUtils.getNode(session, "/config");
		assertNotNull("configNode is null", configNode);
		jcrUtils.setNodename(configNode);
		ConfigData readData = jcrUtils.getData();
		assertNotNull("returned ConfigData object is null", readData);
		assertThat("nodename is not config", readData.getNodename(), is("config"));
	}
	
	@Test
	public void findAndValidateTemplatesNode() throws PathNotFoundException, RepositoryException {
		TemplatesData templatesdata = new TemplatesData();
		JCRUtils<TemplatesData> jcrUtils = new JCRUtilsImpl<TemplatesData>(templatesdata);
		Node templatesNode = jcrUtils.getNode(session, "/config/templates");
		assertNotNull("templatesNode is null", templatesNode);
		TemplatesData readData = jcrUtils.getData();
		assertNotNull("returned TemplatesData object is null", readData);
		assertThat("nodename is not templates", readData.getNodename(), is("templates"));
	}
	
	/*
	@Test
	public void createNewProductAjax_doel() throws Exception {
		// create data
		TestData testBean = new TestData("10.10.10.10", "Ajax doel");
		BeanController<TestData> beanController = new BeanControllerImpl<TestData>(testBean);
		beanController.createData(session);
		session.save();
		embeddedRepository.exportSystemXML(session, "/products", "/Users/sjoerd/tmp/testexport.xml");
		// read the new data
		beanController = new BeanControllerImpl<TestData>(new TestData());
		testBean = beanController.getData(session, "/products/10.10.10.10");
		assertNotNull("testbean is null", testBean);
		assertThat("nodename is not '10.10.10.10'", testBean.getNodename(), is("10.10.10.10"));
		assertThat("description is not Ajax doel", testBean.getDescription(), is("Ajax doel"));
	}
	*/
	
}

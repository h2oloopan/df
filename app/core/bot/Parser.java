package core.bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import play.Logger;


public class Parser {
	public HashMap<String, Topic> parse(String path) throws Exception {
		HashMap<String, Topic> result = new HashMap<String, Topic>();
		
		//read xml
		File confs = new File(new File(path), "confs");
		String[] files = confs.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				File f = new File(current, name);
				return f.isFile() && FilenameUtils.getExtension(f.getAbsolutePath()).equals("aiml");
			}
		});
		for (int i = 0; i < files.length; i++) {
			File aiml = new File(confs, files[i]);
			Logger.info("Loading aiml file: " + aiml.getCanonicalPath());
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			InputStream stream = new FileInputStream(aiml);
			Reader reader = new InputStreamReader(stream, "UTF-8");
			
			InputSource is = new InputSource(reader);
			Document doc = builder.parse(is);
			Node root = doc.getFirstChild();
			Topic topic = new Topic();
			resolve(topic, root, null);
			Logger.info(topic.toString());
			result.put(topic.getName(), topic);
		}
		return result;
	}
	
	private void resolve(Topic t, Node n, Category c) throws Exception {
		//Logger.info(n.getNodeName());
		//Logger.info(n.getTextContent().trim());
		//Logger.info(n.getParentNode().getNodeName().toLowerCase());
		switch (n.getNodeName().toLowerCase()) {
			case "topic":
				if (n.getParentNode().getNodeName().toLowerCase().equals("aiml")) {
					t.setName(n.getAttributes().getNamedItem("name").getTextContent().trim());
				} else {
					throw new Exception("A topic node must be directly under aiml node");
				}
				break;
			case "category":
				if (!(n.getParentNode().getNodeName().toLowerCase().equals("aiml") || n.getParentNode().getNodeName().toLowerCase().equals("topic"))) {
					throw new Exception("A category node must be directly under aiml node or topic node");
				} else {
					c = new Category();
					t.addCategory(c);
				}
				break;
			case "pattern":
				if (n.getParentNode().getNodeName().toLowerCase().equals("category")) {
					Pattern pattern = new Pattern(n.getTextContent().trim());
					c.setPattern(pattern);
				} else {
					throw new Exception("A pattern node must be directly under category node");
				}
				break;
			case "that":
				if (n.getParentNode().getNodeName().toLowerCase().equals("category")) {
					That that = new That(n.getTextContent().trim());
					c.setThat(that);
				} else {
					throw new Exception("A that node must be directly under category node");
				}
				break;
			case "template":
				if (n.getParentNode().getNodeName().toLowerCase().equals("category")) {
					Template template = new Template(n.getTextContent().trim());
					c.setTemplate(template);
				} else {
					throw new Exception("A template node must be directly under category node");
				}
				break;
			default:
				break;
				//doing nothing	
		}
		NodeList children = n.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node next = children.item(i);
			resolve(t, next, c);
		}
	}
}

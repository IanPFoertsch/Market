package production.marshalling;

import java.io.StringReader;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.Unmarshaller;

import production.entity.Post;

import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;

@ApplicationScoped
public class PostUnmarshaller {

	private Unmarshaller unmarshaller;
	
	public PostUnmarshaller() throws JAXBException {
		this.unmarshaller = JAXBContext.newInstance(Post.class).createUnmarshaller();
	}
	
	public Post unmarshall(String xml) throws JAXBException {
		
		StringReader stringReader = new StringReader(xml);
		return (Post) this.unmarshaller.unmarshal(stringReader);
	}
}

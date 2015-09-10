package production.marshalling;

import java.io.StringWriter;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import production.entity.Post;

import javax.xml.bind.JAXBContext;

@ApplicationScoped
public class PostMarshaller {

	private Marshaller marshaller;
	
	public PostMarshaller() throws JAXBException {
		this.marshaller = JAXBContext.newInstance(Post.class).createMarshaller();
	}
	
	public String marshal(Post post) throws JAXBException {
		StringWriter stringWriter = new StringWriter();
		this.marshaller.marshal(post, stringWriter);
		return stringWriter.toString();
	}
	
}

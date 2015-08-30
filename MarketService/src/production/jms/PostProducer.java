package production.jms;

import java.io.StringWriter;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import production.entity.Post;
import production.marshalling.PostMarshaller;

@ApplicationScoped
public class PostProducer {
	
	private static final String postQueueName = "java:jboss/jms/queue/postQueue";
	
	@Inject 
	private JMSContext context;
	@Resource(mappedName = postQueueName)
	private Queue postQueue;
	
	@Inject 
	private PostMarshaller postMarshaller;
	
	
	public void sendMessage(Post post) throws JAXBException {
		String marshalledPost = this.postMarshaller.marshal(post);
		context.createProducer().send(postQueue, marshalledPost);
	}
}

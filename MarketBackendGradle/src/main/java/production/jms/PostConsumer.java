package production.jms;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;

import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.xml.bind.JAXBException;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;

import production.entity.Post;
import production.marketforum.MatchingEngine;
import production.marshalling.PostUnmarshaller;

@MessageDriven(name = "PostQueueReceiver", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", 
								  propertyValue = "java:jboss/jms/queue/postQueue"),
		@ActivationConfigProperty(propertyName = "destinationType",
								  propertyValue = "javax.jms.Queue")
})
public class PostConsumer implements MessageListener {
	
	private static final String postQueueName = "java:jboss/jms/queue/postQueue";
	
	@Inject private JMSContext context;
	
	@Resource(mappedName = postQueueName)
	private Queue postQueue;
	
	
	private PostUnmarshaller postUnmarshaller;
	
	@EJB
	private MatchingEngine matchingEngine;

	
	private Logger logger;
	
	public PostConsumer() throws JAXBException {
		this.postUnmarshaller = new PostUnmarshaller();
		this.logger = Logger.getLogger(this.getClass());
		this.logger.info("PostConsumerCreated!");
	}
	
	@Override
	public void onMessage(Message message) {
		try {
			//Get the string containing the post object from the jms message
			//convert it back to a post object
			String postString = message.getBody(String.class);
			Post post = this.postUnmarshaller.unmarshall(postString);
			//send it to the matching engine
			this.matchingEngine.postListing(post);
			System.out.println(message.getBody(String.class));
			
			logger.info("Post received, unmarshalled and posted to the matchingEngine");
		} catch (JMSException | JAXBException e) {
			logger.debug(e.getMessage());
		}
		
	
		
		
		
	}
	
}

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
	
	@Inject 
	private PostUnmarshaller postUnmarshaller;
	
	@Inject
	private MatchingEngine matchingEngine;

	@Inject
	private Logger logger;
	
	
	@Override
	public void onMessage(Message message) {
		try {
			//Get the string containing the post object from the jms message
			//convert it back to a post object
			String postString = message.getBody(String.class);
			Post post = this.postUnmarshaller.unmarshall(postString);
			//send it to the matching engine
			this.matchingEngine.postListing(post);
			logger.info("Post received, unmarshalled and posted to the matchingEngine");
		} catch (JMSException | JAXBException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
	
		
		
		
	}
	
}

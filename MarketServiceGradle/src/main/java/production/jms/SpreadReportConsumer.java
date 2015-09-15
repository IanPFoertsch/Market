package production.jms;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;


@MessageDriven(name =  "SpreadReportConsumer", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", 
				  propertyValue = "java:jboss/jms/topic/spreadStatusReport"),
		@ActivationConfigProperty(propertyName = "destinationType",
				  propertyValue = "javax.jms.Topic")
})
public class SpreadReportConsumer implements MessageListener {
	
	private static final String spreadReportName = "java:jboss/jms/topic/spreadStatusReport";
	
	@Inject 
	private JMSContext context;
	
	@Resource(mappedName = spreadReportName)
	private Topic spreadReportTopic;
	
	
	@Override
	public void onMessage(Message message) {
		try{
			//verify that we are indeed receiving messages
			System.out.println("Message received on the spread report topic");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}

package production.jms;

import java.util.ArrayList;
import java.util.LinkedList;

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

import production.entity.MarketResolutionReport;
import production.entity.MarketResolutionReportWrapper;
import production.entity.Post;
import production.marketforum.MatchingEngine;
import production.marshalling.MarshallingWrapper;
import production.marshalling.PostUnmarshaller;

@MessageDriven(name = "PostQueueReceiver", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", 
								  propertyValue = "java:jboss/jms/queue/postQueue"),
		@ActivationConfigProperty(propertyName = "destinationType",
								  propertyValue = "javax.jms.Queue")
})
public class PostConsumer implements MessageListener {
	
	private static final String postQueueName = "java:jboss/jms/queue/postQueue";
	
	private static final String reportQueueName = "java:jboss/jms/queue/marketResolutionReportQueue";
	
	@Inject private JMSContext context;
	
	
	
	@Resource(mappedName = postQueueName)
	private Queue postQueue;
	
	@Resource(mappedName = reportQueueName)
	private Queue reportQueue;
	
	private PostUnmarshaller postUnmarshaller;
	private MarshallingWrapper<MarketResolutionReportWrapper> marshallingWrapper;
	
	@EJB
	private MatchingEngine matchingEngine;

	
	
	private Logger logger;
	
	public PostConsumer() throws JAXBException {
		this.postUnmarshaller = new PostUnmarshaller();
		this.logger = Logger.getLogger(this.getClass());
		//this.logger.info("PostConsumerCreated!");
		this.marshallingWrapper = new MarshallingWrapper<MarketResolutionReportWrapper>(MarketResolutionReportWrapper.class);
	}
	
	@Override
	public void onMessage(Message message) {
		try {
			//Get the string containing the post object from the jms message
			//convert it back to a post object
			String postString = message.getBody(String.class);
			Post post = this.postUnmarshaller.unmarshall(postString);
			
			//send it to the matching engine and recover the consequent reports
			System.out.println("POST CONSUMER posting " + message.getBody(String.class));
			LinkedList<MarketResolutionReport> reports = this.matchingEngine.postListing(post);
			if(reports != null) {
				if(reports.size() != 0) {
					MarketResolutionReportWrapper reportWrapper = this.loadReportsToWrapper(reports);
				
					//Marshal the report to xml and send it to the Resolution Report Queue
					String xml = this.marshallingWrapper.marshall(reportWrapper);
					System.out.println(xml);
					this.context.createProducer().send(reportQueue, xml);
				}
			}
		} catch (JMSException | JAXBException e) {
			System.out.println(e.getMessage());
			logger.debug(e.getMessage());
		}
	}
	
	private MarketResolutionReportWrapper loadReportsToWrapper(LinkedList<MarketResolutionReport> list) {
		MarketResolutionReportWrapper reportWrapper = new MarketResolutionReportWrapper();
		ArrayList<MarketResolutionReport> arrayOfReports= new ArrayList<MarketResolutionReport>();
		arrayOfReports.addAll(list);
		reportWrapper.setReports(arrayOfReports);
		return reportWrapper;
	}
	
}

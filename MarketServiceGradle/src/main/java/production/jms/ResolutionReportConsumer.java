package production.jms;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.xml.bind.JAXBException;

import production.business.MarketResolutionReportService;
import production.entity.MarketResolutionReport;
import production.entity.MarketResolutionReportWrapper;
import production.marshalling.MarshallingWrapper;

/**
 * The resolutionReportConsumer is a message driven bean attached to the 
 * market resolution report JMS queue. MarketResolutionReport objects are 
 * posted to this queue by the market backend. This class listens to the 
 * queue for report objects, and upon receipt, unpacks the report object, 
 * accesses the accounts database.
 */
@MessageDriven(name = "ResolutionReportReceiver", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", 
				  propertyValue = "java:jboss/jms/queue/marketResolutionReportQueue"),
		@ActivationConfigProperty(propertyName = "destinationType",
				  propertyValue = "javax.jms.Queue")
})
public class ResolutionReportConsumer implements MessageListener {
	private static final String reportQueueName = "java:jboss/jms/queue/marketResolutionReportQueue";
	
	@Resource(mappedName = reportQueueName)
	private Queue reportQueue;
	
	private MarshallingWrapper<MarketResolutionReportWrapper> marshallingWrapper;
	
	@Inject
	private MarketResolutionReportService marketResolutionReportService;
	
	public ResolutionReportConsumer() throws JAXBException{
		this.marshallingWrapper = new MarshallingWrapper<MarketResolutionReportWrapper>(MarketResolutionReportWrapper.class);
	}
	
	@Override
	public void onMessage(Message message) {
		System.out.println("MessageReceived by the Resolution Report Listener!");
		try {
			//recover the xml from the message body and unmarshall it to a report using the marshallingWrapper
			String xml = message.getBody(String.class);
			//TODO: encase the cast in a try-catch block and add it to the exception handling logic.
			MarketResolutionReportWrapper reportWrapper = (MarketResolutionReportWrapper) this.marshallingWrapper.unmarshal(xml);
			
			//lastly, export the report Wrapper to the injected service
			this.marketResolutionReportService.applyResolutionReportWrapper(reportWrapper);
			
			System.out.println("Message received by the ResolutionReportListener and sent to the resolutionReportService");
			
		} catch (JMSException | JAXBException e) {
			
			System.out.println(e.getMessage());
		}
		
		
		
	}
}

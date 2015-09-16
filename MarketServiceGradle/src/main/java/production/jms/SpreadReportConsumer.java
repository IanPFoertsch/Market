package production.jms;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.xml.bind.JAXBException;
//import org.jboss.logging.Logger;


import production.entity.SpreadStatusReportWrapper;
import production.marshalling.MarshallingWrapper;
import production.storage.StatusReportStorage;


@MessageDriven(name =  "SpreadReportConsumer", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", 
				  propertyValue = "java:jboss/jms/topic/spreadStatusReport"),
		@ActivationConfigProperty(propertyName = "destinationType",
				  propertyValue = "javax.jms.Topic")
})
public class SpreadReportConsumer implements MessageListener {
	
	private static final String spreadReportName = "java:jboss/jms/topic/spreadStatusReport";
	
	@Resource(mappedName = spreadReportName)
	private Topic spreadReportTopic;
	
	@Inject
	private StatusReportStorage statusReportStorage;
	
	private MarshallingWrapper<SpreadStatusReportWrapper> marshallingWrapper;
	
	//private Logger logger;
	
	public SpreadReportConsumer() throws JAXBException {
		//this.logger = Logger.getLogger(this.getClass());
		this.marshallingWrapper = new MarshallingWrapper<SpreadStatusReportWrapper>(SpreadStatusReportWrapper.class);
	}
	
	@Override
	public void onMessage(Message message) {
		try{
			//extract the message body, and use the marshallingWrapper to unmarshall it,
			String xml = message.getBody(String.class);
			//The cast operation is unavoidable below, due to the fact that we are using a "generic" marshaller
			//TODO: encase the cast below in a try-catch block, and address failed casts in the catch section
			SpreadStatusReportWrapper spreadStatusReportWrapper = (SpreadStatusReportWrapper) this.marshallingWrapper.unmarshal(xml);
			//set the in-memory repo to contain this most recent report wrapper
			this.statusReportStorage.setSpreadStatusReportWrapper(spreadStatusReportWrapper);
		}
		catch(Exception e)
		{
		//	logger.error(e);
		}
	}
}

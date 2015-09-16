package production.jms;


import javax.annotation.Resource;


import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.jms.Queue;
import javax.xml.bind.JAXBException;
import org.jboss.logging.Logger;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

import production.entity.Post;
import production.entity.SpreadStatusReport;
import production.entity.SpreadStatusReportWrapper;
import production.marketforum.MatchingEngine;
import production.marshalling.MarshallingWrapper;
import production.marshalling.SpreadStatusReportMarshaller;

@Startup
@LocalBean
@javax.ejb.Singleton
@DependsOn("MatchingEngine")
public class SpreadStatusReportProducer implements Runnable {
	
	
	private static final String spreadStatusReportTopicName = "java:jboss/jms/topic/spreadStatusReport";
	
	@Inject 
	private JMSContext context;
	
	@Resource(mappedName = spreadStatusReportTopicName)
	private Topic topic;
	
	@EJB
	private MatchingEngine matchingEngine;
	
	private Logger logger;
	private MarshallingWrapper<SpreadStatusReportWrapper> marshallingWrapper;
	
	public SpreadStatusReportProducer()
	{
		this.logger = Logger.getLogger(this.getClass());
		logger.info("spread report producer constructed!");
		
		
		try {
			this.marshallingWrapper = new MarshallingWrapper<SpreadStatusReportWrapper>(SpreadStatusReportWrapper.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage() throws JAXBException {
		//Step 1: get the report from the matchingEngine
		SpreadStatusReportWrapper spreadStatusReportWrapper = this.matchingEngine.getSpreadStatusReports();

		
		String marshalledReport = this.marshallingWrapper.marshall(spreadStatusReportWrapper);
		context.createProducer().send(topic, marshalledReport);
		logger.info("Report sent from status report producer.");
		
	}

	@Override
	public void run() {
		try {
			this.sendMessage();
			//logger.info("messageSent!");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
	}
	
	
	
}

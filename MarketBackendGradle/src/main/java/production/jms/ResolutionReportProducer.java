package production.jms;

import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.xml.bind.JAXBException;

import org.jboss.logging.Logger;

import production.entity.MarketResolutionReport;
import production.entity.SpreadStatusReportWrapper;
import production.marketforum.MatchingEngine;
import production.marshalling.MarshallingWrapper;


@Startup
@LocalBean
@DependsOn("MatchingEngine")
public class ResolutionReportProducer {

	private static final String resolutionReportQueueName = "java:jboss/jms/queue/marketResolutionReportQueue";
	
	@Inject 
	private JMSContext context;
	
	@Resource(mappedName = resolutionReportQueueName)
	private Topic topic;
	
	@EJB
	private MatchingEngine matchingEngine;
	
	private Logger logger;
	private MarshallingWrapper<MarketResolutionReport> marshallingWrapper;
	
	public ResolutionReportProducer()
	{
		this.logger = Logger.getLogger(this.getClass());
		logger.info("spread report producer constructed!");
		
		
		try {
			this.marshallingWrapper = new MarshallingWrapper<MarketResolutionReport>(MarketResolutionReport.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage() throws JAXBException {
		//Step 1: get the report from the matchingEngine
		MarketResolutionReport marketResolutionReport = this.matchingEngine.getSpreadStatusReports();
		String marshalledReport = this.marshallingWrapper.marshall(spreadStatusReportWrapper);
		context.createProducer().send(topic, marshalledReport);
		//logger.info("Report sent from status report producer.");
		
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



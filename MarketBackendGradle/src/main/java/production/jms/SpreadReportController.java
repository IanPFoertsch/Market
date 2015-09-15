package production.jms;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;

import org.jboss.logging.Logger;


@ApplicationScoped
@DependsOn("SpreadStatusReportProducer")
public class SpreadReportController {
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	@Inject
	private SpreadStatusReportProducer spreadStatusReportProducer;
	
	private Logger logger;
	
	public SpreadReportController() throws JAXBException {
		this.logger = Logger.getLogger(this.getClass());
		logger.info("Spread Report Controller Created!");
	}
	@PostConstruct
	public void reportEachSecond() {
		 scheduler.scheduleAtFixedRate(spreadStatusReportProducer, 0, 1, TimeUnit.SECONDS);
		 logger.info("Report producer scheduled!");
	}
	
	
}

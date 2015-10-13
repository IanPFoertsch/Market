package production.java.com.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import production.business.MarketResolutionReportService;
import production.dao.MarketResolutionReportDao;
import production.dao.PositionDao;
import production.entity.MarketResolutionReport;
import production.entity.Position;

@RunWith(MockitoJUnitRunner.class)
public class MarketResolutionReportServiceTest {
	
	private String sellerId = "seller";
	private String buyerId = "buyer";
	private String symbol = "GOLD";
	private double volume = 10d;
	private double price = 5d;
	private MarketResolutionReport report; 
	private Position sellerPosition;
	private Position buyerPosition;
	
	@Mock
	private PositionDao positionDao;
	@Mock
	private MarketResolutionReportDao marketResolutionReportDao;

	//Note: declaring the positionDao and reportDao above, followed by the
	//@injectMocks annotation below, will instantiate the service class
	//and inject the mocked objects as it's dependencies prior to running 
	//any tests
	@InjectMocks
	private MarketResolutionReportService service;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.report = new MarketResolutionReport();
		report.setBuyerIdentifier(buyerId);
		report.setSellerIdentifier(sellerId);
		report.setVolume(volume);
		report.setPrice(price);
		report.setSymbol(symbol);
		
		this.sellerPosition = new Position.Builder().
				setUserIdentifier(sellerId).setSymbol(symbol).setVolume(volume).build();
		this.buyerPosition = new Position.Builder().
				setUserIdentifier(buyerId).setSymbol(symbol).setVolume(volume).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * TestReportApplication tests the resolutionReportServices report 
	 * application method. 
	 */
	@Test
	public void testReportApplication() {
		this.service.applyResolutionReport(report);
		//Mockito.
	}

}

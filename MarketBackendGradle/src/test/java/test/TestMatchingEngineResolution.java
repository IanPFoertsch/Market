package test;
import static org.junit.Assert.*;


import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import production.entity.MarketResolutionReport;
import production.entity.Post;
import production.enums.PostOutcome;
import production.enums.PostingType;

import production.marketforum.MatchingEngine;


@RunWith(MockitoJUnitRunner.class)
public class TestMatchingEngineResolution {
	private Post ask;
	private Post bid;
	private String Symbol = "GOLD";
	private String sellerId = "Seller";
	private String buyerId = "Buyer";
	private MatchingEngine matchingEngine;
	private double sellVolume = 10.0;
	private double buyVolume = 10.0;
	private double sellPrice = 10.0;
	private double buyPrice = 10.0;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}
	

	@Before
	public void setUp() throws Exception {
		this.ask = new Post();
		this.bid = new Post();
		this.ask.setSymbol(this.Symbol);
		this.ask.setPrice(this.sellPrice);
		this.ask.setPostingType(PostingType.ASK);
		this.ask.setUserIdentifier(this.sellerId);
		this.ask.setVolume(this.sellVolume);
		this.ask.setDate(System.currentTimeMillis());
		this.bid.setSymbol(this.Symbol);
		this.bid.setPrice(this.buyPrice);
		this.bid.setPostingType(PostingType.BID);
		this.bid.setUserIdentifier(this.buyerId);
		this.bid.setVolume(this.buyVolume);
		this.bid.setDate(System.currentTimeMillis());
		this.matchingEngine = new MatchingEngine();
	}

	@After
	public void tearDown() throws Exception{
	}

	@Test
	public void testMatchingEngineResolution() {
		LinkedList<MarketResolutionReport> reports = this.matchingEngine.postListing(this.ask);
		assertTrue(reports.size() == 0);
	}
	
	@Test
	public void testSimpleOneCommodityExchange()
	{
		this.matchingEngine.postListing(this.ask);
		LinkedList<MarketResolutionReport> reports = this. matchingEngine.postListing(this.bid);
		MarketResolutionReport firstReport = reports.getFirst();
		assertTrue(this.reportIdentitiesMatchPostings(firstReport, ask, bid));
	}

	@Test
	public void testTwoElementSaleBetweenTwoParties()
	{
		Post smallRequestOne = this.bid.deepCopy();
		Post smallRequestTwo = this.bid.deepCopy();
		smallRequestOne.setVolume(this.buyVolume * .5);
		smallRequestTwo.setVolume(this.buyVolume * .5);
		this.matchingEngine.postListing(smallRequestOne);
		this.matchingEngine.postListing(smallRequestTwo);
		LinkedList<MarketResolutionReport> reports = this.matchingEngine.postListing(this.ask);
		
		assertTrue(reports.size() == 2
					&& this.reportIdentitiesMatchPostings(reports.getFirst(), this.ask, smallRequestOne)
					&& this.reportIdentitiesMatchPostings(reports.getFirst(), this.ask, smallRequestOne));
	}

	@Test
	public void testpreferenceForLowestOffer() {
		Post lowPriceOffer = this.ask.deepCopy();
		Post highPriceOffer = this.ask.deepCopy();
		Post localRequest = this.bid.deepCopy();
		this.matchingEngine.postListing(lowPriceOffer);
		this.matchingEngine.postListing(highPriceOffer);
		LinkedList<MarketResolutionReport> reports =	this.matchingEngine.postListing(localRequest);	
	
		
		MarketResolutionReport report = reports.getFirst();
		reportIdentitiesMatchPostings(report, lowPriceOffer, localRequest);
	}

	@Test
	public void testPreferenceForHighestRequest()	{
		Post localOffer = this.ask.deepCopy();
		Post highPriceRequest = this.bid.deepCopy();
		Post lowPriceRequest = this.bid.deepCopy();
		this.matchingEngine.postListing(highPriceRequest);
		this.matchingEngine.postListing(lowPriceRequest);
		LinkedList<MarketResolutionReport> reports = this.matchingEngine.postListing(localOffer);

		MarketResolutionReport report = reports.getFirst();
		reportIdentitiesMatchPostings(report, localOffer, highPriceRequest);
	}
	
	
	@Test 
	public void testSpreadReportGeneration() {
		
	}
	
	
	
	
	

	private boolean reportIdentitiesMatchPostings(MarketResolutionReport report, Post ask, Post bid)
	{
		if(report.getBuyerIdentifier().equalsIgnoreCase(bid.getUserIdentifier())
				&& report.getBuyerDate() == bid.getDate() 
				&& report.getSellerIdentifier().equalsIgnoreCase(ask.getUserIdentifier())
				&& report.getSellerDate() == ask.getDate())
			return true;
		else
			return false;
	}
}
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
	private Post offer;
	private Post request;
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
		this.offer = new Post();
		this.request = new Post();
		this.offer.setSymbol(this.Symbol);
		this.offer.setPrice(this.sellPrice);
		this.offer.setPostingType(PostingType.OFFER);
		this.offer.setUserIdentifier(this.sellerId);
		this.offer.setVolume(this.sellVolume);
		this.offer.setDate(System.currentTimeMillis());
		this.request.setSymbol(this.Symbol);
		this.request.setPrice(this.buyPrice);
		this.request.setPostingType(PostingType.REQUEST);
		this.request.setUserIdentifier(this.buyerId);
		this.request.setVolume(this.buyVolume);
		this.request.setDate(System.currentTimeMillis());
		this.matchingEngine = new MatchingEngine();
	}

	@After
	public void tearDown() throws Exception{
	}

	@Test
	public void testMatchingEngineResolution() {
		LinkedList<MarketResolutionReport> reports = this.matchingEngine.postListing(this.offer);
		assertTrue(reports.size() == 1);
	}
	
	@Test
	public void testSimpleOneCommodityExchange()
	{
		this.matchingEngine.postListing(this.offer);
		LinkedList<MarketResolutionReport> reports = this. matchingEngine.postListing(this.request);
		MarketResolutionReport firstReport = reports.getFirst();
		assertTrue(this.reportIdentitiesMatchPostings(firstReport, offer, request));
	}

	@Test
	public void testTwoElementSaleBetweenTwoParties()
	{
		Post smallRequestOne = this.request.deepCopy();
		Post smallRequestTwo = this.request.deepCopy();
		smallRequestOne.setVolume(this.buyVolume * .5);
		smallRequestTwo.setVolume(this.buyVolume * .5);
		this.matchingEngine.postListing(smallRequestOne);
		this.matchingEngine.postListing(smallRequestTwo);
		LinkedList<MarketResolutionReport> reports = this.matchingEngine.postListing(this.offer);
		
		assertTrue(reports.size() == 2
					&& this.reportIdentitiesMatchPostings(reports.getFirst(), this.offer, smallRequestOne)
					&& this.reportIdentitiesMatchPostings(reports.getFirst(), this.offer, smallRequestOne));
	}

	@Test
	public void testpreferenceForLowestOffer() {
		Post lowPriceOffer = this.offer.deepCopy();
		Post highPriceOffer = this.offer.deepCopy();
		Post localRequest = this.request.deepCopy();
		this.matchingEngine.postListing(lowPriceOffer);
		this.matchingEngine.postListing(highPriceOffer);
		LinkedList<MarketResolutionReport> reports =	this.matchingEngine.postListing(localRequest);	
	
		
		MarketResolutionReport report = reports.getFirst();
		reportIdentitiesMatchPostings(report, lowPriceOffer, localRequest);
	}

	@Test
	public void testPreferenceForHighestRequest()	{
		Post localOffer = this.offer.deepCopy();
		Post highPriceRequest = this.request.deepCopy();
		Post lowPriceRequest = this.request.deepCopy();
		this.matchingEngine.postListing(highPriceRequest);
		this.matchingEngine.postListing(lowPriceRequest);
		LinkedList<MarketResolutionReport> reports = this.matchingEngine.postListing(localOffer);

		MarketResolutionReport report = reports.getFirst();
		reportIdentitiesMatchPostings(report, localOffer, highPriceRequest);
	}
	
	
	@Test 
	public void testSpreadReportGeneration() {
		
	}
	
	
	
	
	

	private boolean reportIdentitiesMatchPostings(MarketResolutionReport report, Post offer, Post request)
	{
		if(report.getBuyerIdentifier().equalsIgnoreCase(request.getUserIdentifier())
				&& report.getBuyerDate() == request.getDate() 
				&& report.getSellerIdentifier().equalsIgnoreCase(offer.getUserIdentifier())
				&& report.getSellerDate() == offer.getDate())
			return true;
		else
			return false;
	}
}
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import production.entity.Post;
import production.entity.SpreadStatusReport;
import production.enums.PostingType;
import production.marketforum.MatchingEngine;

public class TestSpreadReportGeneration {

	private Post ask;
	private Post bid;
	private String goldSymbol = "GOLD";
	private String sellerId = "Seller";
	private String buyerId = "Buyer";
	private MatchingEngine matchingEngine;
	private double sellVolume = 10.0;
	private double buyVolume = 10.0;
	private double sellPrice = 10.0;
	private double buyPrice = 10.0;

	
	private String silverSymbol = "SILVER";
	private String copperSymbol = "COPPER";
	private String ironSymbol = "IRON";
	
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
		this.ask.setSymbol(this.goldSymbol);
		this.ask.setPrice(this.sellPrice);
		this.ask.setPostingType(PostingType.OFFER);
		this.ask.setUserIdentifier(this.sellerId);
		this.ask.setVolume(this.sellVolume);
		this.ask.setDate(System.currentTimeMillis());
		this.bid.setSymbol(this.goldSymbol);
		this.bid.setPrice(this.buyPrice);
		this.bid.setPostingType(PostingType.REQUEST);
		this.bid.setUserIdentifier(this.buyerId);
		this.bid.setVolume(this.buyVolume);
		this.bid.setDate(System.currentTimeMillis());
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSpreadReportContainsDefaultSymbol() {
		this.matchingEngine = this.generateDefaultMatchingEngine();
		ArrayList<SpreadStatusReport> spreadReports = this.matchingEngine.getSpreadStatusReports();
		SpreadStatusReport report = spreadReports.get(0);
		System.out.println(report.getSymbol());
	}
	
	@Test
	public void testMultipleSymbolReports() {
		this.matchingEngine = this.generateFullMatchingEngine();
		
		ArrayList<SpreadStatusReport> spreadReports = this.matchingEngine.getSpreadStatusReports();
		//unpack the arraylist and get all the symbols contained in the spread reports
		//assert they contain all the symbols (Iron, copper, silver, gold)
	}
	
	private MatchingEngine generateDefaultMatchingEngine() {
		MatchingEngine matchingEngine = new MatchingEngine();
		
		Post ask = new Post();
		Post bid = new Post();
		ask.setSymbol(this.goldSymbol);
		ask.setPrice(this.sellPrice);
		ask.setPostingType(PostingType.OFFER);
		ask.setUserIdentifier(this.sellerId);
		ask.setVolume(this.sellVolume);
		ask.setDate(System.currentTimeMillis());
		bid.setSymbol(this.goldSymbol);
		bid.setPrice(this.buyPrice);
		bid.setPostingType(PostingType.REQUEST);
		bid.setUserIdentifier(this.buyerId);
		bid.setVolume(this.buyVolume);
		bid.setDate(System.currentTimeMillis());
		
		matchingEngine.postListing(ask);
		matchingEngine.postListing(bid);
		return matchingEngine;
	}
	
	private MatchingEngine generateFullMatchingEngine() {
		
		MatchingEngine matchingEngine = this.generateDefaultMatchingEngine();
		
		Post silverAsk = this.ask.deepCopy();
		silverAsk.setSymbol(silverSymbol);
		
		Post copperBid = this.bid.deepCopy();
		copperBid.setSymbol(copperSymbol);
		
		Post ironAsk = this.ask.deepCopy();
		ironAsk.setSymbol(ironSymbol);
		
		matchingEngine.postListing(silverAsk);
		matchingEngine.postListing(copperBid);
		matchingEngine.postListing(ironAsk);
		
		return matchingEngine;
		
	}

}

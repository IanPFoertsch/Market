package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

	private String[] symbols = {goldSymbol, "SILVER", "COPPER", "IRON"};
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
		this.ask.setPostingType(PostingType.ASK);
		this.ask.setUserIdentifier(this.sellerId);
		this.ask.setVolume(this.sellVolume);
		this.ask.setDate(System.currentTimeMillis());
		this.bid.setSymbol(this.goldSymbol);
		this.bid.setPrice(this.buyPrice);
		this.bid.setPostingType(PostingType.BID);
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
		ArrayList<SpreadStatusReport> spreadReports = this.matchingEngine.getSpreadStatusReports().getReports();
		SpreadStatusReport report = spreadReports.get(0);
		System.out.println(report.getSymbol());
	}
	
	@Test
	public void testMultipleSymbolReports() {
		this.matchingEngine = this.generateFullMatchingEngine();
		
		ArrayList<SpreadStatusReport> spreadReports = this.matchingEngine.getSpreadStatusReports().getReports();
		Assert.assertTrue(this.spreadReportContainsAllSymbolNames(spreadReports, this.symbols));
	}
	
	private MatchingEngine generateDefaultMatchingEngine() {
		MatchingEngine matchingEngine = new MatchingEngine();
		matchingEngine.postListing(this.ask);
		matchingEngine.postListing(this.bid);
		return matchingEngine;
	}
	
	private MatchingEngine generateFullMatchingEngine() {
		
		MatchingEngine matchingEngine = this.generateDefaultMatchingEngine();
		
		for(String symbol: this.symbols)
		{
			Post newPost = this.ask.deepCopy();
			newPost.setSymbol(symbol);
			matchingEngine.postListing(newPost);
		}
		//The matching engine should now contain an order book for each symbol in the 
		//symbols field
		return matchingEngine;
	}
	
	public boolean spreadReportContainsAllSymbolNames(ArrayList<SpreadStatusReport> reports, String[] symbolNames)
	{
		//extract all of the reports from the arraylist,
		
		Set<String> symbolsInReport = new HashSet<String>(reports.size());
		for(int i = 0; i < reports.size(); i++)
		{
			symbolsInReport.add(reports.get(i).getSymbol());
		}
		//then  for each symbol in the symbolNames parameter, verify that they are present in the report
		for(String name: symbolNames)
		{
			if(!symbolsInReport.contains(name))
				return false;
		}
		
		return true;
	}

}

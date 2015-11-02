package production.marketforum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Startup;


import org.jboss.logging.Logger;
import production.entity.*;
import production.enums.PostingType;
import production.enums.PostOutcome;
import production.entity.SpreadStatusReport;
import production.entity.SpreadStatusReportWrapper;



/**
 * Market is the forum in which buyers are matched with sellers. So we must have
 * two hashmaps, one for asks(from sellers) and one for bids(from buyers)
 * 
 * @author e588318
 */
@Startup
@LocalBean
@javax.ejb.Singleton
public class MatchingEngine {
	
	
	private Map<String, PriorityQueue<Post>> asks;
	private Map<String, PriorityQueue<Post>> bids;
	

	public MatchingEngine() {
		// DataStructure initialization
		this.asks = new HashMap<String, PriorityQueue<Post>>();
		this.bids = new HashMap<String, PriorityQueue<Post>>();
	}

	public LinkedList<MarketResolutionReport> postListing(Post incomingPost){
		Post post = incomingPost.deepCopy();
		if(post.getPostingType() == PostingType.ASK){
			
			//does the market contain a complementaryListing for this symbol?
					//if yes, attempt to resolve to post
					if(this.bids.containsKey(post.getSymbol()))
						return this.resolvePost(post);
					//if no, create a listing for this post
					else{
						//Handle the unsatisfied demand and return and empty list of reports
						this.handleUnresolvedPost(post);
						return new LinkedList<MarketResolutionReport>();
					}	
		}
		else {
			//does the market contain a complementary listing for thissymbol?
			//if yes, attempt to resolve to post
			if(this.asks.containsKey(post.getSymbol()))
				return this.resolvePost(post);
				//if no, create a listing for this post
			else{
				//handle the unsatisfied demand in the post, and return an empty list
				this.handleUnresolvedPost(post);
				return new LinkedList<MarketResolutionReport>();
			}
		}
	}
	/**
	 * Resolve post is the market resolution algorithm. The method determines
	 * the posting type of the post object, looks up the complimentary listing
	 * for the post�s symbol, then attempts to fill the volume of the post from
	 * the available orders from the complimentary listing, generating market
	 * resolution reports as it does so. The algorithm will generate a new
	 * priority queue containing the incoming post object if the market cannot
	 * fulfill the incoming post�s demand. The method returns a list of market
	 * resolution reports containing sales generated by the resolution algorithm
	 * param post
	 * 
	 * @return throws Exception
	 */
	private LinkedList<MarketResolutionReport> resolvePost(Post post) {
		PriorityQueue<Post> pertinentListing;
		LinkedList<MarketResolutionReport> reports = new LinkedList<>();
		// get the complimentary listing for the post (if the post is anASK,
		// get the bids and vice versa
		if (post.getPostingType() == PostingType.ASK)
			pertinentListing = this.bids.get(post.getSymbol());
		else
			pertinentListing = this.asks.get(post.getSymbol());

		// Check to see if the PertinentListing contains an applicableposting
		// and the ASK still contains unfulfilled volume
		while (this.complimentaryListingContainsApplicablePostings(pertinentListing, post) && post.getVolume() > 0) { 
			// poll the	listing to obtain the best listing price
			Post bestListing = pertinentListing.poll();
			// resolve the listings, generating a report
			MarketResolutionReport report = this.resolveListings(post, bestListing);
			// Apply the changes in the resolution report to our current
			// post/post objects:
			this.subtractReportVolumeFromPosts(report, post, bestListing);
			// if the pertinent listing has remaining volume, reinsert
			// it into the appropriate queue
			if (bestListing.getVolume() > 0) {
				pertinentListing.add(bestListing);
			}
			// add the report to the report list
			reports.add(report);
		}
		// Does the post still contain unfilled volume?
		if (post.getVolume() > 0) {
			//if so, handle the unresolved post. 
			//REVISED BY IAN FOERTSCH 11/02/15
			//Removed the addition of null resolution reports
			this.handleUnresolvedPost(post);
		}
		// return the list of reports
		return reports;
	}
	
	public String generateReport() {
		String status = "";
		for(PriorityQueue<Post> queue : this.asks.values()) {
			if(!queue.isEmpty()) {
				status += "ASK Listing " + queue.peek().getSymbol() + " of size : " + queue.size() + "\n";
			}	 
		}
		for(PriorityQueue<Post> queue: this.bids.values())
		{
			if(!queue.isEmpty()) {
				status += "bid Listing " + queue.peek().getSymbol() + " of size : " + queue.size() + "\n";
			}	 
		}
		return status;
	}

	private boolean complimentaryListingContainsApplicablePostings(PriorityQueue<Post> listing, Post post)
	{
		//first check for a null pointer exception, we may be receiving an empty listing
		//this will be the case when the matching engine resolves all of the postings on
		//the pertinent listing, and then checks to see if the listing contains another
		//post with the peek() method, throwing a null pointer exception.
		try{
			//if we are dealing with an ASK posting,
			if(post.getPostingType() == PostingType.ASK)
			{ 	//then does the listing (of bids) contain postings
				//which are greater than or equal to the price of the posting?
				if(listing.peek().getPrice() >= post.getPrice())
					return true;
				else
					return false;
			}
			else{//otherwise the post is a bid, and check if the best ASK price
				//is LESS than or equal to the post�s price
				if(listing.peek().getPrice() <= post.getPrice())
					return true;
				else
					return false;
			}
		}
		catch(NullPointerException e){
			return false;
		}
	}

	/**
	 * The applyReportToListingObjects accepts a report object, and two post
	 * objects describing the bid and ASK posts The method subtracts the
	 * report�s volume field from both the bid and ASK objects Volume
	 * field
	 */
	private void subtractReportVolumeFromPosts(MarketResolutionReport report, Post postObject1, Post postObject2) {
		postObject1.setVolume(postObject1.getVolume() - report.getVolume());
		postObject2.setVolume(postObject2.getVolume() - report.getVolume());
	}

	/**
	 * ResolveListings accepts a bid and an ASK, and builds a
	 * marketResolutionReport describing the parameter of the sale, and returns
	 * the report param bid param ASK return
	 */
	private MarketResolutionReport resolveListings(Post firstPost, Post secondPost) {
		// handle incoming ambiguity: Which post is the ASK and which post
		// isthe bid
		Post ask;
		Post bid;
		if (firstPost.getPostingType() == PostingType.ASK) {
			ask= firstPost;
			bid = secondPost;
		} else {
			ask  = secondPost;
			bid = firstPost;
		}
		// Two possible use cases here: the ask  requires more volume than
		// thebid object
		// possesses, or vice versa.
		MarketResolutionReport report = new MarketResolutionReport();
		report.setBuyerIdentifier(bid.getUserIdentifier());
		report.setSellerIdentifier(ask .getUserIdentifier());
		report.setSymbol(ask .getSymbol());
		// The price will be the seller�s price
		report.setPrice(ask .getPrice());
		// Determine the sale volume
		if (ask.getVolume() > bid.getVolume()) {
			// the ask  will have leftover volume, the bid will have 0
			// volume,
			// therefore the sale volume will be equal to the bid�s current
			// volume.
			report.setVolume(bid.getVolume());
		} else {// otherwise the ask volume is less than or equal to the
				// bid
				// The ask will be completely filled by the bid, so set
				// the sale volume to
				// equal the ask volume
			report.setVolume(ask.getVolume());
		}
		return loadTwoPostsToReport(report, ask, bid);
	}

	private void handleUnresolvedPost(Post post) {
		
		
		// Is this an ask or a bid?
		if (post.getPostingType() == PostingType.ASK) {
			// Does a listing of this type currently exist in the asks field
			// for the post�s symbol?
			if (this.asks.containsKey(post.getSymbol())) {
				// add it to that order book
				this.asks.get(post.getSymbol()).add(post);
			} else { // otherwise generate a new priority queue, insert the
						// post to that queue, and insert the queue to the
						// asks field mapped
						// to the post�s symbol
				PriorityQueue<Post> newListing = new PriorityQueue<>();
				newListing.add(post);
				this.asks.put(post.getSymbol(), newListing);
			}
		}
		// else it is bid:
		else {
			// Do the bid listings contain a queue for this symbol?
			// if so, add it to that listing
			if (this.bids.containsKey(post.getSymbol())) {
				this.bids.get(post.getSymbol()).add(post);
			} else {// otherwise generate a new queue containing this post
					// and add it to the this.bids field
				PriorityQueue<Post> newListing = new PriorityQueue<>();
				newListing.add(post);
				this.bids.put(post.getSymbol(), newListing);
			}
		}
		
	}
	
	


		/**
		 *FOLLOWING CODE REMOVED TO PREVENT NULL REPORT BUG. IAN FOERTSCH 11/02/15 
		 * @param post
		 * @return
		 *
		private  MarketResolutionReport loadSinglePostToIncompleteReport(Post post){
			MarketResolutionReport report = new MarketResolutionReport();
			if(post.getPostingType() == PostingType.ASK)
			{//If we are receiving an ask, then load the information to theseller field
				//of the report
				report.setSellerIdentifier(post.getUserIdentifier());
				report.setSellerDate(post.getDate());
				report.setPostOutcome(PostOutcome.NOT_COMPLETED);
				return report;
			}
			else{
				report.setBuyerIdentifier(post.getUserIdentifier());
				report.setBuyerDate(post.getDate());
				report.setPostOutcome(PostOutcome.NOT_COMPLETED);
				return report;
			}
		}
		*/
			
		private MarketResolutionReport loadTwoPostsToReport(MarketResolutionReport report, Post ask, Post bid)
			{
				report.setBuyerIdentifier(bid.getUserIdentifier());
				report.setBuyerDate(bid.getDate());
				report.setPostOutcome(PostOutcome.COMPLETED);
				//Seller�s rule, therefore we set the report�s price to the ask�s listed price
				report.setPrice(ask.getPrice());
				report.setSellerIdentifier(ask.getUserIdentifier());
				report.setSellerDate(ask.getDate());
				//volume is set when the two postings are resolved, therefore we don�t have to worry wout setting it here,
				//and we can just return the report
						return report;

			}
	
	/**
	 * getSpreadStatusReports generates an arraylist of spread status reports 
	 * detailing the current market spread between bid and ask prices for all symbols
	 * in the order books
	 * @return
	 */
	public SpreadStatusReportWrapper getSpreadStatusReports()
	{	//Workflow:
		//	1.) copy all symbol strings from the order books to a union 
		//		object in a thread-safe way
		//	2.) get individual spread status reports for each symbol 
		//		using a private method.
		
		//create a union object to hold all the strings for both the bid and ask sets
		Set<String> union = new HashSet<String>();
		synchronized(this) {
			//now copy over the bid/ask symbol sets into a new set	
			union.addAll(this.bids.keySet());
			union.addAll(this.asks.keySet());
			//now we can relinquish the intrinsic lock now that our bids and asks 
		}
		//now create an arraylist of spreadstatusreports with a size == the size of the union set
		ArrayList<SpreadStatusReport> reports = new ArrayList<SpreadStatusReport>(union.size());
		
		for(String symbol: union)
		{	//get individual reports for all symbols
			reports.add(this.generateReportForSymbol(symbol));
		}
		
		SpreadStatusReportWrapper spreadStatusReportWrapper = new SpreadStatusReportWrapper();
		spreadStatusReportWrapper.setReports(reports);
		return spreadStatusReportWrapper;
	}
	
	/**
	 * generateReportForSymbol generates an individual spread status 
	 * report for a given symbol. If the bid or ask books for that 
	 * symbol either do not exist or do not currently contain a current
	 * bid or ask, those fields in the report are uninitialized, (set to 0 with 
	 * default java)
	 * @param symbol
	 * @return
	 */
	private SpreadStatusReport generateReportForSymbol(String symbol) {
		SpreadStatusReport report = new SpreadStatusReport();
		report.setSymbol(symbol);
		//get the pertinent listings from the object fields. 
		//test to see if the listing exists. If it does, extract the pertinent info and assign it to the 
		//status report. Otherwise, set the fields to null
		PriorityQueue<Post> bidListing = this.bids.get(symbol);
		if(bidListing != null)
		{	//then we have an order book, get the best listing in the book using the peek method
			Post bestBid = bidListing.peek();
			if(bestBid != null)
			{
				report.setBidPrice(bestBid.getPrice());
				report.setBidVolume(bestBid.getVolume());
			}			
		}
		PriorityQueue<Post> askListing = this.asks.get(symbol);
		if(askListing != null)
		{
			Post bestAsk = askListing.peek();
			if(bestAsk != null)
			{
				report.setAskPrice(bestAsk.getPrice());
				report.setAskVolume(bestAsk.getVolume());
			}
		}
		return report;
	}
}
package production.business;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import production.dao.MarketResolutionReportDao;
import production.dao.PositionDao;
import production.entity.MarketResolutionReport;
import production.entity.Position;

/**
 * The MarketResolutionReportService is a business logic bean which
 * actually applies the marketresolutionreport to the underlying database.
 * in particular, when it is given a marketResolutionReport, it subtracts 
 * the volume detailed in the report from the seller and adds it to the buyer for 
 * the stated symbol. Then, it calculates the amount of cash required for the 
 * sale and adds it to the seller, and subtracts  it from the buyer. This is done 
 * in a transactional manner. 
 * @author Ian
 *
 */
@RequestScoped
public class MarketResolutionReportService {

	
	@Inject 
	private PositionDao positionDao;
	
	@Inject
	private MarketResolutionReportDao marketResolutionReportDao;
	
	private static String CASH_SYMBOL = "CASH";
	/**
	 * ApplyResolution report performs the actual business calculations and interfaces
	 * with the positions data access object to persist the changes to the database.
	 * @param report
	 */
	public void applyResolutionReport(MarketResolutionReport report) {

		//recover the parameters from the report and calculate the sale magnitude in cash.
		String symbol = report.getSymbol();
		String buyerID = report.getBuyerIdentifier();
		String sellerID = report.getSellerIdentifier();
		double volume = report.getVolume();
		double price = report.getPrice();
		double cash = price*volume;
		
		//build a series of Positions objects detailing the cash and positions to be subtracted and 
		//added to the buyer/seller.
		
		//build a seller cash positions which will add the cash to their accounts
		Position sellerCash = new Position.Builder().setUserIdentifier(sellerID).setVolume(cash).setSymbol(CASH_SYMBOL).build();
		//build a buyer cash position which will subtract the cash from their account
		Position buyerCash = new Position.Builder().setUserIdentifier(buyerID).setVolume(-cash).setSymbol(CASH_SYMBOL).build();
		//build a seller Position which will subtract the security from their account.
		Position sellerPosition = new Position.Builder().setUserIdentifier(sellerID).setVolume(-volume).setSymbol(symbol).build();
		//build a buyer symbol position which will add the security to their account.
		Position buyerPosition = new Position.Builder().setUserIdentifier(buyerID).setVolume(volume).setSymbol(symbol).build();
		
		//access the PositionDAO and execute the transaction
		this.positionDao.applyMarketResolutionReport(sellerCash, buyerCash, sellerPosition, buyerPosition);
		//THIS IS A POINT OF FAILURE HERE: DO WE NEED TO MAKE THIS SECTION TRANSACTIONAL?
		this.marketResolutionReportDao.persist(report);
	}
}

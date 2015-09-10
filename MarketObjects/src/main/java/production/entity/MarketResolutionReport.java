package production.entity;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import production.enums.PostOutcome;

@XmlRootElement(name ="marketResolutionReport")
@XmlAccessorType(XmlAccessType.FIELD)
public class MarketResolutionReport {
	@XmlElement
	private String Symbol;
	@XmlElement
	private double price;
	@XmlElement
	private double volume;
	@XmlElement
	private String sellerIdentifier;
	@XmlElement
	private String buyerIdentifier;
	@XmlElement
	private PostOutcome postOutcome;
	@XmlElement
	private long sellerDate;
	@XmlElement
	private long buyerDate;

	public String toString()
	{
		return "Market Resolution Report:\nType: " + this.getPostOutcome() +
				"\nSymbol: "+ this.getSymbol() + "\nPrice: " + this.getPrice() +"\nVolume: "+
				this.getVolume() + "\nBuyer Id: " + this.getBuyerIdentifier() + "\nSeller Id: " +
				this.getSellerIdentifier();
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return Symbol;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return the volume
	 */
	public double getVolume() {
		return volume;
	}

	/**
	 * @return the sellerIdentifier
	 */
	public String getSellerIdentifier() {
		return sellerIdentifier;
	}

	/**
	 * @return the buyerIdentifier
	 */
	public String getBuyerIdentifier() {
		return buyerIdentifier;
	}

	/**
	 * @return the postOutcome
	 */
	public PostOutcome getPostOutcome() {
		return postOutcome;
	}

	/**
	 * @return the sellerDate
	 */
	public long getSellerDate() {
		return sellerDate;
	}

	/**
	 * @return the buyerDate
	 */
	public long getBuyerDate() {
		return buyerDate;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		Symbol = symbol;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(double volume) {
		this.volume = volume;
	}

	/**
	 * @param sellerIdentifier the sellerIdentifier to set
	 */
	public void setSellerIdentifier(String sellerIdentifier) {
		this.sellerIdentifier = sellerIdentifier;
	}

	/**
	 * @param buyerIdentifier the buyerIdentifier to set
	 */
	public void setBuyerIdentifier(String buyerIdentifier) {
		this.buyerIdentifier = buyerIdentifier;
	}

	/**
	 * @param postOutcome the postOutcome to set
	 */
	public void setPostOutcome(PostOutcome postOutcome) {
		this.postOutcome = postOutcome;
	}

	/**
	 * @param sellerDate the sellerDate to set
	 */
	public void setSellerDate(long sellerDate) {
		this.sellerDate = sellerDate;
	}

	/**
	 * @param buyerDate the buyerDate to set
	 */
	public void setBuyerDate(long buyerDate) {
		this.buyerDate = buyerDate;
	}
}
	
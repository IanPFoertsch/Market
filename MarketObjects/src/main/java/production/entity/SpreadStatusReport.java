package production.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="spreadstatusreport")

@XmlAccessorType(XmlAccessType.FIELD)
public class SpreadStatusReport {
	@XmlElement
	private String symbol;
	@XmlElement
	private double askPrice;
	@XmlElement
	private double bidPrice;
	@XmlElement
	private double askVolume;
	@XmlElement
	private double bidVolume;
	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	/**
	 * @return the askPrice
	 */
	public double getAskPrice() {
		return askPrice;
	}
	/**
	 * @return the bidPrice
	 */
	public double getBidPrice() {
		return bidPrice;
	}
	/**
	 * @return the askVolume
	 */
	public double getAskVolume() {
		return askVolume;
	}
	/**
	 * @return the bidVolume
	 */
	public double getBidVolume() {
		return bidVolume;
	}
	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	/**
	 * @param askPrice the askPrice to set
	 */
	public void setAskPrice(double askPrice) {
		this.askPrice = askPrice;
	}
	/**
	 * @param bidPrice the bidPrice to set
	 */
	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}
	/**
	 * @param askVolume the askVolume to set
	 */
	public void setAskVolume(double askVolume) {
		this.askVolume = askVolume;
	}
	/**
	 * @param bidVolume the bidVolume to set
	 */
	public void setBidVolume(double bidVolume) {
		this.bidVolume = bidVolume;
	}

}

package production.entity;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import production.enums.PostingType;


@XmlRootElement(name="Post")
@XmlAccessorType(XmlAccessType.FIELD)
public class Post implements Comparable<Post>{
	
	@FormParam(value = "symbol")
	@XmlElement
	private String symbol;
	
	@FormParam(value = "price")
	@XmlElement
	private double price;
	
	@FormParam(value = "volume")
	@XmlElement
	private double volume;
	
	@FormParam(value = "postingType")
	@XmlElement
	private PostingType postingType;
	
	@FormParam(value = "userIdentifier")
	@XmlElement
	private String userIdentifier;
	
	@FormParam(value = "date")
	@XmlElement
	private long date;
	
	
	@Override
	public int compareTo(Post otherPost) {
		if(this.postingType == PostingType.OFFER)
			//Return -1 to indicate that offers should return the lowest possible priced offer
					return this.getPrice() <= otherPost.getPrice() ? -1:1;
		else
			//we want to reverse the ordering here if these are Requests,
			//because the highest available offer should be returned
			return this.getPrice() >= otherPost.getPrice() ? -1:1;
	}
	
	
	
	@Override
	public boolean equals(Object object){
		try{
			Post otherPost = (Post) object;
			if(this.getPrice() == otherPost.getPrice() &&
					this.getSymbol().equalsIgnoreCase(otherPost.getSymbol()) &&
					this.getVolume() == otherPost.getVolume() &&
					this.getPostingType() == otherPost.getPostingType())
				return true;
			else
				return false;
		}
		catch(ClassCastException e)
		{
			return false;
		}
	}

	public String toString(){
	return "Post:" + "\nType: " + PostingType.toString(this.postingType) +
			"\nUser Id: " + this.userIdentifier +
			"\nSymbol: "+ this.symbol + "\nVolume: " +
			this.volume + "\nPrice: " + this.price;
	}

	public Post deepCopy(){
		Post copy = new Post();
		copy.setPostingType(this.postingType);
		copy.setPrice(this.price);
		copy.setSymbol(this.symbol);
		copy.setDate(this.getDate());
		copy.setUserIdentifier(this.userIdentifier);
		copy.setVolume(this.volume);
		return copy;
}



	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
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
	 * @return the postingType
	 */
	public PostingType getPostingType() {
		return postingType;
	}



	/**
	 * @return the userIdentifier
	 */
	public String getUserIdentifier() {
		return userIdentifier;
	}



	



	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
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
	 * @param postingType the postingType to set
	 */
	public void setPostingType(PostingType postingType) {
		this.postingType = postingType;
	}



	/**
	 * @param userIdentifier the userIdentifier to set
	 */
	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}



	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}



	/**
	 * @param date the date to set
	 */
	public void setDate(long date) {
		this.date = date;
	}



	
}
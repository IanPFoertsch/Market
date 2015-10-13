package production.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Position")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "positions")
public class Position implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double EQUALITY_COMPARISON = .0001;


	@Id
	@NotNull
	@Column(name = "user_identifier")
	@XmlElement
	private String userIdentifier;

	@Id
	@NotNull
	@Column(name = "symbol")
	@XmlElement
	private String symbol;
	
	@NotNull
	@Column(name = "volume")
	@XmlElement
	private double volume;
	
	/**
	 * @return the userIdentifier
	 */
	public String getUserIdentifier() {
		return userIdentifier;
	}


	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}


	/**
	 * @return the volume
	 */
	public double getVolume() {
		return volume;
	}


	/**
	 * @param userIdentifier the userIdentifier to set
	 */
	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}


	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}


	/**
	 * @param volume the volume to set
	 */
	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String toString() {
		String status = "Position:\nuser_identifier = " + this.getUserIdentifier() +
				"\nsymbol = " + this.symbol +  "\nvolume = " +this.volume + "\n" ;
		return status;
	}
	
	public boolean equals(Position otherPosition) {
		double delta = this.getVolume() - otherPosition.getVolume();
		if(this.getSymbol().equalsIgnoreCase(otherPosition.getSymbol()) &&
				this.getUserIdentifier().equalsIgnoreCase(otherPosition.getUserIdentifier()) &&
				Math.abs(delta) < EQUALITY_COMPARISON)
			return true;
		else 
			return false;
	}
	
	public static class Builder {
		
		private String userIdentifier;
		private String symbol;
		private double volume;
		
		public Builder() {
			this.userIdentifier = "";
			this.symbol = "";
			this.volume = 0.0;
		}
		public Builder setUserIdentifier(String userIdentifier) {
			this.userIdentifier = userIdentifier;
			return this;
		}
		public Builder setSymbol(String symbol) {
			this.symbol = symbol;
			return this;
		}
		public Builder setVolume(double volume) {
			this.volume = volume;
			return this;
		}
		
		public Position build() {
			Position position = new Position();
			position.setSymbol(this.symbol);
			position.setUserIdentifier(this.userIdentifier);
			position.setVolume(this.volume);
			return position;
		}
		
		
		
	}

}

package production.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;

import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;

import production.enums.PostOutcome;

@XmlRootElement(name ="marketreport")

@XmlAccessorType(XmlAccessType.FIELD)

public class MarketReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement
	private PostOutcome purchaseOutcome;

	public PostOutcome getPurchaseOutcome() {

		return purchaseOutcome;

	}

	public void setPurchaseOutcome(PostOutcome purchaseOutcome) {

		this.purchaseOutcome = purchaseOutcome;

	}

}


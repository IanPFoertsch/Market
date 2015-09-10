package production.entity;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;

import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;

import production.enums.PostOutcome;

@XmlRootElement(name ="marketreport")

@XmlAccessorType(XmlAccessType.FIELD)

public class MarketReport {

	@XmlElement

	private PostOutcome purchaseOutcome;

	public PostOutcome getPurchaseOutcome() {

		return purchaseOutcome;

	}

	public void setPurchaseOutcome(PostOutcome purchaseOutcome) {

		this.purchaseOutcome = purchaseOutcome;

	}

}


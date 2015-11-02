package production.entity;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
@XmlRootElement(name="MarketResolutionReportWrapper")
@XmlAccessorType(XmlAccessType.FIELD)
public class MarketResolutionReportWrapper {
	
	@XmlElement
	private ArrayList<MarketResolutionReport> reports;

	/**
	 * @return the reports
	 */
	public ArrayList<MarketResolutionReport> getReports() {
		return reports;
	}

	/**
	 * @param reports the reports to set
	 */
	public void setReports(ArrayList<MarketResolutionReport> reports) {
		this.reports = reports;
	}
	
}

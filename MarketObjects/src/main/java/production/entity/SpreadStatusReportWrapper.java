package production.entity;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name ="SpreadStatusReportWrapper")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpreadStatusReportWrapper {
	@XmlElement
	private ArrayList<SpreadStatusReport> reports;

	/**
	 * @return the reports
	 */
	public ArrayList<SpreadStatusReport> getReports() {
		return reports;
	}

	/**
	 * @param reports the reports to set
	 */
	public void setReports(ArrayList<SpreadStatusReport> reports) {
		this.reports = reports;
	}
}



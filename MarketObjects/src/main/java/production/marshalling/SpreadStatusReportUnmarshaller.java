package production.marshalling;


import java.io.StringReader;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.Unmarshaller;

import production.entity.SpreadStatusReport;

import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;

@ApplicationScoped
public class SpreadStatusReportUnmarshaller {

	private Unmarshaller unmarshaller;
	
	public SpreadStatusReportUnmarshaller() throws JAXBException {
		this.unmarshaller = JAXBContext.newInstance(SpreadStatusReport.class).createUnmarshaller();
	}
	
	public SpreadStatusReport unmarshall(String xml) throws JAXBException {
		StringReader stringReader = new StringReader(xml);
		return (SpreadStatusReport) this.unmarshaller.unmarshal(stringReader);
	}
}


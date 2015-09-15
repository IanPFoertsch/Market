package production.marshalling;

import java.io.StringWriter;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import production.entity.SpreadStatusReport;
@ApplicationScoped
public class SpreadStatusReportMarshaller {

	private Marshaller marshaller;
	
	public SpreadStatusReportMarshaller() throws JAXBException {
		this.marshaller = JAXBContext.newInstance(SpreadStatusReport.class).createMarshaller();
	}
	
	public String marshal(SpreadStatusReport spreadStatusReport) throws JAXBException {
		StringWriter stringWriter = new StringWriter();
		this.marshaller.marshal(spreadStatusReport, stringWriter);
		return stringWriter.toString();
	}
	
}

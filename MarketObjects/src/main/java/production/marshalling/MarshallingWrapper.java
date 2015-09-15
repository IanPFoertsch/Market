package production.marshalling;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MarshallingWrapper<Type> {

	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	public MarshallingWrapper(Class<Type> type) throws JAXBException
	{
		this.marshaller = JAXBContext.newInstance(type).createMarshaller();
		this.unmarshaller = JAXBContext.newInstance(type).createUnmarshaller();
	}
	
	public String marshall(Type type) throws JAXBException
	{
		StringWriter writer = new StringWriter();
		this.marshaller.marshal(type, writer);
		return writer.toString();
	}
	
	
	/**
	 * unmarshalling requires the use of a cast which introduces an 
	 * unchecked exception due to type erasure
	 * @param xml
	 * @return
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	public Type unmarshal(String xml) throws JAXBException
	{
		StringReader stringReader = new StringReader(xml);
		return (Type) this.unmarshaller.unmarshal(stringReader);
	}
}

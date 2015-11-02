package production.java.com.services;



import java.io.StringReader;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

//import org.jboss.resteasy.logging.Logger;


import production.entity.Post;
import production.enums.PostingType;
import production.jms.PostProducer;

import production.marshalling.PostUnmarshaller;


//Explanation of annotations. 
//@Path maps the url to this service
//produces indicates the mediatype this produces
//request scoped indicates that this service is instantiated for individual requests
//and discarded at the end of it. 
@Path("/order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_HTML)
@RequestScoped
public class OrderService {
	//private Logger logger = Logger.getLogger(OfferService.class);
	
	
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	
	@Inject 
	private PostProducer postProducer;
	
	@Inject 
	private PostUnmarshaller postUnmarshaller;
	
	
	
	@Path("")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@POST
	public Response submitPost(String xml)  {
	
		try {
			Post post = this.postUnmarshaller.unmarshall(xml);
			System.out.println(post.getUserIdentifier());
			System.out.println(post.toString());
			this.postProducer.sendMessage(post);
		}
		catch(JAXBException e) {
			//this.logger.error(e.getMessage());
			System.out.println("Error in sending message via JMS, see application logs");
		}
		return Response.ok().build();
	}

	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	public Response submitJSONPost(Post post) {
		
		try {
			this.postProducer.sendMessage(post);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();	
	}
}

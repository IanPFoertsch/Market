package production.java.com.services;

import java.io.InputStream;
import java.sql.Date;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;


import org.jboss.resteasy.logging.Logger;

import production.entity.Post;
import production.enums.PostingType;
import production.jms.PostProducer;
import production.marketforum.MatchingEngine;
import production.marshalling.PostUnmarshaller;


//Explanation of annotations. 
//@Path maps the url to this service
//produces indicates the mediatype this produces
//request scoped indicates that this service is instantiated for individual requests
//and discarded at the end of it. 
@Path("/offer")
@Produces(MediaType.TEXT_HTML)
@RequestScoped
public class OfferService {
	
	private Logger logger = Logger.getLogger(OfferService.class);
	
	
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Inject
	private MatchingEngine matchingEngine;
	
	@Inject 
	private PostProducer postProducer;
	
	@Inject 
	private PostUnmarshaller postUnmarshaller;
	
	
	
	@Path("")
	@GET
	public String handleGetRequest() {
		Post post = new Post();
		post.setPostingType(PostingType.OFFER);
		post.setPrice(1.0);
		post.setSymbol("GOLD");
		post.setDate(System.currentTimeMillis());
		post.setUserIdentifier("Ian");
		post.setVolume(1.0);
		
		this.matchingEngine.postListing(post);
		logger.info(this.matchingEngine.generateReport());
		return "Hello!";
	}
	
	@Path("/CurrentOffer")
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public Post getCurrentRequest() {
		Post post = new Post();
		post.setPostingType(PostingType.OFFER);
		post.setPrice(1.0);
		post.setSymbol("GOLD");
		post.setDate(System.currentTimeMillis());
		post.setUserIdentifier("Ian");
		post.setVolume(1.0);
		
		return post;
	}
	
	@Path("")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@POST
	public String submitPost(String xml)  {
	
		try {
			Post post = this.postUnmarshaller.unmarshall(xml);
			System.out.println(post.getUserIdentifier());
			System.out.println(post.toString());
			this.postProducer.sendMessage(post);
		}
		catch(JAXBException e) {
			this.logger.error(e.getMessage());
			return "Error in sending message via JMS, see application logs";
		}
		return "Acknowledged";
	}


}

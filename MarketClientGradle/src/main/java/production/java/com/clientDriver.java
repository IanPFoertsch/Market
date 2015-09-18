package production.java.com;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import production.entity.Post;
import production.entity.SpreadStatusReportWrapper;
import production.enums.PostingType;
import production.marshalling.PostMarshaller;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class clientDriver {
	public static void main(String[] args) throws JAXBException, JsonProcessingException {
		
		Post post = new Post();
		
		post.setPostingType(PostingType.OFFER);
		post.setPrice(1.0);
		post.setSymbol("GOLD");
		post.setDate(System.currentTimeMillis());
		post.setUserIdentifier("Ian");
		post.setVolume(1.0);
		
		ObjectMapper mapper = new ObjectMapper();
		
		
		
		String json = mapper.writeValueAsString(post);
		
		
		
		
		
		String targetURL = "http://localhost:8080/MarketServiceGradle";
		String resourceUrl = "/offer";
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(targetURL + resourceUrl);
		
		Response response = target.request().post(Entity.json(post));
		
		
		System.out.println(response.getStatus());
		
		
		
	}
}



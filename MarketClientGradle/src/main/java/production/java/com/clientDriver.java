package production.java.com;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import production.entity.Post;
import production.entity.SpreadStatusReportWrapper;
import production.entity.UserProfile;
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
		
		post.setPostingType(PostingType.ASK);
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
		System.out.println(mapper.writeValueAsString(post));
		response.close();
		
		UserProfile userProfile = new UserProfile();
		userProfile.setUserIdentifier("clerp");
		userProfile.setPassword("merp");
		
		String accountUrl = "/account";
		WebTarget otherTarget = client.target(targetURL + accountUrl);
		Response otherResponse = otherTarget.request().post(Entity.json(userProfile));
		//Response otherResponse = otherTarget.request().get();
		
		//String result = otherResponse.readEntity(String.class);
		//System.out.println(otherResponse.getStatus());
		//System.out.println(result);
		
		
		
		
	}
}



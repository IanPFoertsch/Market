package production.java.com;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBException;

import org.jboss.resteasy.client.ClientRequestFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import production.entity.Position;
import production.entity.PositionCollection;
import production.entity.Post;
import production.entity.UserProfile;
import production.enums.PostingType;

public class PositionDriver {

	
public static void main(String[] args) throws JAXBException, JsonProcessingException {
		


		String targetURL = "http://localhost:8080/MarketServiceGradle";
		String resourceUrl = "/position/user";
	
		ObjectMapper mapper = new ObjectMapper();
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(targetURL + resourceUrl);
		
		/*
		Position position = new Position();
		position.setSymbol("GOLD");
		position.setUserIdentifier("Phil");
		position.setVolume(10);
		
		
		Response response = target.request().post(Entity.entity(position, MediaType.APPLICATION_JSON));
		
		System.out.println(response.getStatus());
		
		response.close();
		
		
		Response otherResponse = target.request().get();
		
		System.out.println(otherResponse.readEntity(PositionCollection.class));
		
		UserProfile userProfile = new UserProfile();
		userProfile.setUserIdentifier("Phil");
		userProfile.setPassword("merp");
		String accountUrl = "/account";
		
		WebTarget otherTarget = client.target(targetURL + accountUrl);
		Response otherResponse = otherTarget.request().post(Entity.json(userProfile));
		
		
		String result = otherResponse.readEntity(String.class);
		System.out.println(otherResponse.getStatus());
		System.out.println(result);
*/		
		
		
		//UserProfile userProfile = new UserProfile();
		//userProfile.setUserIdentifier("Ian");
		//userProfile.setPassword("merp");
		
		
		Response userResponse = target.queryParam("userIdentifier", "Ian").queryParam("password", "merp").request().get(); 	
		System.out.println(userResponse.getStatus());
		System.out.println(userResponse.readEntity(PositionCollection.class).toString());
	}
}

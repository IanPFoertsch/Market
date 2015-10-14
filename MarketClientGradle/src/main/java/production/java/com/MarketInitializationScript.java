package production.java.com;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import production.entity.PositionCollection;
import production.enums.PostingType;

public class MarketInitializationScript {
	public static void main(String[] args) {
		String targetURL = "http://localhost:8080/MarketServiceGradle";
		String resourceUrl = "/position/user";

		ObjectMapper mapper = new ObjectMapper();
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(targetURL + resourceUrl);
		

		String[] bidders = {"Ian", "Christine"};
		String[] askers = {"Tana", "Travis", "Eli"};
		
		String[] symbols = {"SILVER", "GOLD", "COPPER", "IRON", "STEEL", "WHEAT", "OIL", "TIMBER", "STONE"};
		
		
		double askPrice = 20d;
		double bidPrice = 10d;
		
		double askVolume = 10d;
		double bidVolume = 10d;
		
		PostingType[] types = {PostingType.ASK, PostingType.BID};
		long date = System.currentTimeMillis();
		
		
		for(String name: userIdentifiers) {
			for(String symbol: symbols) {
				for
				
			}
		}
		
		//UserProfile userProfile = new UserProfile();
		//userProfile.setUserIdentifier("Ian");
		//userProfile.setPassword("merp");
		
		
		Response userResponse = target.queryParam("userIdentifier", "Phil").queryParam("password", "merp").request().get(); 	
		System.out.println(userResponse.getStatus());
		System.out.println(userResponse.readEntity(PositionCollection.class).toString());
	}

}

package production.java.com;

import java.io.IOException;
import java.util.ArrayList;


import production.entity.Post;
import production.entity.SpreadStatusReport;
import production.entity.SpreadStatusReportWrapper;
import production.enums.PostingType;
import production.marshalling.MarshallingWrapper;
import production.marshalling.PostMarshaller;

public class SpreadReportTestClass {
/*
	private String targetURL = "http://localhost:8080/MarketServiceGradle";
	private String statusReportUrl = "/marketReport";

	private WebTarget statusReportResource; 
	
	public SpreadReportTestClass() {
		Client restClient = ClientBuilder.newClient();
		this.statusReportResource = restClient.target(targetURL + statusReportUrl);
		
	}
	
	public SpreadStatusReportWrapper getStatusReportsFromServer() {
		return this.statusReportResource.request().get(SpreadStatusReportWrapper.class);

	}
	
	
public static void main(String[] args) throws JAXBException {
	SpreadReportTestClass testClass = new SpreadReportTestClass();
	SpreadStatusReportWrapper wrapper = testClass.getStatusReportsFromServer();
	
	ArrayList<SpreadStatusReport> reports = wrapper.getReports();
	for(SpreadStatusReport report: reports) {
		System.out.println(report.getSymbol());
	}
}
*/}



/*
private WebTarget accountResource;
private WebTarget seatResource;

public static void main(String[] args) {
    new RestServiceTestApplication().runSample();
}

public RestServiceTestApplication() {
    Client restclient = ClientBuilder.newClient();

    accountResource = restclient.target(APPLICATION_URL + "account");
    seatResource = restclient.target(APPLICATION_URL + "seat");
}

public void runSample() {
    printAccountStatusFromServer();

    System.out.println("=== Current status: ");
    Collection<SeatDto> seats = getSeatsFromServer();
    printSeats(seats);

    System.out.println("=== Booking: ");
    bookSeats(seats);

    System.out.println("=== Status after booking: ");
    Collection<SeatDto> bookedSeats = getSeatsFromServer();
    printSeats(bookedSeats);

    printAccountStatusFromServer();
}

private void printAccountStatusFromServer() {
    AccountDto account = accountResource.request().get(AccountDto.class);
    System.out.println(account);
}

private Collection<SeatDto> getSeatsFromServer() {
   
}
*/

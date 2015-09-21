package production.java.com.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import production.entity.SpreadStatusReportWrapper;
import production.storage.StatusReportStorage;

@Path("/marketReport")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class StatusReportService {
	
	@Inject
	private StatusReportStorage statusReportStorage;
	
	
	
	
	@Path("")
	@GET
	public SpreadStatusReportWrapper getSpreadStatusReports()
	{try {
		System.out.println(statusReportStorage.getSpreadStatusReportWrapper().getReports().size());
	}
		catch(Exception e) {
			e.printStackTrace();
		}
		return this.statusReportStorage.getSpreadStatusReportWrapper();
	}

	
	
	
	
	
}

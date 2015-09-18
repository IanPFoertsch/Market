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
	{
		return this.statusReportStorage.getSpreadStatusReportWrapper();
	}

	
	
	
	
	
}

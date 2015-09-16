package production.storage;

import javax.enterprise.context.ApplicationScoped;

import production.entity.SpreadStatusReportWrapper;

@ApplicationScoped
public class StatusReportStorage {
	
	private SpreadStatusReportWrapper spreadStatusReportWrapper;

	/**
	 * @return the spreadStatusReportWrapper
	 */
	public SpreadStatusReportWrapper getSpreadStatusReportWrapper() {
		return spreadStatusReportWrapper;
	}

	/**
	 * the setter must be synchronized, to prevent stale or corrupted reads during the setting process,
	 * Therefore, method is synchronized on the object itself, preventing all other calls to the object
	 * while the reportWrapper field is being set
	 * @param spreadStatusReportWrapper the spreadStatusReportWrapper to set
	 * @return 
	 */
	public void setSpreadStatusReportWrapper(SpreadStatusReportWrapper spreadStatusReportWrapper) {
		synchronized(this) {
			this.spreadStatusReportWrapper = spreadStatusReportWrapper;
		}
	}
	
}

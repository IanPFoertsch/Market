package production.enums;

public enum PostingType {
	BID, ASK;
	public static String toString(PostingType type){
		switch(type){
		case BID:
			return "BID";
		case ASK:
			return "ASK";
		default:
			return "UNKNOWN";
		}
	}
}



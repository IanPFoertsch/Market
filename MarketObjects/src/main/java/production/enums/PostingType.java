package production.enums;

public enum PostingType {
	OFFER, REQUEST;
	public static String toString(PostingType type){
		switch(type){
		case OFFER:
			return "OFFER";
		case REQUEST:
			return "REQUEST";
		default:
			return "UNKNOWN";
		}
	}
}



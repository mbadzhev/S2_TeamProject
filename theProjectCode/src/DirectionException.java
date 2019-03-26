
public class DirectionException extends Exception {

	private String message;
	
	public DirectionException(String message) {
		super();
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}

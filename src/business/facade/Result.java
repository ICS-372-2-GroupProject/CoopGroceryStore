package business.facade;

/**
 * This class is used for returning many of the results of the library system's
 * business logic to user interface. At present, the Result object returns an
 * int code,plus values of selected fields of Book and Member. They are the book
 * title, id, borrower id, due date, member name, member phone, and member id.
 * 
 * @author Brahma Dathan
 */
public class Result extends DataTransfer {
	public static final int PRODUCT_NOT_FOUND = 1;
	public static final int ORDER_PLACED = 2;
	public static final int NO_ORDER_FOUND = 3;
	public static final int OPERATION_COMPLETED = 4;
	public static final int OPERATION_FAILED = 5;
	public static final int NO_SUCH_MEMBER = 6;

	private int resultCode;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}
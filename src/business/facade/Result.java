package business.facade;

/**
 * This class is used for returning many of the results of the store system's
 * business logic to user interface. At present, the Result object returns an
 * int code, plus values of selected fields of Product, Member, and Order as
 * listed below. Product: ID, Name, Price, Reorder Level, Stock on hand Member:
 * ID, Name, Address, Phone Number, Date Joined, Fee Order: ID, Product ordered,
 * Quantity Ordered.
 * 
 * @author Brahma Dathan
 */
public class Result extends DataTransfer {

    public static final int OPERATION_COMPLETED = 1;
    public static final int OPERATION_FAILED = 2;
    public static final int NO_SUCH_MEMBER = 3;
    public static final int PRODUCT_NOT_FOUND = 4;
    public static final int PRODUCT_EXISTS = 5;
    public static final int NAME_IN_USE = 6;
    public static final int NO_ORDER_FOUND = 7;
    public static final int ORDER_PLACED = 8;

    private int resultCode;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

}
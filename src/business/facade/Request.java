package business.facade;

import java.util.Calendar;

/**
 * This class is used for requesting many of the results of the store system's
 * business logic to user interface. It is a singleton At present, the Request
 * object returns an int code,plus values of selected fields of Product and
 * Member. They are the product Name, id, reorderLevel , currentPrice, member
 * name, member phone, member id, dateJoined, and fee paid.
 */
public class Request extends DataTransfer {
    private static Request request;
    private Calendar date;
    private Calendar beginDate; // beginning date for range of Transactions
    private Calendar endDate; // ending date for range of Transactions

    /**
     * This is a singleton class. Hence the private constructor.
     */
    private Request() {

    }

    /**
     * Returns the only instance of the class.
     * 
     * @return the only instance
     */
    public static Request instance() {
        if (request == null) {
            request = new Request();
        }
        return request;
    }

    public Calendar getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Calendar beginDate) {
        this.beginDate = beginDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
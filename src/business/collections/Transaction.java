package business.collections;

/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.entities.LineItem;

/**
 * Represents a record of items purchased by a member at checkout as a single
 * transaction.
 * 
 * @author Nalongsone Danddank and G.D. Ponsness
 *
 */

public class Transaction implements Iterable<LineItem>, Serializable {
    private static final long serialVersionUID = 1L;
    private Calendar date;
    private String purchaseTotal;
    private List<LineItem> groceryItems = new LinkedList<LineItem>();

    /**
     * 
     */
    public Transaction() {
        date = new GregorianCalendar();
    }

    /**
     * Checks whether this transaction is on the given date
     * 
     * @param date The date for which transactions are being sought
     * @return true iff the dates match
     */
    public boolean onDate(Calendar date) {
        return ((date.get(Calendar.YEAR) == this.date.get(Calendar.YEAR))
                && (date.get(Calendar.MONTH) == this.date.get(Calendar.MONTH))
                && (date.get(Calendar.DATE) == this.date.get(Calendar.DATE)));
    }

    public boolean betweenTwoDate(Calendar beginDate, Calendar endDate) {

        return this.date.after(beginDate) && this.date.before(endDate);
    }

    /**
     * Returns the date as a String
     * 
     * @return date with month, date, and year
     */
    public String getDate() {
        return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/"
                + date.get(Calendar.YEAR);
    }

    /**
     * Returns an iterator to all products
     * 
     * @return iterator to the collection
     */
    public Iterator<LineItem> iterator() {
        return groceryItems.iterator();
    }

    /**
     * String form of the transaction
     * 
     */
    @Override
    public String toString() {
        return ("Transaction on: " + getDate());
    }
}

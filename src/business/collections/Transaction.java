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
import java.util.LinkedList;
import java.util.List;

import business.entities.Product;

/**
 * Represents a record of items purchased by a member at checkout as a single
 * transaction. Only allows products to be added; transaction must be voided if
 * input error occurs.
 * 
 * @author Nalongsone Danddank and G.D.Ponsness
 * @author (Modified from code written by Brahma Dathan and Sarnath Ramnath)
 *
 */

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private Calendar date;
    private double purchaseTotal;
    private List<LineItem> groceryItems = new LinkedList<LineItem>();

    private class LineItem {
        private String productName;
        private String productPrice;
        private int purchaseAmount;
        private double purchasePrice;

        public LineItem(Product product, int purchaseAmount) {
            this.productName = product.getName();
            this.productPrice = product.getPrice();
            this.purchaseAmount = purchaseAmount;
            purchasePrice = Double.parseDouble(productPrice) * purchaseAmount;
        }

        public double getPurchasePrice() {
            return purchasePrice;
        }

        @Override
        public String toString() {
            String dollarPurchasePrice = String.format("$%.2f", purchasePrice);
            return productName + " \t " + "$" + productPrice + " \t "
                    + purchaseAmount + " \t " + dollarPurchasePrice;
        }
    }

    /**
     * 
     */
    public Transaction() {
        date = new GregorianCalendar();
        purchaseTotal = 0;
    }

    /**
     * 
     * @param product
     * @param purchaseAmount
     * @return
     */
    public String addItem(Product product, int purchaseAmount) {
        LineItem lineItem = new LineItem(product, purchaseAmount);
        groceryItems.add(lineItem);
        purchaseTotal += lineItem.getPurchasePrice();
        return lineItem.toString();
    }

    /**
     * 
     * @return
     */
    public String getPurchaseTotal() {
        return String.format("$%.2f", purchaseTotal);
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
     * String form of the transaction
     * 
     */
    @Override
    public String toString() {
        return ("Transaction on: " + getDate());
    }
}

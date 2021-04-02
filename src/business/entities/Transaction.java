package business.entities;

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

import business.entities.helpers.LineItem;

/**
 * Represents a record of items purchased by a member at checkout as a single
 * transaction. Only allows products to be added; transaction must be voided if
 * input error occurs.
 * 
 * @author Nalongsone Danddank and G.D.Ponsness
 * @author (Modified from code written by Brahma Dathan and Sarnath Ramnath)
 */

public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	private Calendar date;
	private double purchaseTotal;
	private List<LineItem> groceryItems = new LinkedList<LineItem>();

	/**
	 * Initializes a new transaction.
	 */
	public Transaction() {
		date = new GregorianCalendar();
		purchaseTotal = 0;
	}

	/**
	 * Adds a grocery item to the transaction.
	 * 
	 * @param itemForPurchase the product to be purchased
	 * @param purchaseAmount  the amount of items being purchased
	 * @return String which displays sale information for line item
	 */
	public String addItem(Product itemForPurchase, int purchaseAmount) {
		LineItem lineItem = new LineItem(itemForPurchase, purchaseAmount);
		groceryItems.add(lineItem);
		purchaseTotal += lineItem.getPurchasePrice();
		return lineItem.toString();
	}

	/**
	 * Returns the transaction purchase total as a String
	 * 
	 * @return purchaseTotal in proper dollar format
	 */
	public String getPurchaseTotal() {
		return String.format("$%.2f", purchaseTotal);
	}

	/**
	 * Checks whether this transaction is between the given date range
	 * 
	 * @param beginDate lower bound for date range
	 * @param endDate   upper bound for date range
	 * @return true iff date is within range
	 */
	public boolean betweenDates(Calendar beginDate, Calendar endDate) {
		return this.date.after(beginDate) && this.date.before(endDate);
	}

	/**
	 * Returns the transaction date as a String
	 * 
	 * @return date with month, date, and year
	 */
	public String getDate() {
		return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR);
	}

	/**
	 * get LineItems
	 * 
	 * @return groceryItems - Iterator
	 */
	public Iterator<LineItem> getLineItems() {
		return groceryItems.iterator();
	}

	/**
	 * build a Receipt
	 * 
	 * @return receipt - String
	 */
	public String buildReceipt() {
		Iterator<LineItem> iterator = getLineItems();
		String receipt = "";
		while (iterator.hasNext()) {
			receipt += iterator.next().toString() + "\n";
		}
		receipt += "\t\t  TOTAL AMOUNT DUE: " + getPurchaseTotal();
		return receipt;
	}

	/**
	 * 
	 * @return String form of the transaction
	 */
	@Override
	public String toString() {
		return ("Transaction on: " + getDate() + "\n" + buildReceipt());
	}
}

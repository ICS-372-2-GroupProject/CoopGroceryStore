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

/**
 * Order of a product.
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private Product productOrdered;
	private Calendar dateOrdered;
	private int qtyOrdered;
	private boolean orderOutstanding;
	private static final String Order_Num = "O";

	private static int idCounter;

	/**
	 * @param id
	 * @param productOrdered
	 * @param dateOrdered
	 * @param qtyOrdered
	 */
	public Order(Product productOrdered, int qtyOrdered) {
		super();
		this.productOrdered = productOrdered;
		this.dateOrdered = Calendar.getInstance();
		this.qtyOrdered = qtyOrdered;
		this.orderOutstanding = true;
		id = Order_Num + ++idCounter;
	}

	/**
	 * Method to check if an order is outstanding (hasn't been received as a
	 * shipment yet)
	 * 
	 * @return boolean that states if the order is outstanding.
	 */
	public boolean isOutstanding() {
		return orderOutstanding;
	}

	/**
	 * Method to update the orderOustanding field. True means the order hasn't be
	 * received yet. False means the order was received and processed.
	 * 
	 * @param status
	 */
	public void updateStatus(boolean status) {
		this.orderOutstanding = status;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the productOrdered
	 */
	public Product getProductOrdered() {
		return productOrdered;
	}

	/**
	 * @return the dateOrdered
	 */
	public String getDateOrdered() {
		return dateOrdered.getTime().toString();
	}

	/**
	 * @return the qtyOrdered
	 */
	public int getQtyOrdered() {
		return qtyOrdered;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", productOrdered=" + productOrdered + ", dateOrdered=" + dateOrdered
				+ ", qtyOrdered=" + qtyOrdered + "]";
	}

}

package business.collections;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import business.entities.Order;

/**
 * Maintains a list of Order objects.
 */
public class OrderList implements Iterable<Order>, Serializable {
	private List<Order> orders = new LinkedList<Order>();

	/**
	 * Adds a Order object to the list.
	 * 
	 * @param order
	 *            the Order object to be added
	 */
	public boolean addOrder(Order order) {
		orders.add(order);
		return true;
	}

	/**
	 * Removes the hold for a specific book
	 * 
	 * @param bookId
	 *            the book id for removing a hold
	 * @return the removed Hold object if the hold could be removed; otherwise, nul
	 */
	public Order removeOrder(String orderId) {
		for (ListIterator<Order> iterator = orders.listIterator(); iterator.hasNext();) {
			Order order = iterator.next();
			String id = order.getProductOrdered().getId();
			if (id.equals(orderId)) {
				iterator.remove();
				return order;
			}
		}
		return null;
	}

	/**
	 * Returns true iff the holds list is empty.
	 * 
	 * @return iff the list of holds is true;
	 */
	public boolean isEmpty() {
		return orders.isEmpty();
	}

	@Override
	public Iterator<Order> iterator() {
		return orders.iterator();
	}

	/**
	 * Checks whether a order with a given order number exists.
	 * 
	 * @param orderId
	 *            the id of the order
	 * @return true iff the order exists
	 * 
	 */
	public Order search(String orderId) {
		for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			String id = order.getId();
			if (id.equals(orderId)) {
				return order;
			}
		}
		return null;
	}

}
package business.entities.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import business.entities.Order;

/**
 * This class implements the Iterator interface to iterate only on Orders that
 * satisfy a certain predicate.
 * 
 * @author Brahma Dathan
 *
 * @param <T>
 *            the type of the item to be traversed
 */
public class FilteredOrderIterator implements Iterator<Order> {
	private Order item;
	private Predicate<Order> predicate;
	private Iterator<Order> iterator;

	/**
	 * Sets the iterator and predicate fields and positions to the first item that
	 * satisfies the predicate.
	 * 
	 * @param iterator
	 *            the iterator to the list
	 * @param predicate
	 *            specifies the test
	 */
	public FilteredOrderIterator(Iterator<Order> iterator, Predicate<Order> predicate) {
		this.predicate = predicate;
		this.iterator = iterator;
		getNextItem();
	}

	@Override
	public boolean hasNext() {
		return item != null;
	}

	@Override
	public Order next() {
		if (!hasNext()) {
			throw new NoSuchElementException("No such element");
		}
		Order returnValue = item;
		getNextItem();
		return returnValue;
	}

	/*
	 * This method searches for the next item that satisfies the predicate. If none
	 * is found, the item field is set to null.
	 */
	private void getNextItem() {
		while (iterator.hasNext()) {
			item = iterator.next();
			if (predicate.test(item)) {
				return;
			}
		}
		item = null;
	}

}
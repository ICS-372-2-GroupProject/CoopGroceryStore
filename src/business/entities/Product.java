package business.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 * product represents a product of the Store.
 */
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private int stockOnHand;
	private String currentPrice;
	private int reorderLevel;

	/**
	 * Creates a product with the name, given id, current price, and reorder level
	 * 
	 * @param name
	 *            product name
	 * @param id
	 *            product id
	 * @param currentPrice
	 *            product currentPrice
	 * @param reorderLevel
	 *            product reorderLevel
	 */
	public Product(String name, String id, String currentPrice, int reorderLevel) {
		this.name = name;
		this.id = id;
		this.currentPrice = currentPrice;
		this.reorderLevel = reorderLevel;
	}

	/**
	 * @return the stockOnHand
	 */
	public int getStockOnHand() {
		return stockOnHand;
	}

	/**
	 * @param stockOnHand
	 *            the stockOnHand to set
	 */
	public void setStockOnHand(int stockOnHand) {
		this.stockOnHand = stockOnHand;
	}

	/**
	 * @return the currentPrice
	 */
	public String getCurrentPrice() {
		return currentPrice;
	}

	/**
	 * @param currentPrice
	 *            the currentPrice to set
	 */
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	/**
	 * @return the reorderLevel
	 */
	public int getReorderLevel() {
		return reorderLevel;
	}

	/**
	 * @param reorderLevel
	 *            the reorderLevel to set
	 */
	public void setReorderLevel(int reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(currentPrice, id, name, reorderLevel, stockOnHand);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Product)) {
			return false;
		}
		Product other = (Product) obj;
		return Objects.equals(currentPrice, other.currentPrice) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(reorderLevel, other.reorderLevel)
				&& Objects.equals(stockOnHand, other.stockOnHand);
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", id=" + id + ", stockOnHand=" + stockOnHand + ", currentPrice="
				+ currentPrice + ", reorderLevel=" + reorderLevel + "]";
	}

}
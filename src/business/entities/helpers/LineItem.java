package business.entities.helpers;

import java.io.Serializable;

import business.entities.Product;

/**
 * Helper class for Transaction. Stores relevant information for each product
 * purchased.
 * 
 * @author G.D.Ponsness and Nalongsone Danddank
 */

public class LineItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private Product product;
	private int purchaseAmount;
	private double purchasePrice;

	public LineItem(Product product, int purchaseAmount) {
		this.product = product;
		this.purchaseAmount = purchaseAmount;
		purchasePrice = Double.parseDouble(product.getPrice()) * purchaseAmount;
	}

	public Product getProduct() {
		return product;
	}

	public int getPurchaseAmount() {
		return purchaseAmount;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	/**
	 * @return String with sale information in proper dollar format
	 */
	@Override
	public String toString() {
		String formatPurchasePrice = String.format("$%.2f", purchasePrice);
		return product.getName() + "\t\t$" + product.getPrice() + "    " + purchaseAmount + "    "
				+ formatPurchasePrice;
	}
}

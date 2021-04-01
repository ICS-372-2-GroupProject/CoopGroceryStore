package business.entities.helpers;

import business.entities.Product;

/**
 * Helper class for Transaction. Stores relevant information for each product
 * purchased.
 * 
 * @author G.D.Ponsness
 */

public class LineItem {
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

    /**
     * @return String with sale information in proper dollar format
     */
    @Override
    public String toString() {
        String formatPurchasePrice = String.format("$%.2f", purchasePrice);
        return productName + "\t\t$" + productPrice + "    " + purchaseAmount
                + "    " + formatPurchasePrice;
    }
}

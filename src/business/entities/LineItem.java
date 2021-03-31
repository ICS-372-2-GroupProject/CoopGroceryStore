package business.entities;

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

    @Override
    public String toString() {
        String dollarPurchasePrice = String.format("$%.2f", purchasePrice);
        return productName + " \t " + "$" + productPrice + " \t "
                + purchaseAmount + " \t " + dollarPurchasePrice;
    }
}

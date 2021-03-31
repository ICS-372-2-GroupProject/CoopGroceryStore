package business.entities;

public class LineItem {
    private String productName;
    private String productPrice;
    private int purchaseAmount;
    private String purchasePrice;

    private LineItem(String productName, String productPrice,
            int purchaseAmount, String purchasePrice) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.purchaseAmount = purchaseAmount;
        this.purchasePrice = purchasePrice;
    }

    @Override
    public String toString() {
        return productName + " \t " + productPrice + " \t " + purchaseAmount
                + " \t " + purchasePrice;
    }
}

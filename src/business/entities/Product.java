package business.entities;

import java.io.Serializable;

/**
 * product represents a product of the Store.
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String id;
    private String price;
    private int reorderLevel;
    private int stockOnHand;

    /**
     * Creates a product with the name, given id, current price, reorder level,
     * and stock on hand.
     * 
     * @param name         product name
     * @param id           product id
     * @param price        product currentPrice
     * @param reorderLevel product reorderLevel
     * @param stockInHand  product stockOnHand
     */
    public Product(String name, String id, String price, int reorderLevel,
            int stockInHand) {
        this.name = name;
        this.id = id;
        this.stockOnHand = stockInHand;
        this.price = price;
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

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the reorderLevel
     */
    public int getReorderLevel() {
        return reorderLevel;
    }

    /**
     * @param reorderLevel the reorderLevel to set
     */
    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    /**
     * @return the stockOnHand
     */
    public int getStockOnHand() {
        return stockOnHand;
    }

    /**
     * @param stockOnHand the stockOnHand to set
     */
    public void setStockOnHand(int stockOnHand) {
        this.stockOnHand = stockOnHand;
    }

    @Override
    public String toString() {
        return "Product [name=" + name + ", id=" + id + ", stockOnHand="
                + stockOnHand + ", currentPrice=" + price + ", reorderLevel="
                + reorderLevel + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * Checks whether the product is equal to the one supplied
     * 
     * @param object the product which should be compared
     * @return true iff the product ids match
     */

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
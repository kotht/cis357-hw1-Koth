public class PurchaseInfo {
    /** member data     */
    private float price;
    private int quantity;
    private String itemName;

    /** No-Args Constructor     */
    public PurchaseInfo(){
    }

    /**
     * Constructor
     * @param price
     * @param quantity
     * @param itemName
     */
    public PurchaseInfo(float price, int quantity, String itemName){
        this.price = price;
        this.quantity = quantity;
        this.itemName = itemName;
    }

    /**
     * Get Price Method
     * @return
     */
    public float getPrice() {
        return this.price;
    }

    /**
     * Get Quantity Method
     * @return
     */
    public int getQuantity(){
        return this.quantity;
    }

    /**
     * Get Item Name Method
     * @return
     */
    public String getItemName(){
        return this.itemName;
    }
}


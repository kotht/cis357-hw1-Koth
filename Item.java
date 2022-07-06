public class Item {
    /** Member Data     */
    private int itemCode;
    private String itemName;
    private float unitPrice;

    /** No-Args Constructor     */
    public Item(){
    }

    /**
     * Constructor
     * @param itemCode
     * @param itemName
     * @param unitPrice
     */
    public Item(int itemCode, String itemName, float unitPrice){
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
    }

    /**
     * Get Item Code Method
     * @return
     */
    public int getItemCode() {
        return this.itemCode;
    }

    /**
     * Get Item Name Method
     * @return
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * Get Unit Price Method
     * @return
     */
    public float getUnitPrice() {
        return this.unitPrice;
    }
}

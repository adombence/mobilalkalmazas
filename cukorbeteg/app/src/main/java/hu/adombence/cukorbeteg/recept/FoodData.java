package hu.adombence.cukorbeteg.recept;

public class FoodData {

    private String itemName;
    private String itemIngredients;
    private String itemDescription;
    private String itemEnergy;
    private String itemCarbohydrate;
    private int itemImage;

    public FoodData(String itemName, String itemIngredients, String itemDescription, String itemEnergy, String itemCarbohydrate, int itemImage) {
        this.itemName = itemName;
        this.itemIngredients = itemIngredients;
        this.itemDescription = itemDescription;
        this.itemEnergy = itemEnergy;
        this.itemCarbohydrate = itemCarbohydrate;
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPrice() {
        return itemEnergy + " kcal";
    }

    public void setItemPrice(String itemPrice) {
        this.itemEnergy = itemPrice;
    }

    public int getItemImage() {
        return itemImage;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemIngredients() {
        return itemIngredients;
    }

    public void setItemIngredients(String itemIngredients) {
        this.itemIngredients = itemIngredients;
    }

    public String getItemEnergy() {
        return itemEnergy;
    }

    public void setItemEnergy(String itemEnergy) {
        this.itemEnergy = itemEnergy;
    }

    public String getItemCarbohydrate() {
        return itemCarbohydrate;
    }

    public void setItemCarbohydrate(String itemCarbohydrate) {
        this.itemCarbohydrate = itemCarbohydrate;
    }

    @Override
    public String toString() {
        return "FoodData{" +
                "itemName='" + itemName + '\'' +
                ", itemIngredients='" + itemIngredients + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemEnergy='" + itemEnergy + '\'' +
                ", itemCarbohydrate='" + itemCarbohydrate + '\'' +
                ", itemImage=" + itemImage +
                '}';
    }
}

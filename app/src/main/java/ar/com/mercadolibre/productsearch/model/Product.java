package ar.com.mercadolibre.productsearch.model;

import java.util.ArrayList;

public class Product {
    private String id;
    private String title;
    private String subtitle;
    private int sellerId;
    private String categoryId;
    private Double price;
    private Double basePrice;
    private Double originalPrice;
    private String currencyId;
    private String condition;
    private int availableQuantity;
    private String description;
    private String thumbnail;
    private ArrayList<ProductPicture> pictureList;

    public Product() {
        pictureList = new ArrayList<ProductPicture>();
    }

    public Product(String id, String title, String subtitle, int sellerId, String categoryId, Double price, Double basePrice, Double originalPrice, String currencyId, String condition, int availableQuantity, String description, ArrayList<ProductPicture> pictureList) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.price = price;
        this.basePrice = basePrice;
        this.originalPrice = originalPrice;
        this.currencyId = currencyId;
        this.condition = condition;
        this.availableQuantity = availableQuantity;
        this.description = description;
        this.pictureList = pictureList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<ProductPicture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(ArrayList<ProductPicture> pictureList) {
        this.pictureList = pictureList;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

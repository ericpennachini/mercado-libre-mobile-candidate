package ar.com.mercadolibre.productsearch.model;

public class ProductPicture {
    private String id;
    private String url;
    private String secureUrl;
    private String size;
    private String maxSize;
    private String quality;

    public ProductPicture() {
    }

    public ProductPicture(String id, String url, String secureUrl, String size, String maxSize, String quality) {
        this.id = id;
        this.url = url;
        this.secureUrl = secureUrl;
        this.size = size;
        this.maxSize = maxSize;
        this.quality = quality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSecureUrl() {
        return secureUrl;
    }

    public void setSecureUrl(String secureUrl) {
        this.secureUrl = secureUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }
}

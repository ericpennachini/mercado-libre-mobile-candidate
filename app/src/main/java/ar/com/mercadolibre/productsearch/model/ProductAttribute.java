package ar.com.mercadolibre.productsearch.model;

import java.io.Serializable;

public class ProductAttribute implements Serializable {
    private String id;
    private String name;
    private String value;

    public ProductAttribute() {

    }

    public ProductAttribute(String id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

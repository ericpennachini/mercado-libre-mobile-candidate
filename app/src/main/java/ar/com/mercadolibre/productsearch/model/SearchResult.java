package ar.com.mercadolibre.productsearch.model;

import java.util.ArrayList;

public class SearchResult {

    private int pagingTotal;
    private int pagingOffset;
    private int pagingLimit;
    private int pagingPrimaryResults;
    private ArrayList<Product> resultsList;

    public SearchResult() {
        resultsList = new ArrayList<Product>();
    }

    public int getPagingTotal() {
        return pagingTotal;
    }

    public void setPagingTotal(int pagingTotal) {
        this.pagingTotal = pagingTotal;
    }

    public int getPagingOffset() {
        return pagingOffset;
    }

    public void setPagingOffset(int pagingOffset) {
        this.pagingOffset = pagingOffset;
    }

    public int getPagingLimit() {
        return pagingLimit;
    }

    public void setPagingLimit(int pagingLimit) {
        this.pagingLimit = pagingLimit;
    }

    public int getPagingPrimaryResults() {
        return pagingPrimaryResults;
    }

    public void setPagingPrimaryResults(int pagingPrimaryResults) {
        this.pagingPrimaryResults = pagingPrimaryResults;
    }

    public ArrayList<Product> getResultsList() {
        return resultsList;
    }

    public void setResultsList(ArrayList<Product> resultsList) {
        this.resultsList = resultsList;
    }
}

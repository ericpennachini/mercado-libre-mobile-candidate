package ar.com.mercadolibre.productsearch.interfaces;

import ar.com.mercadolibre.productsearch.model.Product;
import ar.com.mercadolibre.productsearch.model.SearchResult;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface SearchServiceRetroFit {

    @GET("sites/MLA/search")
    Call<SearchResult> getProductsByNameExample(@QueryMap Map<String, String> parameters);

    @GET("items/{id}")
    Call<Product> getProductById(@Path("id") String id);

    @GET("items/{id}/description")
    Call<String> getDescriptionByProductId(@Path("id") String productId);

}

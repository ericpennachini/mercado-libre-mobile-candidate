package ar.com.mercadolibre.productsearch.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import ar.com.mercadolibre.productsearch.model.Product;

public class SearchService {


    public ArrayList<Product> getProductsByNameExample(String example) throws Exception {
        try {
            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("q", example);
            // parameters.put("limit", "1000");
            InputStream inputStream = MercadoLibreAPI.call("/sites/MLA/search", MercadoLibreAPI.METHOD_GET, parameters);
            // analizar el objeto devuelto por la API cuando da error
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            for (String line; (line = bf.readLine()) != null;) {
                stringBuilder.append(line);
            }

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

            JSONArray productsList = jsonObject.getJSONArray("results");

            ArrayList<Product> productsListResult = new ArrayList<Product>();

            for (int i = 0; i < productsList.length(); i++) {
                JSONObject productJson = (JSONObject) productsList.get(i);
                Product productObj = new Product();
                productObj.setId(productJson.getString("id"));
                productObj.setTitle(productJson.getString("title"));
                productObj.setCondition(productJson.getString("condition"));
                productObj.setCurrencyId(productJson.getString("currency_id"));
                productObj.setPrice(productJson.getDouble("price"));
                productObj.setAvailableQuantity(productJson.getInt("available_quantity"));
                productObj.setThumbnail(productJson.getString("thumbnail"));
                productsListResult.add(productObj);
            }

            return productsListResult;
        } catch (Exception e) {
            throw e;
        }
    }

    public Product getProductById(String id) throws Exception {

        try {
            InputStream inputStream = MercadoLibreAPI.call("/items/" + id, MercadoLibreAPI.METHOD_GET, null);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            for (String line; (line = bf.readLine()) != null;) {
                stringBuilder.append(line);
            }
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            Gson gson =  new Gson();



        } catch (Exception ex) {
            // tratar excepción
            throw ex;
        }


        return null;
    }

}

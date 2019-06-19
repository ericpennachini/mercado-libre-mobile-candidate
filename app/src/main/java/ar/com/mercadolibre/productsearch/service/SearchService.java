package ar.com.mercadolibre.productsearch.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import ar.com.mercadolibre.productsearch.model.Product;
import ar.com.mercadolibre.productsearch.model.ProductAttribute;
import ar.com.mercadolibre.productsearch.model.ProductPicture;
import ar.com.mercadolibre.productsearch.model.SearchResult;

public class SearchService {

    /**
     * Realiza una llamada a la API para obtener un listado de productos según el texto ingresado
     * por el usuario. Recibe el InputStream de MercadoLibreAPI.call(...), lo convierte a un objeto
     * StringBuilder para luego pasarlo a JSON. A partir de ahí se empieza a convertir este JSON
     * a los objetos necesarios.
     * @param example Texto ingresado por el usuario para buscar coincidencias
     * @param offset Desplazamiento en el listado (depende de limit)
     * @param limit cantidad máxima de productos que va a devolver la API por cada request
     * @return Objeto SearchResult con los datos procesados.
     * @throws Exception
     */
    public SearchResult getProductsByNameExample(String example, Integer offset, Integer limit) throws Exception {
        try {
            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("q", example);
            if (offset != null && limit != null) {
                parameters.put("offset", offset.toString());
                parameters.put("limit", limit.toString());
            }
            // parameters.put("limit", "1000");
            InputStream inputStream = MercadoLibreAPI.call("/sites/MLA/search", MercadoLibreAPI.METHOD_GET, parameters);
            // analizar el objeto devuelto por la API cuando da error
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            for (String line; (line = bf.readLine()) != null;) {
                stringBuilder.append(line);
            }

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

            JSONObject pagingJson = jsonObject.getJSONObject("paging");
            JSONArray productsList = jsonObject.getJSONArray("results");

            SearchResult searchResult = new SearchResult();
            searchResult.setPagingTotal(pagingJson.getInt("total"));
            searchResult.setPagingOffset(pagingJson.getInt("offset"));
            searchResult.setPagingLimit(pagingJson.getInt("limit"));
            searchResult.setPagingPrimaryResults(pagingJson.getInt("primary_results"));

            ArrayList<Product> productsListResult = new ArrayList<Product>();

            for (int i = 0; i < productsList.length(); i++) {
                JSONObject productJson = (JSONObject) productsList.get(i);
                Product productObj = new Product();
                productObj.setId(productJson.getString("id"));
                productObj.setTitle(productJson.getString("title"));
                productObj.setCondition(productJson.getString("condition"));
                productObj.setCurrencyId(productJson.getString("currency_id"));
                productObj.setPrice(productJson.getDouble("price"));
                productObj.setOriginalPrice(productJson.getString("original_price") == "null" ? 0.0 : productJson.getDouble("original_price"));
                productObj.setAvailableQuantity(productJson.getInt("available_quantity"));
                productObj.setFreeShipping(productJson.getJSONObject("shipping").getBoolean("free_shipping"));
                productObj.setThumbnail(productJson.getString("thumbnail"));
                productsListResult.add(productObj);
            }
            searchResult.setResultsList(productsListResult);

            return searchResult;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Realiza una llamada a la API para obtener un producto según un ID determinado.
     * Recibe el InputStream de MercadoLibreAPI.call(...), lo convierte a un objeto StringBuilder
     * para luego pasarlo a JSON. A partir de ahí se empieza a convertir este JSON a los objetos
     * necesarios.
     * @param id ID de producto a buscar
     * @return Objeto Product con toda la información del producto encontrado.
     * @throws Exception
     */
    public Product getProductById(String id) throws Exception {

        try {
            InputStream inputStream = MercadoLibreAPI.call("/items/" + id, MercadoLibreAPI.METHOD_GET, null);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            for (String line; (line = bf.readLine()) != null;) {
                stringBuilder.append(line);
            }
            JSONObject productJson = new JSONObject(stringBuilder.toString());

            Product productObj = new Product();

            productObj.setId(productJson.getString("id"));
            productObj.setTitle(productJson.getString("title"));
            productObj.setPrice(productJson.getDouble("price"));
            productObj.setOriginalPrice(
                    productJson.getString("original_price") == "null"
                            ? 0.0
                            : productJson.getDouble("original_price")
            );
            productObj.setCurrencyId(productJson.getString("currency_id"));
            productObj.setCondition(productJson.getString("condition"));
            productObj.setThumbnail(productJson.getString("thumbnail"));
            productObj.setSoldQuantity(productJson.getInt("sold_quantity"));
            productObj.setAvailableQuantity(productJson.getInt("available_quantity"));
            productObj.setFreeShipping(productJson.getJSONObject("shipping").getBoolean("free_shipping"));
            productObj.setWarranty(productJson.getString("warranty") == "null" ? "" : productJson.getString("warranty"));
            JSONArray picturesJson = productJson.getJSONArray("pictures");
            for (int i = 0; i < picturesJson.length(); i++) {
                ProductPicture pictureObj = new ProductPicture();
                JSONObject pictureJson = (JSONObject) picturesJson.get(i);
                pictureObj.setId(pictureJson.getString("id"));
                pictureObj.setUrl(pictureJson.getString("url"));
                pictureObj.setQuality(pictureJson.getString("quality"));
                pictureObj.setMaxSize(pictureJson.getString("max_size"));
                pictureObj.setSize(pictureJson.getString("size"));
                pictureObj.setSecureUrl(pictureJson.getString("secure_url"));
                productObj.getPictureList().add(pictureObj);
            }
            JSONArray attributesJson = productJson.getJSONArray("attributes");
            for (int i = 0; i < attributesJson.length(); i++) {
                JSONObject attributeJson = attributesJson.getJSONObject(i);
                ProductAttribute attribute = new ProductAttribute(
                        attributeJson.getString("id"),
                        attributeJson.getString("name"),
                        (attributeJson.getString("value_name") == "null" ? " - " : attributeJson.getString("value_name"))
                );
                productObj.getAttributes().add(attribute);
            }

            return productObj;

        } catch (Exception ex) {
            // tratar excepción
            throw ex;
        }
    }

    /**
     * Realiza una llamada a la API para obtener la descripción de un producto según un ID de
     * producto determinado. Recibe el InputStream de MercadoLibreAPI.call(...), lo convierte a un
     * objeto StringBuilder para luego pasarlo a JSON. Luego se obtiene el campo 'plain_text' que
     * contiene la descripción, o bien se busca que en la respuesta no haya un campo 'error' (esto
     * indica que el producto no tiene descripción).
     * @param productId ID de producto del cual se quiere obtener la descripción
     * @return Objeto String con la descripción del producto (vacío si no hay descripción)
     * @throws Exception
     */
    public String getDescriptionByProductId(String productId) throws Exception {
        try {
            InputStream inputStreamDesc = MercadoLibreAPI.call("/items/" + productId + "/description", MercadoLibreAPI.METHOD_GET, null);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStreamDesc));
            StringBuilder stringBuilder = new StringBuilder();
            for (String line; (line = bf.readLine()) != null;) {
                stringBuilder.append(line);
            }
            JSONObject descriptionJson = new JSONObject(stringBuilder.toString());

            if (descriptionJson.has("error")) {
                return "";
            } else {
                return descriptionJson.getString("plain_text");
            }
        } catch (Exception e) {
            throw e;
        }
    }

}

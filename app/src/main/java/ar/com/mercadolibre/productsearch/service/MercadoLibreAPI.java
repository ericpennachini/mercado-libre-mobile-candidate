package ar.com.mercadolibre.productsearch.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MercadoLibreAPI {

    public static final String BASE_URL = "https://api.mercadolibre.com";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    /**
     * Método static que realiza una llamada a la API de MercadoLibre con los parámetros indicados
     * y devuelve un objeto InputStream
     * @param endpoint Endpoint específico de la API
     * @param httpMethod Método HTTP (GET o POST)
     * @param parameters Objeto HashMap con los parámetros que se usarán en la llamada
     * @return InputStream con los datos obtenidos
     * @throws Exception
     */
    public static InputStream call(String endpoint, String httpMethod, HashMap<String, String> parameters) throws Exception {
        HttpURLConnection connection = null;
        String urlString = BASE_URL + endpoint;
        try {
            if (parameters != null) {
                String strParameters = "";
                byte[] postReq;
                int counter = 0;

                for (HashMap.Entry<String, String> par : parameters.entrySet()) {
                    strParameters += par.getKey() + "=" + par.getValue();
                    strParameters += counter < (parameters.entrySet().size() - 1) ? "&" : "";
                    counter++;
                }
                switch (httpMethod) {
                    case METHOD_GET:
                        urlString += "?" + strParameters;
                        break;
                    case METHOD_POST:
//                        postReq = strParameters.getBytes();
//                        connection.setDoOutput(true);
                        break;
                }
            }

            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(httpMethod);
            connection.setRequestProperty("Content-Type", "application/json");
            InputStream inputStream;
            int status = connection.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = connection.getErrorStream();
            } else {
                inputStream = connection.getInputStream();
            }

            return inputStream;

        } catch (Exception ex) {
            throw ex;
        }

    }

}

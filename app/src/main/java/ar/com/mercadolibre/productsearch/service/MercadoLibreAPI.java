package ar.com.mercadolibre.productsearch.service;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import ar.com.mercadolibre.productsearch.model.Product;

public class MercadoLibreAPI {

    public static final String BASE_URL = "https://api.mercadolibre.com";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    // public static Object RESULT;

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

            InputStream inputStream = connection.getInputStream();
            // cuando se retorna error de la API, analizar el objeto devuelto
            return inputStream;


            // System.out.println(inputStream.toString());
        } catch (Exception ex) {
            throw ex;
        }

    }

//    private static class HTTPAsyncTask extends AsyncTask<String, Void, Void> {
//
//
//        @Override
//        protected Void doInBackground(String... strings) {
//            try {
//                String urlString = strings[0];
//                URL url = new URL(urlString);
//                HttpURLConnection connection = null;
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod(strings[1]);
//                connection.setRequestProperty("Content-Type", "application/json");
//
//
//                InputStream inputStream = connection.getInputStream();
//                RESULT = inputStream.toString();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }

}

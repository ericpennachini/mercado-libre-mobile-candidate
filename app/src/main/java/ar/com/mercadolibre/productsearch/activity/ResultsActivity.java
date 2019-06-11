package ar.com.mercadolibre.productsearch.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ar.com.mercadolibre.productsearch.R;
import ar.com.mercadolibre.productsearch.adapter.ProductListAdapter;
import ar.com.mercadolibre.productsearch.model.Product;
import ar.com.mercadolibre.productsearch.service.SearchService;
import ar.com.mercadolibre.productsearch.utils.Utils;

public class ResultsActivity extends AppCompatActivity {

    private String queryText;
    private SearchService searchService;
    private ProgressBar pb;
    // private ArrayList<Product> productResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // productResult = new ArrayList<Product>();

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.VISIBLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Resultados de búsqueda");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        queryText = bundle.getString("queryText");
        // probar si llega el string
        // Utils.showToast(getApplicationContext(), queryText, Toast.LENGTH_SHORT);

        searchService = new SearchService();
        initializeListView();

    }

    private void initializeListView() {
        // llamar al servicio que trae los datos para poder mostrar los resultados

        try {
            AsyncSearch asyncSearch = new AsyncSearch();
            asyncSearch.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo.isConnected();
    }

    private class AsyncSearch extends AsyncTask<Void, Void, ArrayList<Product>> {

        @Override
        protected ArrayList<Product> doInBackground(Void... voids) {
            try {
                ArrayList<Product> productResult = searchService.getProductsByNameExample(queryText);
                return productResult;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Product> products) {
            ListView lstProductSearchResults = (ListView) findViewById(R.id.lstProductSearchResults);
            TextView txvNoResults = (TextView) findViewById(R.id.txvNoResults);

            if (products.size() > 0) {
                txvNoResults.setVisibility(View.GONE);
                lstProductSearchResults.setVisibility(View.VISIBLE);
                ArrayList<Object> productList = new ArrayList<Object>();
                productList.addAll(products);
                ProductListAdapter productListAdapter = new ProductListAdapter(getApplicationContext(), productList);
                lstProductSearchResults.setAdapter(productListAdapter);
                productListAdapter.notifyDataSetChanged();
            } else {
                txvNoResults.setVisibility(View.VISIBLE);
                txvNoResults.setText("No hay resultados");
                lstProductSearchResults.setVisibility(View.GONE);
            }


            /*
            TextView txvResultsSize = (TextView) findViewById(R.id.txvResultsSize);
            if (products != null) {
                txvResultsSize.setText("Resultados: " + products.size());
            } else {
                Utils.showToast(getApplicationContext(), "Ocurrió un error", Toast.LENGTH_LONG);
            }
            */
            pb.setVisibility(View.GONE);
        }
    }
}

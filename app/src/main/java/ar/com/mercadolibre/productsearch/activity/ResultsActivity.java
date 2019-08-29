package ar.com.mercadolibre.productsearch.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.mercadolibre.productsearch.interfaces.SearchServiceRetroFit;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;

import ar.com.mercadolibre.productsearch.R;
import ar.com.mercadolibre.productsearch.adapter.ProductListAdapter;
import ar.com.mercadolibre.productsearch.interfaces.ICustomActivity;
import ar.com.mercadolibre.productsearch.model.Product;
import ar.com.mercadolibre.productsearch.model.SearchResult;
import ar.com.mercadolibre.productsearch.service.SearchService;
import ar.com.mercadolibre.productsearch.utils.Utils;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultsActivity extends AppCompatActivity implements ICustomActivity {
    private String queryText;
    private SearchService searchService;
    private ProgressBar pb;
    private ListView lstProductSearchResults;
    private SearchResult searchCurrent;
    private FloatingActionButton fabNext;
    private FloatingActionButton fabPrev;
    private ImageView imgNoConnection;
    private TextView txvNoConnection;
    private Button btnRetrySearch;
    private int currentPage;
    private int totalPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        currentPage = 1;
        totalPages = 0;
        searchCurrent = null;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Resultados de búsqueda");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        queryText = bundle.getString("queryText");

        searchService = new SearchService();

        this.initializeLayoutElements();
        this.startActivityWork();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void initializeLayoutElements() {
        pb = findViewById(R.id.progressBar1);
        btnRetrySearch = findViewById(R.id.btnRetrySearchRes);
        btnRetrySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWork();
            }
        });
        lstProductSearchResults = findViewById(R.id.lstProductSearchResults);
        lstProductSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txvItemID = (TextView) view.findViewById(R.id.txvItemID);
                Bundle bundle = new Bundle();
                bundle.putString("selectedItemID", txvItemID.getText().toString());
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        fabNext = findViewById(R.id.fabResultsNext);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                int offset = searchCurrent.getPagingOffset();
                int limit = searchCurrent.getPagingLimit();
                searchCurrent.setPagingOffset(offset + limit);
                startActivityWork();
            }
        });
        fabPrev = findViewById(R.id.fabResultsPrev);
        fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage--;
                int offset = searchCurrent.getPagingOffset();
                int limit = searchCurrent.getPagingLimit();
                searchCurrent.setPagingOffset(offset - limit);
                startActivityWork();
            }
        });
        imgNoConnection = findViewById(R.id.imgNoConnectionRes);
        txvNoConnection = findViewById(R.id.txvNoConnectionRes);
    }

    @Override
    public void startActivityWork() {
        lstProductSearchResults.setVisibility(View.GONE);
        if (!Utils.checkNetworkConnection(this)) {
            pb.setVisibility(View.GONE);
            fabPrev.hide();
            fabNext.hide();
            this.showNoConnectionStatus();
        } else {
            pb.setVisibility(View.VISIBLE);
            this.hideNoConnectionStatus();
            fabPrev.show();
            fabNext.show();
            fabNext.setEnabled(false);
            fabPrev.setEnabled(false);
            Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            SearchServiceRetroFit service = retrofit.create(SearchServiceRetroFit.class);

            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("q", queryText);
            if (searchCurrent != null) {
                parameters.put("offset", String.valueOf(searchCurrent.getPagingOffset()));
                parameters.put("limit", String.valueOf(searchCurrent.getPagingLimit()));
            }

            Call<SearchResult> call = service.getProductsByNameExample(parameters);
            call.enqueue(new Callback<SearchResult>() {
                @Override
                public void onResponse(final Call<SearchResult> call, final Response<SearchResult> response) {
                    switch (response.code()) {
                    case 200:
                        TextView txvNoResults = findViewById(R.id.txvNoResults);
                        SearchResult searchResult = response.body();
                        searchCurrent = searchResult;

                        ArrayList<Product> products = searchResult.getResultsList();

                        if (!products.isEmpty()) {
                            // Se controla la cantidad total, ya que si es más de 1000 la API
                            // pone una restricción al operar sin ACCESS_TOKEN
                            int maxPages = (searchCurrent.getPagingTotal() > 1000 ? 1000 : searchCurrent.getPagingTotal());
                            totalPages = maxPages / searchCurrent.getPagingLimit();
                            if (searchCurrent.getPagingPrimaryResults() % searchCurrent.getPagingLimit() > 0) {
                                totalPages++;
                            }
                            ActionBar actionBar = getSupportActionBar();
                            actionBar.setSubtitle(currentPage + " de " + totalPages);
                            lstProductSearchResults.setVisibility(View.VISIBLE);
                            txvNoResults.setVisibility(View.GONE);
                            ArrayList<Object> productList = new ArrayList<Object>();
                            productList.addAll(products);
                            ProductListAdapter productListAdapter = new ProductListAdapter(getApplicationContext(), productList);
                            lstProductSearchResults.setAdapter(productListAdapter);
                            productListAdapter.notifyDataSetChanged();
                        } else {
                            lstProductSearchResults.setVisibility(View.GONE);
                            txvNoResults.setVisibility(View.VISIBLE);
                            txvNoResults.setText(getResources().getString(R.string.search_no_results));
                        }

                        endActivityWork();
                        break;
                    }
                }

                @Override
                public void onFailure(final Call<SearchResult> call, final Throwable t) {

                }
            });
            /*
            try {
                AsyncSearch asyncSearch = new AsyncSearch();
                asyncSearch.execute();
            } catch (Exception e) {
                Utils.showDialog(getApplicationContext(), "No se pudo iniciar la búsqueda", "Error");
            }
             */
        }
    }

    @Override
    public void endActivityWork() {
        fabNext.setEnabled(true);
        fabPrev.setEnabled(true);
        if (searchCurrent == null || searchCurrent.getPagingOffset() == 0) {
            fabPrev.hide();
        } else {
            fabPrev.show();
        }
        if (searchCurrent == null || currentPage < totalPages) {
            fabNext.show();
        } else {
            fabNext.hide();
        }

        pb.setVisibility(View.GONE);
    }

    @Override
    public void showNoConnectionStatus() {
        imgNoConnection.setVisibility(View.VISIBLE);
        txvNoConnection.setVisibility(View.VISIBLE);
        btnRetrySearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoConnectionStatus() {
        imgNoConnection.setVisibility(View.GONE);
        txvNoConnection.setVisibility(View.GONE);
        btnRetrySearch.setVisibility(View.GONE);
    }


    private class AsyncSearch extends AsyncTask<Void, Void, SearchResult> {

        @Override
        protected SearchResult doInBackground(Void... voids) {
            try {
                SearchResult search;
                if (searchCurrent == null) {
                    search = searchService.getProductsByNameExample(queryText, null, null);
                } else {
                    search = searchService.getProductsByNameExample(queryText, searchCurrent.getPagingOffset(), searchCurrent.getPagingLimit());
                }
                return search;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(SearchResult searchResult) {
            TextView txvNoResults = findViewById(R.id.txvNoResults);
            searchCurrent = searchResult;
            if (searchResult != null) {
                ArrayList<Product> products = searchResult.getResultsList();

                if (products.size() > 0) {
                    // Se controla la cantidad total, ya que si es más de 1000 la API
                    // pone una restricción al operar sin ACCESS_TOKEN
                    int maxPages = (searchCurrent.getPagingTotal() > 1000 ? 1000 : searchCurrent.getPagingTotal());
                    totalPages = maxPages / searchCurrent.getPagingLimit();
                    if (searchCurrent.getPagingPrimaryResults() % searchCurrent.getPagingLimit() > 0) {
                        totalPages++;
                    }
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setSubtitle(currentPage + " de " + totalPages);
                    lstProductSearchResults.setVisibility(View.VISIBLE);
                    txvNoResults.setVisibility(View.GONE);
                    ArrayList<Object> productList = new ArrayList<Object>();
                    productList.addAll(products);
                    ProductListAdapter productListAdapter = new ProductListAdapter(getApplicationContext(), productList);
                    lstProductSearchResults.setAdapter(productListAdapter);
                    productListAdapter.notifyDataSetChanged();
                } else {
                    lstProductSearchResults.setVisibility(View.GONE);
                    txvNoResults.setVisibility(View.VISIBLE);
                    txvNoResults.setText(getResources().getString(R.string.search_no_results));
                }

                endActivityWork();
            } else {
                Utils.showToast(getApplicationContext(), "No se pueden mostrar los datos requeridos", Toast.LENGTH_LONG);
            }
        }
    }
}

package ar.com.mercadolibre.productsearch.activity;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.mercadolibre.productsearch.R;
import ar.com.mercadolibre.productsearch.interfaces.ICustomActivity;
import ar.com.mercadolibre.productsearch.service.SearchService;
import ar.com.mercadolibre.productsearch.utils.Utils;

public class DescriptionActivity extends AppCompatActivity implements ICustomActivity {

    private String productID;
    private SearchService searchService;
    private ProgressBar progressBar;
    private TextView txvDescription;
    private ImageView imgNoConnectionDesc;
    private TextView txvNoConnectionDesc;
    private Button btnRetrySearchDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Descripción del producto");
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.initializeLayoutElements();

        progressBar.setVisibility(View.VISIBLE);
        txvDescription.setVisibility(View.GONE);

        productID = getIntent().getExtras().getString("productID");

        searchService = new SearchService();

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
        progressBar = findViewById(R.id.progressBar3);
        txvDescription = findViewById(R.id.txvItemDescription);
        imgNoConnectionDesc = findViewById(R.id.imgNoConnectionDesc);
        txvNoConnectionDesc = findViewById(R.id.txvNoConnectionDesc);
        btnRetrySearchDesc = findViewById(R.id.btnRetrySearchDesc);
        btnRetrySearchDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWork();
            }
        });
    }

    @Override
    public void startActivityWork() {
        progressBar.setVisibility(View.VISIBLE);
        txvDescription.setVisibility(View.GONE);
        if (!Utils.checkNetworkConnection(getApplicationContext())) {
            progressBar.setVisibility(View.GONE);
            showNoConnectionStatus();
        } else {
            hideNoConnectionStatus();
            AsyncDescription asyncDescription = new AsyncDescription();
            asyncDescription.execute();
        }
    }

    @Override
    public void endActivityWork() {
        txvDescription.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoConnectionStatus() {
        imgNoConnectionDesc.setVisibility(View.VISIBLE);
        txvNoConnectionDesc.setVisibility(View.VISIBLE);
        btnRetrySearchDesc.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoConnectionStatus() {
        imgNoConnectionDesc.setVisibility(View.GONE);
        txvNoConnectionDesc.setVisibility(View.GONE);
        btnRetrySearchDesc.setVisibility(View.GONE);
    }

    private class AsyncDescription extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String description = searchService.getDescriptionByProductId(productID);
                return description;
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                txvDescription.setText(s.equals("") ? "El producto no tiene descripción" : s);
            } else {
                Utils.showToast(getApplicationContext(), "No se pudo obtener la descripción", Toast.LENGTH_LONG);
            }
            endActivityWork();
        }
    }
}

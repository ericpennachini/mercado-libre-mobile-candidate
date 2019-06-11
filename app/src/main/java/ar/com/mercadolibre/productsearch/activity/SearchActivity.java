package ar.com.mercadolibre.productsearch.activity;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import ar.com.mercadolibre.productsearch.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Búsqueda");

//        Intent intent = getIntent();
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            Toast toast = Toast.makeText(getApplicationContext(), intent.getStringExtra(SearchManager.QUERY), Toast.LENGTH_LONG);
//            toast.show();
//        }
//        Intent intent = getIntent();
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            Toast toast = Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT);
//            toast.show();
//        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast toast = Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

package ar.com.mercadolibre.productsearch.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import ar.com.mercadolibre.productsearch.R;
import ar.com.mercadolibre.productsearch.adapter.AttributeListAdapter;
import ar.com.mercadolibre.productsearch.model.ProductAttribute;

public class ProductAttributesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_attributes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Características");
        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayList<ProductAttribute> attributes = (ArrayList<ProductAttribute>) getIntent().getExtras().get("product_attributes");

        ArrayList<Object> attrList = new ArrayList<Object>();
        attrList.addAll(attributes);
        AttributeListAdapter attrAdapter = new AttributeListAdapter(getApplicationContext(), attrList);
        ListView lstItemAttributes = findViewById(R.id.lstItemAttributes);
        lstItemAttributes.setAdapter(attrAdapter);
        attrAdapter.notifyDataSetChanged();
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
}

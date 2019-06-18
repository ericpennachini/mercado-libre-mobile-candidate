package ar.com.mercadolibre.productsearch.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

import ar.com.mercadolibre.productsearch.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Acerca de esta aplicación");
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView txvAboutAppName = findViewById(R.id.txvAboutAppName);
        txvAboutAppName.setText(getResources().getString(R.string.app_name));

        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        TextView txvAboutYear = findViewById(R.id.txvAboutYear);
        txvAboutYear.setText(year.toString());
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

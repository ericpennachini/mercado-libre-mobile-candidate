package ar.com.mercadolibre.productsearch.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import ar.com.mercadolibre.productsearch.R;
import ar.com.mercadolibre.productsearch.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Product Search");

        Button button = (Button) findViewById(R.id.btnExecuteSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.edtSearchText);
                String queryText = editText.getText().toString();
                if (queryText.length() == 0) {
                    Utils.showToast(getApplicationContext(), "El texto de la búsqueda no puede ser vacío", Toast.LENGTH_SHORT);
                } else {
                    // crear bundle y pasarlo al siguiente activity cuando se inicia
                    Bundle bundle = new Bundle();
                    bundle.putString("queryText", queryText);
                    // crear intento para iniciar el activity de resultados
                    Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });

        // prueba para que la llamada se ejecute en el mismo hilo
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast toast;
        switch (item.getItemId()) {
            case R.id.action_settings:
                toast = Toast.makeText(getApplicationContext(), "Configuración", Toast.LENGTH_SHORT);
                toast.show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

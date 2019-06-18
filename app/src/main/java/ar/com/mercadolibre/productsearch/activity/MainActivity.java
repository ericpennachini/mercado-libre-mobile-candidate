package ar.com.mercadolibre.productsearch.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.mercadolibre.productsearch.R;
import ar.com.mercadolibre.productsearch.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private String queryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edtSearchText);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Product Search");

        Button button = (Button) findViewById(R.id.btnExecuteSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });

        editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66) {
                    startSearch();
                }
                return true;
            }
        });

    }

    private void startSearch() {
        queryText = editText.getText().toString();
        if (queryText.length() == 0) {
            Utils.showToast(getApplicationContext(), "El texto de la búsqueda no puede ser vacío", Toast.LENGTH_SHORT);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("queryText", queryText);
            Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView imgNoConnection = findViewById(R.id.imgNoConnectionMain);
        TextView txvNoConnection = findViewById(R.id.txvNoConnectionMain);
        if (!Utils.checkNetworkConnection(this)) {
            imgNoConnection.setVisibility(View.VISIBLE);
            txvNoConnection.setVisibility(View.VISIBLE);
        } else {
            imgNoConnection.setVisibility(View.GONE);
            txvNoConnection.setVisibility(View.GONE);
        }
        editText.clearFocus();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view instanceof EditText) {
            // quitar el foco del editText si hago clic afuera
            editText.clearFocus();
            // esconder el teclado
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
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
            case R.id.action_about:
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        System.exit(0);
//    }
}

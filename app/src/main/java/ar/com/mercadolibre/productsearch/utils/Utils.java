package ar.com.mercadolibre.productsearch.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    public static void showToast(Context context, String text, int duration) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}

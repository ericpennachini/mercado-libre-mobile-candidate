package ar.com.mercadolibre.productsearch.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Utils {

    /**
     * Muestra un toast
     * @param context Contexto actual
     * @param text Texto a mostrar
     * @param duration Duración
     */
    public static void showToast(Context context, String text, int duration) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Muestra un cuadro de diálogo
     * @param context Contexto actual
     * @param text Texto a mostrar
     * @param title Título del diálogo
     */
    public static void showDialog(Context context, String text, String title) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(text).setTitle(title).setPositiveButton("OK", null).create().show();
    }

    /**
     * Obtiene el símbolo de una moneda dado su abreviación
     * @param currency Abreviación de la moneda
     * @return Símbolo de la moneda
     */
    public static String getCurrencySymbol(String currency) {
        String result = "";
        switch (currency) {
            case "ARS":
                result = "$";
                break;
            case "USD":
                result = "U$S";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * Controla el estado de conexión a Internet
     * @param context Contexto actual
     * @return <b>true</b> si hay conexión; <b>false</b> si no hay conexión
     */
    public static boolean checkNetworkConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        } else {
            return networkInfo.isConnected();
        }
    }
}

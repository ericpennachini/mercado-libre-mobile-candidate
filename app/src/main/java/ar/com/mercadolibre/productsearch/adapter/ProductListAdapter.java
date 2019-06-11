package ar.com.mercadolibre.productsearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ar.com.mercadolibre.productsearch.R;

import java.util.ArrayList;

import ar.com.mercadolibre.productsearch.model.Product;

public class ProductListAdapter extends BaseAdapter {

    private ArrayList<Object> data = new ArrayList<Object>();
    private LayoutInflater inflater;

    public ProductListAdapter(Context context, ArrayList<Object> list) {
        data.addAll(list);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = (Product) this.getItem(position);

        if (convertView == null){
            convertView = inflater.inflate(R.layout.product_item, parent,false);
        }

        TextView txvItemID = (TextView) convertView.findViewById(R.id.txvItemID);
        TextView txvItemTitle = (TextView) convertView.findViewById(R.id.txvItemTitle);
        TextView txvItemDisp = (TextView) convertView.findViewById(R.id.txvItemDisp);
        TextView txvItemPrice = (TextView) convertView.findViewById(R.id.txvItemPrice);
        txvItemID.setText(product.getId());
        txvItemTitle.setText(product.getTitle());
        txvItemPrice.setText("$ " + product.getPrice());
        txvItemDisp.setText("Disponible: " + product.getAvailableQuantity());

        return convertView;
    }
}

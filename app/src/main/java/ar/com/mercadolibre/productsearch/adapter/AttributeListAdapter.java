package ar.com.mercadolibre.productsearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.mercadolibre.productsearch.R;
import ar.com.mercadolibre.productsearch.model.ProductAttribute;

public class AttributeListAdapter extends BaseAdapter {

    private ArrayList<Object> data = new ArrayList<Object>();
    private LayoutInflater inflater;

    public AttributeListAdapter(Context context, ArrayList<Object> list) {
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
        ProductAttribute attribute = (ProductAttribute) this.getItem(position);

        if (convertView == null){
            convertView = inflater.inflate(R.layout.attribute_item, parent,false);
        }

        TextView txvAttrName = convertView.findViewById(R.id.txvAttrName);
        txvAttrName.setText(attribute.getName());
        TextView txvAttrValue = convertView.findViewById(R.id.txvAttrValue);
        txvAttrValue.setText(attribute.getValue());

        // para no poder hacer clic en los elementos
        convertView.setEnabled(false);

        return convertView;
    }
}

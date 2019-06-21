package ar.com.mercadolibre.productsearch.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ar.com.mercadolibre.productsearch.R;

import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

// import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.w3c.dom.Text;

import ar.com.mercadolibre.productsearch.model.Product;
import ar.com.mercadolibre.productsearch.utils.Utils;

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
        TextView txvItemTitle = (TextView) convertView.findViewById(R.id.txvItemDetTitle);
        TextView txvItemDisp = (TextView) convertView.findViewById(R.id.txvItemDisp);
        TextView txvItemPrice = (TextView) convertView.findViewById(R.id.txvItemDetPrice);
        TextView txvDiscount = (TextView) convertView.findViewById(R.id.txvDiscount);
        TextView txvFreeShipping = (TextView) convertView.findViewById(R.id.txvFreeShippingRes);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgItemPicture);

        txvItemID.setText(product.getId());
        txvItemTitle.setText(product.getTitle());
        String pattern = (product.getPrice() % 1 == 0 ? "###,###" : "###,###.00");
        String itemPrice = Utils.getCurrencySymbol(product.getCurrencyId())
                + " " + new DecimalFormat(pattern).format(product.getPrice());
        txvItemPrice.setText(itemPrice);
        String itemDisp = convertView.getResources().getString(R.string.item_disp_pre) + " " + product.getAvailableQuantity();
        txvItemDisp.setText(itemDisp);
        if (product.getOriginalPrice().equals(0.0)) {
            txvDiscount.setText("");
        } else {
            Long discount = Math.round(100 - (product.getPrice() * 100) / product.getOriginalPrice());
            txvDiscount.setText(discount.toString() + convertView.getResources().getString(R.string.item_discount_post));
        }
        if (!product.isFreeShipping()) {
            txvFreeShipping.setVisibility(View.GONE);
        } else {
            txvFreeShipping.setVisibility(View.VISIBLE);
        }

        try {
            Picasso.get().load(product.getThumbnail()).into(imageView);
        } catch (Exception ex) {
            imageView.setImageDrawable(convertView.getResources().getDrawable(R.drawable.ic_image_default));
        }

        return convertView;
    }
}

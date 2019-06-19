package ar.com.mercadolibre.productsearch.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ar.com.mercadolibre.productsearch.R;
import ar.com.mercadolibre.productsearch.adapter.AttributeListAdapter;
import ar.com.mercadolibre.productsearch.interfaces.ICustomActivity;
import ar.com.mercadolibre.productsearch.model.Product;
import ar.com.mercadolibre.productsearch.model.ProductAttribute;
import ar.com.mercadolibre.productsearch.model.ProductPicture;
import ar.com.mercadolibre.productsearch.service.SearchService;
import ar.com.mercadolibre.productsearch.utils.Utils;

public class DetailsActivity extends AppCompatActivity implements ICustomActivity {

    private String itemID;
    private SearchService searchService;
    private ProgressBar pb;
    private ImageView imageView;
    private TextView txvItemPrice;
    private TextView txvItemCondition;
    private TextView txvItemTitle;
    private TextView txvItemSoldQuantity;
    private Button btnGetProductDescription;
    private TextView txvItemOriginalPrice;
    private TextView txvItemDiscount;
    private TextView txvFreeShippingDet;
    private ImageView imgFreeShippingDet;
    private View divider;
    private TextView txvItemDetAvailable;
    private ImageView imgNoConnectionDet;
    private TextView txvNoConnectionDet;
    private Button btnRetrySearchDet;
    private TextView txvItemWarrantyTitle;
    private TextView txvItemWarranty;
    private View dividerDet2;
    private View dividerDet3;
    private Button btnShowAttributes;
    private ImageButton btnPrevPicture;
    private ImageButton btnNextPicture;
    private int currentPictureIndex;
    private ArrayList<ProductPicture> picturesList;
    private ArrayList<ProductAttribute> attributesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        itemID = getIntent().getExtras().getString("selectedItemID");
        currentPictureIndex = 0;
        picturesList = new ArrayList<ProductPicture>();
        attributesList = new ArrayList<ProductAttribute>();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detalle de producto");
        actionBar.setSubtitle("#" + itemID);
        actionBar.setDisplayHomeAsUpEnabled(true);

        searchService = new SearchService();

        this.initializeLayoutElements();

        this.startActivityWork();
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



    @Override
    public void initializeLayoutElements() {
        pb = findViewById(R.id.progressBar2);
        imageView = findViewById(R.id.imgItemDetImages);
        txvItemPrice = findViewById(R.id.txvItemDetPrice);
        txvItemCondition = findViewById(R.id.txvItemDetCondition);
        txvItemTitle = findViewById(R.id.txvItemDetTitle);
        txvItemSoldQuantity = findViewById(R.id.txvItemDetSoldQuantity);
        btnGetProductDescription = findViewById(R.id.btnGetProductDescription);
        btnGetProductDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("productID", itemID);
                Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        txvItemOriginalPrice = findViewById(R.id.txtItemDetOriginalPrice);
        txvItemDiscount = findViewById(R.id.txvItemDetDiscount);
        txvFreeShippingDet = findViewById(R.id.txvFreeShippingDet);
        imgFreeShippingDet = findViewById(R.id.imgFreeShippingDet);
        divider = findViewById(R.id.dividerDet);
        txvItemDetAvailable = findViewById(R.id.txvItemDetAvailable);
        imgNoConnectionDet = findViewById(R.id.imgNoConnectionDet);
        txvNoConnectionDet = findViewById(R.id.txvNoConnectionDet);
        btnRetrySearchDet = findViewById(R.id.btnRetrySearchDet);
        btnRetrySearchDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWork();
            }
        });
        txvItemWarrantyTitle = findViewById(R.id.txvItemWarrantyTitle);
        txvItemWarranty = findViewById(R.id.txvItemWarranty);
        dividerDet2 = findViewById(R.id.dividerDet2);
        dividerDet3 = findViewById(R.id.dividerDet3);
        btnShowAttributes = findViewById(R.id.btnShowProductAttributes);
        btnPrevPicture = findViewById(R.id.btnPrevPicture);
        btnPrevPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPictureIndex--;
                try {
                    UrlImageViewHelper.setUrlDrawable(imageView, picturesList.get(currentPictureIndex).getUrl());
                } catch (Exception e) {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_image_default));
                }
                if (currentPictureIndex == 0) {
                    btnPrevPicture.setEnabled(false);
                } else {
                    btnPrevPicture.setEnabled(true);
                }
                if (currentPictureIndex < picturesList.size() - 1) {
                    btnNextPicture.setEnabled(true);
                }
            }
        });
        btnNextPicture = findViewById(R.id.btnNextPicture);
        btnNextPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPictureIndex++;
                try {
                    UrlImageViewHelper.setUrlDrawable(imageView, picturesList.get(currentPictureIndex).getUrl());
                } catch (Exception e) {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_image_default));
                }
                if (currentPictureIndex == picturesList.size() - 1) {
                    btnNextPicture.setEnabled(false);
                } else {
                    btnNextPicture.setEnabled(true);
                }
                if (currentPictureIndex > 0) {
                    btnPrevPicture.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void startActivityWork() {
        pb.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        txvItemPrice.setVisibility(View.GONE);
        txvItemCondition.setVisibility(View.GONE);
        txvItemTitle.setVisibility(View.GONE);
        txvItemSoldQuantity.setVisibility(View.GONE);
        btnGetProductDescription.setVisibility(View.GONE);
        txvItemOriginalPrice.setVisibility(View.GONE);
        txvItemDiscount.setVisibility(View.GONE);
        txvFreeShippingDet.setVisibility(View.GONE);
        imgFreeShippingDet.setVisibility(View.GONE);
        divider.setVisibility(View.GONE);
        txvItemDetAvailable.setVisibility(View.GONE);
        txvItemWarrantyTitle.setVisibility(View.GONE);
        txvItemWarranty.setVisibility(View.GONE);
        dividerDet2.setVisibility(View.GONE);
        dividerDet3.setVisibility(View.GONE);
        btnShowAttributes.setVisibility(View.GONE);
        btnNextPicture.setVisibility(View.GONE);
        btnPrevPicture.setVisibility(View.GONE);
        if (!Utils.checkNetworkConnection(getApplicationContext())) {
            pb.setVisibility(View.GONE);
            showNoConnectionStatus();
        } else {
            hideNoConnectionStatus();
            AsyncDetails asyncDetails = new AsyncDetails();
            asyncDetails.execute();
        }
    }

    @Override
    public void endActivityWork() {
        pb.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        txvItemPrice.setVisibility(View.VISIBLE);
        txvItemCondition.setVisibility(View.VISIBLE);
        txvItemTitle.setVisibility(View.VISIBLE);
        txvItemSoldQuantity.setVisibility(View.VISIBLE);
        btnGetProductDescription.setVisibility(View.VISIBLE);
        txvItemOriginalPrice.setVisibility(View.VISIBLE);
        txvItemDiscount.setVisibility(View.VISIBLE);
        divider.setVisibility(View.VISIBLE);
        txvItemDetAvailable.setVisibility(View.VISIBLE);
        txvItemWarrantyTitle.setVisibility(View.VISIBLE);
        txvItemWarranty.setVisibility(View.VISIBLE);
        dividerDet2.setVisibility(View.VISIBLE);
        dividerDet3.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoConnectionStatus() {
        imgNoConnectionDet.setVisibility(View.VISIBLE);
        txvNoConnectionDet.setVisibility(View.VISIBLE);
        btnRetrySearchDet.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoConnectionStatus() {
        imgNoConnectionDet.setVisibility(View.GONE);
        txvNoConnectionDet.setVisibility(View.GONE);
        btnRetrySearchDet.setVisibility(View.GONE);
    }

    private class AsyncDetails extends AsyncTask<Void, Void, Product> {

        @Override
        protected Product doInBackground(Void... voids) {
            try {
                Product product = searchService.getProductById(itemID);
                return product;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Product product) {
            if (product == null) {
                Utils.showToast(getApplicationContext(), "Error al recuperar el producto", Toast.LENGTH_LONG);
                pb.setVisibility(View.GONE);
                showNoConnectionStatus();
            } else {
                // tratamiento de imágenes
                try {
                    UrlImageViewHelper.setUrlDrawable(imageView, product.getPictureList().get(0).getUrl());
                    currentPictureIndex = 0;
                    btnPrevPicture.setEnabled(false);
                    picturesList.addAll(product.getPictureList());
                    if (product.getPictureList().size() == 1) {
                        btnPrevPicture.setVisibility(View.GONE);
                        btnNextPicture.setVisibility(View.GONE);
                    } else {
                        btnPrevPicture.setVisibility(View.VISIBLE);
                        btnNextPicture.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_image_default));
                }

                String condition = "";
                switch (product.getCondition()) {
                    case "new":
                        condition = "Nuevo";
                        break;
                    case "used":
                        condition = "Usado";
                        break;
                }
                txvItemCondition.setText(condition);

                String itemSold = product.getSoldQuantity() + " " + (product.getSoldQuantity() == 1 ? "vendido" : "vendidos");
                txvItemSoldQuantity.setText(itemSold);

                txvItemTitle.setText(product.getTitle());

                String pattern = (product.getPrice() % 1 == 0 ? "###,###" : "###,###.00");
                DecimalFormat formatter = new DecimalFormat(pattern);
                String itemPrice = Utils.getCurrencySymbol(product.getCurrencyId()) + " " + formatter.format(product.getPrice());
                txvItemPrice.setText(itemPrice);

                if (product.getOriginalPrice().equals(0.0)) {
                    txvItemOriginalPrice.setText("");
                    txvItemDiscount.setText("");
                } else {
                    String itemOriginalPrice = Utils.getCurrencySymbol(product.getCurrencyId()) + " "
                            + formatter.format(product.getOriginalPrice());
                    txvItemOriginalPrice.setText(itemOriginalPrice);
                    txvItemOriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    Long discount = Math.round(100 - (product.getPrice() * 100) / product.getOriginalPrice());
                    txvItemDiscount.setText(discount.toString() + getResources().getString(R.string.item_discount_post));
                }

                if (!product.isFreeShipping()) {
                    txvFreeShippingDet.setVisibility(View.GONE);
                    imgFreeShippingDet.setVisibility(View.GONE);
                } else {
                    txvFreeShippingDet.setVisibility(View.VISIBLE);
                    imgFreeShippingDet.setVisibility(View.VISIBLE);
                }

                txvItemDetAvailable.setText(product.getAvailableQuantity() + (product.getAvailableQuantity() == 1 ? " disponible" : " disponibles"));

                txvItemWarranty.setText(product.getWarranty() == "" ? "Sin garantía" : product.getWarranty());

                if (product.getAttributes().size() > 0) {
                    btnShowAttributes.setVisibility(View.VISIBLE);
                    attributesList.addAll(product.getAttributes());
                    btnShowAttributes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("product_attributes", attributesList);
                            Intent intent = new Intent(getApplicationContext(), ProductAttributesActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                } else {
                    btnShowAttributes.setVisibility(View.GONE);
                }

                endActivityWork();
            }
        }
    }
}

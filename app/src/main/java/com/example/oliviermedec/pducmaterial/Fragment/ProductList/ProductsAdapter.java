package com.example.oliviermedec.pducmaterial.Fragment.ProductList;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oliviermedec.pducmaterial.Cache.Cache;
import com.example.oliviermedec.pducmaterial.Fragment.Panier.Panier;
import com.example.oliviermedec.pducmaterial.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by oliviermedec on 24/03/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> products;
    private Fragment parent;
    private Context context;
    private Panier panier = null;
    private Cache cache = null;

    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_product_list, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductsAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.txtProductName.setText(products.get(i).nom);
        viewHolder.txtDescrition.setText(products.get(i).description);
        viewHolder.txtPrice.setText(products.get(i).prix + "€");
        cache.loadPicture(viewHolder.imgProduit, products.get(i).image);
        viewHolder.btnKnowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ProductsListFragment)parent).callProduct(products.get(i)._id, products.get(i).nom);
            }
        });
        viewHolder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panier.addProduct(products.get(i));
                Snackbar.make(parent.getView(), products.get(i).nom + " est ajouté à votre panier.", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public ProductsAdapter(Fragment parent, List<Product> products, Context context){
        this.products = products;
        this.parent = parent;
        this.context = context;
        this.panier = new Panier(this.context);
        this.cache = new Cache(this.context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtProductName;
        public TextView txtDescrition;
        public TextView txtPrice;
        public ImageView imgProduit;
        public Button btnKnowMore;
        public Button btnBuy;

        public ViewHolder(View v) {
            super(v);
            txtProductName = (TextView)v.findViewById(R.id.txtProductName);
            txtDescrition = (TextView)v.findViewById(R.id.txtDescription);
            txtPrice = (TextView)v.findViewById(R.id.txtPrice);
            imgProduit = (ImageView)v.findViewById(R.id.imgProduct);
            btnKnowMore = (Button)v.findViewById(R.id.btnOne);
            btnBuy = (Button)v.findViewById(R.id.btnBuy);
        }
    }
}
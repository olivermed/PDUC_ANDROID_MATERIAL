package com.example.oliviermedec.pducmaterial.Fragment.ProductList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oliviermedec.pducmaterial.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by oliviermedec on 24/03/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> products;
    private Context context;

    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_product_list, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.txtProductName.setText(products.get(i).nom);
        viewHolder.txtDescrition.setText(products.get(i).description);
        viewHolder.txtPrice.setText(products.get(i).prix + "€");
        Picasso.with(context).load(context.getResources().getString(R.string.server_url) +
                "/images/" + products.get(i).image).
                into(viewHolder.imgProduit);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public ProductsAdapter(Context context, List<Product> products){
        this.products = products;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtProductName;
        public TextView txtDescrition;
        public TextView txtPrice;
        public ImageView imgProduit;

        public ViewHolder(View v) {
            super(v);
            txtProductName = (TextView)v.findViewById(R.id.txtProductName);
            txtDescrition = (TextView)v.findViewById(R.id.txtDescription);
            txtPrice = (TextView)v.findViewById(R.id.txtPrice);
            imgProduit = (ImageView)v.findViewById(R.id.imgProduct);
        }
    }
}
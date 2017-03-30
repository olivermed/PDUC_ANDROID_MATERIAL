package com.example.oliviermedec.pducmaterial.Fragment.Panier;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oliviermedec.pducmaterial.Fragment.ProductList.Product;
import com.example.oliviermedec.pducmaterial.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by oliviermedec on 31/03/2017.
 */

public class PanierAdapter extends RecyclerView.Adapter<PanierAdapter.ViewHolder> {
    public List<Product> products = null;
    private Fragment parent;
    private Panier panier;

    public PanierAdapter(Fragment parent, List<Product> products) {
        this.products = products;
        this.parent = parent;
        this.panier = new Panier(this.parent.getContext());
    }

    @Override
    public PanierAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.panier_cardview_product, parent, false);
        // set the view's size, margins, paddings and layout parameters
        PanierAdapter.ViewHolder vh = new PanierAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final PanierAdapter.ViewHolder holder, final int position) {
        holder.txtProductName.setText(products.get(position).nom);
        holder.txtPrice.setText(products.get(position).prix + "€");
        /*Picasso.with(parent.getContext()).load(parent.getContext().getResources().getString(R.string.server_url) +
                "/images/" + products.get(position).image).
                into(holder.imgProduit);*/
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panier.deleteProduct(products.get(position).id);
                products = panier.getPanier();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName;
        public TextView txtPrice;
        public ImageView imgProduit;
        public ImageButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtProductName = (TextView)itemView.findViewById(R.id.txtTitle);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            imgProduit = (ImageView)itemView.findViewById(R.id.imgProduct);
            btnDelete = (ImageButton)itemView.findViewById(R.id.btnDelete);
        }
    }
}

package com.example.oliviermedec.pducmaterial.Fragment.Categories;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oliviermedec.pducmaterial.Fragment.ProductList.ProductListActivity;
import com.example.oliviermedec.pducmaterial.Fragment.SousCategories.SousCategorieFragment;
import com.example.oliviermedec.pducmaterial.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by oliviermedec on 22/03/2017.
 */

public class categorieAdapter extends BaseAdapter {
    private Fragment parent;
    private List<Categorie> Categories;

    public categorieAdapter(Fragment parent, List<Categorie> Categories) {
        this.parent = parent;
        this.Categories = Categories;
    }

    @Override
    public int getCount() {
        return Categories.size();
    }

    @Override
    public Object getItem(int i) {
        return Categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View categorieView = inflater.inflate(R.layout.categorie_layout, null);

        TextView catagorieTxt = (TextView)categorieView.findViewById(R.id.txtCategorie);

        ImageView imageView = (ImageView)categorieView.findViewById(R.id.imageViewCategorie);

        Picasso.with(parent.getContext()).load(parent.getContext().getResources().getString(R.string.server_url) +
                "/images/" + Categories.get(i).image).
                into(imageView);

        if (Categories.get(i).sousCategorie == null) { // Si c'est un categorie
            catagorieTxt.setText(Categories.get(i).categorie.toUpperCase());
        } else { // Si c'est une sous categorie
            catagorieTxt.setText(Categories.get(i).sousCategorie.toUpperCase());
        }

        catagorieTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Categories.get(i).sousCategorie == null) { // Si c'est un categorie
                    ((CategoriesFragment)parent).setMainActivityInstance(((CategoriesFragment)parent)._instance);
                    ((CategoriesFragment)parent).callSousCategorie(Categories.get(i)._id);
                } else { // Si c'est une sous categorie
                    ((SousCategorieFragment)parent).callProductList(Categories.get(i)._id, Categories.get(i).sousCategorie);
                }
            }
        });
        return categorieView;
    }
}

package com.example.oliviermedec.pducmaterial.Fragment.Categories;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oliviermedec.pducmaterial.Cache.Cache;
import com.example.oliviermedec.pducmaterial.Fragment.ProductList.ProductListActivity;
import com.example.oliviermedec.pducmaterial.Fragment.SousCategories.SousCategorieFragment;
import com.example.oliviermedec.pducmaterial.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by oliviermedec on 22/03/2017.
 */

public class categorieAdapter extends BaseAdapter {
    private Fragment parent;
    private List<Categorie> Categories;
    private Cache cache;
    private View categorieView;

    public categorieAdapter(Fragment parent, List<Categorie> Categories) {
        this.parent = parent;
        this.Categories = Categories;
        cache = new Cache(parent.getContext());
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

        categorieView = inflater.inflate(R.layout.categorie_layout, null);

        TextView catagorieTxt = (TextView)categorieView.findViewById(R.id.txtCategorie);

        final ImageView imageView = (ImageView)categorieView.findViewById(R.id.imageViewCategorie);

        cache.loadPicture(imageView, Categories.get(i).image);

        if (Categories.get(i).sousCategorie == null) { // Si c'est un categorie
            catagorieTxt.setText(Categories.get(i).categorie.toUpperCase());
        } else { // Si c'est une sous categorie
            catagorieTxt.setText(Categories.get(i).sousCategorie.toUpperCase());
        }

        try {
            catagorieTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Categories.get(i).sousCategorie == null) { // Si c'est un categorie
                        ((CategoriesFragment)parent).callSousCategorie(Categories.get(i)._id, Categories.get(i).categorie);
                    } else { // Si c'est une sous categorie
                        ((SousCategorieFragment)parent).callProductList(Categories.get(i)._id, Categories.get(i).sousCategorie);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categorieView;
    }
}

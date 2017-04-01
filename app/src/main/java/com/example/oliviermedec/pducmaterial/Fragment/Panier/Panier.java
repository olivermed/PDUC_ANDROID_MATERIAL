package com.example.oliviermedec.pducmaterial.Fragment.Panier;

import android.content.Context;

import com.example.oliviermedec.pducmaterial.Cache.Cache;
import com.example.oliviermedec.pducmaterial.Fragment.ProductList.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oliviermedec on 31/03/2017.
 */

public class Panier {
    public static String TAG = Panier.class.getSimpleName();
    private Context context;
    private List<Product> products;
    private Cache cache;

    public Panier(Context context) {
        this.context = context;
        cache = new Cache(this.context);
        products = getPanier();
        if (products == null) {
            products = new ArrayList<>();
        }
    }

    public void addProduct(Product product) {
        products.add(product);
        savePanier();
    }

    public void deleteProduct(String id) {
        int index = getIndexToDeleteById(id);
        System.out.println("index :: " + index);
        products.remove(index);
        savePanier();
    }

    public List<Product> getPanier() {
        List<Product> list = cache.getListProduct(TAG);
        return list;
    }

    private int getIndexToDeleteById(String id) {
        int i = 0;
        for (Product product: products) {
            System.out.println(product._id + " == " +  id);
            if (id.equals(product._id)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private void savePanier() {
        try {
            cache.serealize(products, TAG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deletePanier(){
        cache.delFile(TAG);
    }
}

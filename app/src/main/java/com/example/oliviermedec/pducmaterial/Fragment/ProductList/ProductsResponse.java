package com.example.oliviermedec.pducmaterial.Fragment.ProductList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by oliviermedec on 24/03/2017.
 */

public class ProductsResponse {
    @SerializedName("results")
    private List<Product> results;

    public List<Product> getResults() {
        return results;
    }
}

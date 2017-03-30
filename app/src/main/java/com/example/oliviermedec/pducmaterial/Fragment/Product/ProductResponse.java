package com.example.oliviermedec.pducmaterial.Fragment.Product;

import com.example.oliviermedec.pducmaterial.Fragment.ProductList.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by oliviermedec on 27/03/2017.
 */

public class ProductResponse {
    @SerializedName("result")
    private Product result;

    public Product getResult() {
        return result;
    }
}

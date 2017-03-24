package com.example.oliviermedec.pducmaterial.Fragment.Categories;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by oliviermedec on 22/03/2017.
 */

public class CategorieResponse {
    @SerializedName("results")
    private List<Categorie> results;

    public List<Categorie> getResults() {
        return results;
    }
}
package com.example.oliviermedec.pducmaterial.Fragment.Categories;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oliviermedec on 29/11/2016.
 * Object is a container for Categories and sub category
 */

public class Categorie {
    @SerializedName("categorie")
    public String categorie;
    @SerializedName("sousCategorie")
    public String sousCategorie;
    @SerializedName("_id")
    public String _id;
    @SerializedName("image")
    public String image;

    public Categorie(String categorie){
        this.categorie = categorie;
    }
}
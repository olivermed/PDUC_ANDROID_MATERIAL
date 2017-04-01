package com.example.oliviermedec.pducmaterial.Fragment.ProductList;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oliviermedec on 24/03/2017.
 */

public class Product {
    @SerializedName("_id")
    public String _id;
    @SerializedName("nom")
    public String nom;
    @SerializedName("description")
    public String description;
    @SerializedName("prix")
    public String prix;
    @SerializedName("image")
    public String image;

    public Product(String nom, String description, String prix, String image){
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
    }
}

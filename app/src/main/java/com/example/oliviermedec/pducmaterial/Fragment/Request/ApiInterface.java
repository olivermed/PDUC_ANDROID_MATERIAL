package com.example.oliviermedec.pducmaterial.Fragment.Request;

import com.example.oliviermedec.pducmaterial.Fragment.Categories.CategorieResponse;
import com.example.oliviermedec.pducmaterial.Fragment.ProductList.ProductsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by oliviermedec on 22/03/2017.
 */

public interface ApiInterface {
    @GET("/getCategories")
    Call<CategorieResponse> getCategories();

    @GET("/getSousCategorie/{idSubCat}")
    Call<CategorieResponse> getSousCategorie(@Path("idSubCat")String idSubCat);


    @GET("/getProductsBySubCat/{subCat}")
    Call<ProductsResponse> getProducts(@Path("subCat")String idSubCat);
}

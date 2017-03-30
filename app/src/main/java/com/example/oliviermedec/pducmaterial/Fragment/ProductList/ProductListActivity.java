package com.example.oliviermedec.pducmaterial.Fragment.ProductList;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.oliviermedec.pducmaterial.Fragment.Request.ApiInterface;
import com.example.oliviermedec.pducmaterial.Fragment.Request.PducAPI;
import com.example.oliviermedec.pducmaterial.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {
    private static final String TAG = ProductListActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Product> Products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.listProducts);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ApiInterface productInterface = PducAPI.getClient().create(ApiInterface.class);

        Intent categorie = getIntent();
        String objectId = categorie.getStringExtra(getResources().getString(R.string.objectID));
        String objectName = categorie.getStringExtra(getResources().getString(R.string.objectName));
        collapsingToolbar.setTitle(objectName);

        Call<ProductsResponse> call = productInterface.getProducts(objectId);

        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse>call, Response<ProductsResponse> response) {
                if (response.body() != null) {
                    List<Product> products = response.body().getResults();
                    for (Product product: products) {
                        Products.add(product);
                    }
                    Log.d(TAG, "Number of products received: " + products.size());

                    //mAdapter = new ProductsAdapter(ProductsListFragment.this, Products);
                    //mRecyclerView.setAdapter(mAdapter);
                }
                Log.d(TAG, "L'object body est null");
            }

            @Override
            public void onFailure(Call<ProductsResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}

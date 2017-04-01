package com.example.oliviermedec.pducmaterial.Fragment.Product;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oliviermedec.pducmaterial.Cache.Cache;
import com.example.oliviermedec.pducmaterial.FRequirement;
import com.example.oliviermedec.pducmaterial.Fragment.Categories.CategoriesFragment;
import com.example.oliviermedec.pducmaterial.Fragment.Panier.Panier;
import com.example.oliviermedec.pducmaterial.Fragment.ProductList.Product;
import com.example.oliviermedec.pducmaterial.Fragment.ProductList.ProductsAdapter;
import com.example.oliviermedec.pducmaterial.Fragment.ProductList.ProductsListFragment;
import com.example.oliviermedec.pducmaterial.Fragment.ProductList.ProductsResponse;
import com.example.oliviermedec.pducmaterial.Fragment.Request.ApiInterface;
import com.example.oliviermedec.pducmaterial.Fragment.Request.PducAPI;
import com.example.oliviermedec.pducmaterial.MainActivity;
import com.example.oliviermedec.pducmaterial.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment implements FRequirement {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = ProductFragment.class.getSimpleName();
    private static final String ID = "id";
    private static final String NAME = "name";
    private MainActivity _instance;
    private Panier panier = null;

    // TODO: Rename and change types of parameters
    private String ProductId;
    private String ProductName;

    private OnFragmentInteractionListener mListener;

    private Cache cache = null;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     * @param name Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String id, String name) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        args.putString(NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ProductId = getArguments().getString(ID);
            ProductName = getArguments().getString(NAME);
            getActivity().setTitle(ProductName);
        }
        cache = new Cache(getContext());
        panier = new Panier(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ApiInterface productInterface = PducAPI.getClient().create(ApiInterface.class);
        final ImageView imgProduct = (ImageView)view.findViewById(R.id.ImageProduct);
        final TextView descProduct = (TextView)view.findViewById(R.id.txtDescription);
        final TextView priceProduct = (TextView)view.findViewById(R.id.txtPrice);
        final TextView nameProduct = (TextView)view.findViewById(R.id.txtTitle);
        final Button btnBuy = (Button)view.findViewById(R.id.btnBuy);

        Call<ProductResponse> call = productInterface.getProduct(ProductId);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse>call, Response<ProductResponse> response) {
                if (response.body() != null) {
                    final Product product = response.body().getResult();
                    descProduct.setText(product.description);
                    nameProduct.setText(product.nom);
                    priceProduct.setText(product.prix + "€");
                    Picasso.with(getContext()).load(getResources().getString(R.string.server_url) +
                            "/images/" + product.image).
                            into(imgProduct);

                    btnBuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            panier.addProduct(product);
                            Snackbar.make(getView(), product.nom + " est ajouté à votre panier.", Snackbar.LENGTH_LONG).show();
                        }
                    });
                    try {
                        cache.serealize(product, ProductId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "L'object body est null");
                }
            }

            @Override
            public void onFailure(Call<ProductResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        try {
            if (ProductId != null) {
                @SuppressWarnings("unchecked")
                Product product = (Product)cache.deserialize(ProductId, Product.class);
                if (product != null) {
                    descProduct.setText(product.description);
                    nameProduct.setText(product.nom);
                    priceProduct.setText(product.prix + "€");
                    Picasso.with(getContext()).load(getResources().getString(R.string.server_url) +
                            "/images/" + product.image).
                            into(imgProduct);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setMainActivityInstance(MainActivity mainActivity) {
        _instance = mainActivity;
    }

    @Override
    public boolean setAppBarMenu() {
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

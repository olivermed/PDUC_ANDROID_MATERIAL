package com.example.oliviermedec.pducmaterial.Fragment.SousCategories;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.oliviermedec.pducmaterial.Cache.Cache;
import com.example.oliviermedec.pducmaterial.FRequirement;
import com.example.oliviermedec.pducmaterial.Fragment.Categories.Categorie;
import com.example.oliviermedec.pducmaterial.Fragment.Categories.CategorieResponse;
import com.example.oliviermedec.pducmaterial.Fragment.Categories.CategoriesFragment;
import com.example.oliviermedec.pducmaterial.Fragment.Categories.categorieAdapter;
import com.example.oliviermedec.pducmaterial.Fragment.ProductList.ProductsListFragment;
import com.example.oliviermedec.pducmaterial.Fragment.Request.ApiInterface;
import com.example.oliviermedec.pducmaterial.Fragment.Request.PducAPI;
import com.example.oliviermedec.pducmaterial.MainActivity;
import com.example.oliviermedec.pducmaterial.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SousCategorieFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SousCategorieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SousCategorieFragment extends Fragment implements FRequirement {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = SousCategorieFragment.class.getSimpleName();
    private static final String SUBCATID = "subcatid";
    private static final String SUBCATNAME = "subcatname";
    private MainActivity _instance = null;

    // TODO: Rename and change types of parameters
    private String subCatId;
    private String subCatName;

    private OnFragmentInteractionListener mListener;

    private List<Categorie> Categories = new ArrayList<>();

    @BindView(R.id.grid_category)
    GridView categorieGridview;

    private Cache cache = null;

    public SousCategorieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     * @return A new instance of fragment SousCategorieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SousCategorieFragment newInstance(String id, String name) {
        SousCategorieFragment fragment = new SousCategorieFragment();
        Bundle args = new Bundle();
        args.putString(SUBCATID, id);
        args.putString(SUBCATNAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subCatId = getArguments().getString(SUBCATID);
            subCatName = getArguments().getString(SUBCATNAME);
        }
        cache = new Cache(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        getActivity().setTitle(subCatName);
        categorieGridview = (GridView) view.findViewById(R.id.grid_category);

        ApiInterface productInterface = PducAPI.getClient().create(ApiInterface.class);

        Log.w(TAG, "Categorie request started");
        Call<CategorieResponse> call = productInterface.getSousCategorie(subCatId);

        call.enqueue(new Callback<CategorieResponse>() {
            @Override
            public void onResponse(Call<CategorieResponse>call, Response<CategorieResponse> response) {
                Log.w(TAG, "Categorie request request finished");
                Categories = new ArrayList<>();

                List<Categorie> categories = response.body().getResults();

                for (Categorie categorie: categories) {
                    Categories.add(categorie);
                }
                Log.d(TAG, "Number of categorie received: " + categories.size());

                categorieGridview.setAdapter(new categorieAdapter(SousCategorieFragment.this, Categories));
                try {
                    cache.serealize(Categories, subCatId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CategorieResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        List<Categorie> categories = cache.getListCategory(subCatId);
        if (categories != null)
            categorieGridview.setAdapter(new categorieAdapter(this, categories));
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

    public void callProductList(String id, String name){
        Fragment fragment = ProductsListFragment.newInstance(id, name);
        ((ProductsListFragment)fragment).setMainActivityInstance(_instance);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, SousCategorieFragment.TAG)
                .addToBackStack(SousCategorieFragment.TAG)
                .commit();
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

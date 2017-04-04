package com.example.oliviermedec.pducmaterial.Fragment.Categories;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.oliviermedec.pducmaterial.Cache.Cache;
import com.example.oliviermedec.pducmaterial.Fragment.Request.ApiInterface;
import com.example.oliviermedec.pducmaterial.Fragment.Request.PducAPI;
import com.example.oliviermedec.pducmaterial.Fragment.SousCategories.SousCategorieFragment;
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
 * {@link CategoriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {
    public static final String TAG = CategoriesFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<Categorie> Categories = new ArrayList<>();

    private Cache cache = null;
    private List<Categorie> categoriesCache = null;

    @BindView(R.id.grid_category)
    GridView categorieGridview;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public void setAppBarMenu() {
        ((MainActivity)getActivity()).setAppBarMenu(R.id.nav_category);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        cache = new Cache(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        getActivity().setTitle(getString(R.string.TitleCategorie));

        categoriesCache = cache.getListCategory(TAG);

        categorieGridview = (GridView) view.findViewById(R.id.grid_category);

        ApiInterface productInterface = PducAPI.getClient().create(ApiInterface.class);

        Log.w(TAG, "Categorie request started");
        Call<CategorieResponse> call = productInterface.getCategories();

        call.enqueue(new Callback<CategorieResponse>() {
            @Override
            public void onResponse(Call<CategorieResponse>call, Response<CategorieResponse> response) {
                Log.w(TAG, "Categorie request request finished");

                List<Categorie> categories = response.body().getResults();
                if (categoriesCache == null || categories.size() != categoriesCache.size()) {
                    Categories = new ArrayList<>();
                    for (Categorie categorie : categories) {
                        Categories.add(categorie);
                    }
                    Log.d(TAG, "Number of categorie received: " + categories.size());
                    try {
                        cache.serealize(Categories, TAG);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    categorieGridview.setAdapter(new categorieAdapter(CategoriesFragment.this, Categories));
                } else {
                    System.out.println("No need to reset adapter for categories");
                }
            }

            @Override
            public void onFailure(Call<CategorieResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        if (categoriesCache != null){
            System.out.println("Catégorie cache :: " + categoriesCache.size());
            categorieGridview.setAdapter(new categorieAdapter(this, categoriesCache));
        } else {
            System.out.println("Catégorie cache :: null");
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

    public void callSousCategorie(String id, String name){
        Fragment fragment = SousCategorieFragment.newInstance(id, name);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, SousCategorieFragment.TAG)
                .addToBackStack(SousCategorieFragment.TAG)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        setAppBarMenu();
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

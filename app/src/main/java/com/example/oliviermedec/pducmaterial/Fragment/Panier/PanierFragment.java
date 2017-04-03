package com.example.oliviermedec.pducmaterial.Fragment.Panier;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.oliviermedec.pducmaterial.Fragment.ProductList.Product;
import com.example.oliviermedec.pducmaterial.MainActivity;
import com.example.oliviermedec.pducmaterial.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PanierFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PanierFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PanierFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = PanierFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Product> Products = new ArrayList<>();
    private Panier panier = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view = null;

    private OnFragmentInteractionListener mListener;

    public PanierFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PanierFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PanierFragment newInstance(String param1, String param2) {
        PanierFragment fragment = new PanierFragment();
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
        panier = new Panier(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_panier, container, false);
        getActivity().setTitle(getString(R.string.TitlePanier));
        List<Product> products = panier.getPanier();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.listProducts);
        ImageView img = (ImageView) view.findViewById(R.id.empty);

        if (products != null && products.size() > 0) {
            img.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            Products = new ArrayList<>();
            for (Product product: products) {
                Products.add(product);
            }

            Log.d(TAG, "Number of products received: " + products.size());

            mAdapter = new PanierAdapter(PanierFragment.this, Products);
            mRecyclerView.setAdapter(mAdapter);
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
    public void onResume() {
        super.onResume();
        setAppBarMenu();
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
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).fab.setImageResource(R.drawable.ic_camera_alt);
        ((MainActivity)getActivity()).setScannerFabListener();
    }

    public void setAppBarMenu() {
        ((MainActivity)getActivity()).setAppBarMenu(R.id.nav_pannier);
        ((MainActivity)getActivity()).fab.setImageResource(R.drawable.ic_shopping_cart);
        ((MainActivity)getActivity()).setPanierFabListener();
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

    public void setPanierEmpty(){
        if (view != null) {
            ImageView img = (ImageView) view.findViewById(R.id.empty);
            mRecyclerView.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);
        }
    }
}

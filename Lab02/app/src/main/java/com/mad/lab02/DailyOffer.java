package com.mad.lab02;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DailyOffer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DailyOffer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyOffer extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private OnFragmentInteractionListener mListener;

    private static final String Name = "keyName";
    private static final String Description = "keyDescription";
    private static final String Price = "keyEuroPrice";
    private static final String Photo ="keyPhotoPath";
    private static final String Quantity = "keyQuantity";
    private static ArrayList<DailyOfferItem> dataList = new ArrayList<>();


    public DailyOffer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyOffer.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyOffer newInstance(String param1, String param2) {
        DailyOffer fragment = new DailyOffer();
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
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dailyoffer, container, false);


        recyclerView = view.findViewById(R.id.dish_list);
        mAdapter = new RecyclerAdapterDish(getContext(), dataList);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.add:
                Intent edit_profile = new Intent(getContext(), EditOffer.class);
                startActivityForResult(edit_profile, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null && resultCode == 1){
            getData(data);
            //mAdapter.notifyDataSetChanged();
        }

    }

    public static ArrayList<DailyOfferItem> getData(Intent data){

        DailyOfferItem item = new DailyOfferItem();
        item.setName(data.getStringExtra(Name));
        item.setDesc(data.getStringExtra(Description));
        item.setPrice(data.getFloatExtra(Price, 0));
        item.setQuantity(data.getIntExtra(Quantity, 0));
        item.setPhotoPath(data.getStringExtra(Photo));


        Log.d("NAME", ""+item.getName());
        Log.d("DESC", ""+item.getDesc());
        Log.d("PRICE", ""+item.getPrice());
        Log.d("QUANTITY",""+item.getQuantity());
        Log.d("PHOTO", ""+item.getPhotoPath());

        dataList.add(item);

        return dataList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

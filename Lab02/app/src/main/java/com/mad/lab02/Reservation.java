package com.mad.lab02;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Reservation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Reservation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reservation extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    String [] names = {"carlo", "fede", "davide", "marco"};
    String [] addrs = {"via uno 1", "via due 2", "via tre 3", "via quattro 4"};
    String [] cells = {"123", "124", "125", "126"};
    Integer [] imgs = {R.drawable.profile_drhouse, R.drawable.profile_goku, R.drawable.profile_link, R.drawable.profile_vegeta};

    private OnFragmentInteractionListener mListener;

    class ResListView extends ArrayAdapter<String> {
        private String [] names;
        private String [] addrs;
        private String [] cells;
        private Integer [] imgs;
        private Activity context;
        public ResListView (Activity context, String[] names, String[] addrs, String[] cells, Integer[] imgs){
            super(context, R.layout.reservation_listview, names);
            this.context=context;
            this.names=names;
            this.addrs=addrs;
            this.cells=cells;
            this.imgs=imgs;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View r = convertView;
            ViewHolder viewHolder = null;
            if(r==null){
                LayoutInflater layoutInflater = context.getLayoutInflater();
                r=layoutInflater.inflate(R.layout.reservation_listview, null);
                viewHolder = new ViewHolder(r);
                r.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) r.getTag();
            }
            viewHolder.imw.setImageResource(imgs[position]);
            viewHolder.tw1.setText(names[position]);
            viewHolder.tw2.setText(addrs[position]);
            viewHolder.tw3.setText(cells[position]);
            return r;
        }
        class ViewHolder
        {
            TextView tw1;
            TextView tw2;
            TextView tw3;
            ImageView imw;
            ViewHolder(View v){
                tw1=(TextView)v.findViewById(R.id.listview_name);
                tw2=(TextView)v.findViewById(R.id.listview_address);
                tw3=(TextView)v.findViewById(R.id.listview_cellphone);
                imw=(ImageView)v.findViewById(R.id.profile_image);

            }

        }
    }
    public Reservation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reservation.
     */
    // TODO: Rename and change types and number of parameters
    public static Reservation newInstance(String param1, String param2) {
        Reservation fragment = new Reservation();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        ListView reservations = (ListView) view.findViewById(R.id.reservation_list);
        ResListView myview = new ResListView(getActivity(), names, addrs, cells, imgs);
        reservations.setAdapter(myview);

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

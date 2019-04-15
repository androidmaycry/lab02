package com.mad.lab02;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerAdapterReservation extends RecyclerView.Adapter<RecyclerAdapterReservation.MyViewHolder> {
    private ArrayList<ReservationItem> items;
    private LayoutInflater mInflater;

    public RecyclerAdapterReservation(Context context, ArrayList<ReservationItem> items){
        mInflater = LayoutInflater.from(context);
        this.items = items;
    }
    @NonNull
    @Override
    public RecyclerAdapterReservation.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = mInflater.inflate(R.layout.reservation_listview, parent, false);

        ImageView ciao = view.findViewById(R.id.confirm_reservation);

        ciao.setOnClickListener(e->{
            Toast.makeText(view.getContext(), "DIOOOOOo", Toast.LENGTH_SHORT).show();
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterReservation.MyViewHolder myViewHolder, int position) {
        ReservationItem mCurrent = items.get(position);
        myViewHolder.name.setText(mCurrent.getName());
        myViewHolder.addr.setText(mCurrent.getAddr());
        myViewHolder.cell.setText(mCurrent.getCell());
        myViewHolder.img.setImageResource(mCurrent.getImg());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView addr;
        TextView cell;
        ImageView img;

        public MyViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.listview_name);
            addr = itemView.findViewById(R.id.listview_address);
            cell = itemView.findViewById(R.id.listview_cellphone);
            img = itemView.findViewById(R.id.profile_image);
        }
    }
}

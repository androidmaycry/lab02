package com.mad.lab02;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<Reservation_item> items;
    private LayoutInflater mInflater;

    public RecyclerAdapter(Context context, ArrayList<Reservation_item> items){
        mInflater = LayoutInflater.from(context);
        this.items = items;
    }
    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = mInflater.inflate(R.layout.reservation_listview, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder myViewHolder, int position) {
        Reservation_item mCurrent = items.get(position);
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

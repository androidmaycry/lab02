package com.mad.lab02;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecyclerAdapterDish extends RecyclerView.Adapter<RecyclerAdapterDish.MyViewHolder> {
    private ArrayList<DailyOfferItem> mData;
    private LayoutInflater mInflater;

    public RecyclerAdapterDish(Context context, ArrayList<DailyOfferItem> data){
        mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    @NonNull
    @Override
    public RecyclerAdapterDish.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = mInflater.inflate(R.layout.dailyoffer_listview, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterDish.MyViewHolder myViewHolder, int position) {
        Log.d("DIO", "ciao");
        DailyOfferItem currentObj = mData.get(position);

        myViewHolder.setData(currentObj, position);

    }

    @Override
    public int getItemCount() {
        Log.d("DIO", "size:"+mData.size());
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView dishPhoto;
        TextView dishName, dishDesc, dishPrice, dishQuantity;
        int position;
        DailyOfferItem current;

        public MyViewHolder(View itemView){
            super(itemView);
            dishName = itemView.findViewById(R.id.dish_name);
            dishDesc = itemView.findViewById(R.id.dish_desc);
            dishPrice = itemView.findViewById(R.id.dish_price);
            dishQuantity = itemView.findViewById(R.id.dish_quant);
            dishPhoto = itemView.findViewById(R.id.dish_image);

        }

        public void setData(DailyOfferItem current, int position){
            this.dishName.setText(current.getName());
            this.dishDesc.setText(current.getDesc());
            this.dishPrice.setText(String.valueOf(current.getPrice()));
            this.dishQuantity.setText(String.valueOf(current.getQuantity()));
            try {
                if(current.getPhotoPath()!="")
                    setPhoto(current.getPhotoPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.position = position;
            this.current = current;
        }

        private void setPhoto(String photoPath) throws IOException {
            File imgFile = new File(photoPath);

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            myBitmap = adjustPhoto(myBitmap, photoPath);

            dishPhoto.setImageBitmap(myBitmap);
        }

        private Bitmap adjustPhoto(Bitmap bitmap, String photoPath) throws IOException {
            ExifInterface ei = new ExifInterface(photoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap rotatedBitmap = null;
            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }

            return rotatedBitmap;
        }

        private Bitmap rotateImage(Bitmap source, float angle) {
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        }
    }




}

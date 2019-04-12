package com.mad.lab02;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.text.SimpleDateFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class EditOffer extends AppCompatActivity {
    private static final String MyOFFER = "Daily Offer";
    private static final String Name = "keyName";
    private static final String Description = "keyDescription";
    private static final String Price = "keyEuroPrice";
    private static final String Photo ="keyPhotoPath";
    private static final String Quantity = "keyQuantity";

    private String name;
    private String desc;
    private String error_msg;
    private float priceValue;
    private int quantValue;

    private boolean dialog_open = false;
    private static final int PERMISSION_GALLERY_REQUEST = 1;
    private String currentPhotoPath = "";
    private Button prb;
    private Button quant;
    private SharedPreferences offer_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_offer);

        prb = findViewById(R.id.price);
        quant = findViewById(R.id.quantity);

        prb.setOnClickListener(e->setPrice());
        quant.setOnClickListener(f->setQuantity());
        findViewById(R.id.plus).setOnClickListener(p -> editPhoto());
        findViewById(R.id.img_profile).setOnClickListener(e -> editPhoto());

        Button confirm_reg = findViewById(R.id.button);
        confirm_reg.setOnClickListener(e -> {
            if(checkFields()){
                //returns instance pointing to the file that contains values to be saved
                //MODE_PRIVATE: the file can only be accessed using calling application
                offer_data = getSharedPreferences(MyOFFER, MODE_PRIVATE);
                SharedPreferences.Editor editor = offer_data.edit();

                //store data into file
                editor.putString(Name, name);
                editor.putString(Description, desc);
                editor.putFloat(Price, priceValue);
                editor.putInt(Quantity, quantValue);
                editor.putString(Photo, currentPhotoPath);
                editor.apply();

                //data saved and start new activity
                Intent i = new Intent();
                i.putExtra(Name, name);
                i.putExtra(Description, desc);
                i.putExtra(Price, priceValue);
                i.putExtra(Photo, currentPhotoPath);
                i.putExtra(Quantity, quantValue);

                setResult(1, i);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), error_msg, Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean checkFields(){
        name = ((EditText)findViewById(R.id.name)).getText().toString();
        desc = ((EditText)findViewById(R.id.description)).getText().toString();

        //TODO CHECK ON PRICE AND AVAILABLE QUANTITY

        if(name.trim().length() == 0){
            error_msg = "Insert name";
            return false;
        }

        if(desc.trim().length() == 0){
            error_msg = "Insert description";
            return false;
        }

        return true;
    }

    private String[] setCentsValue(){
        String[] cent = new String[100];
        for(int i=0; i<100; i++){
            if(i<10) {
                cent[i] = "0" +i;
            }
            else{
                cent[i] = ""+i;
            }
        }
        return cent;
    }

    private void setPrice(){
            AlertDialog priceDialog = new AlertDialog.Builder(this).create();
            LayoutInflater inflater = LayoutInflater.from(EditOffer.this);
            final View view = inflater.inflate(R.layout.price_dialog, null);

            NumberPicker euro = view.findViewById(R.id.euro_picker);
            NumberPicker cent = view.findViewById(R.id.cent_picker);

            priceDialog.setView(view);

            priceDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    float centValue = cent.getValue();
                    priceValue = euro.getValue() + (centValue/100);
                    prb.setText(""+priceValue);
                }
            });
            priceDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            euro.setMinValue(0);
            euro.setMaxValue(Integer.MAX_VALUE);
            euro.setValue(0);

            String[] cents = setCentsValue();

            cent.setDisplayedValues(cents);
            cent.setMinValue(0);
            cent.setMaxValue(99);
            cent.setValue(0);

            priceDialog.show();
    }

    private void setQuantity(){
        AlertDialog quantDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = LayoutInflater.from(EditOffer.this);
        final View view = inflater.inflate(R.layout.quantity_dialog, null);

        NumberPicker quantity = view.findViewById(R.id.quant_picker);

        quantDialog.setView(view);

        quantDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quantValue = quantity.getValue();
                quant.setText(""+quantValue);
            }
        });
        quantDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        quantity.setMinValue(1);
        quantity.setMaxValue(Integer.MAX_VALUE);
        quantity.setValue(1);

        quantDialog.show();
    }

    private void editPhoto(){
        AlertDialog alertDialog = new AlertDialog.Builder(EditOffer.this, R.style.AlertDialogStyle).create();
        LayoutInflater factory = LayoutInflater.from(EditOffer.this);
        final View view = factory.inflate(R.layout.custom_dialog, null);

        dialog_open = true;

        alertDialog.setOnCancelListener(dialog -> {
            dialog_open = false;
            alertDialog.dismiss();
        });

        view.findViewById(R.id.camera).setOnClickListener( c -> {
            cameraIntent();
            dialog_open = false;
            alertDialog.dismiss();
        });
        view.findViewById(R.id.gallery).setOnClickListener( g -> {
            galleryIntent();
            dialog_open = false;
            alertDialog.dismiss();
        });
        alertDialog.setView(view);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Camera", (dialog, which) -> {
            cameraIntent();
            dialog_open = false;
            dialog.dismiss();
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Gallery", (dialog, which) -> {
            galleryIntent();
            dialog_open = false;
            dialog.dismiss();
        });
        alertDialog.show();
    }

    private void cameraIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("FILE: ","error creating file");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 2);
            }
        }
    }

    private void galleryIntent(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    PERMISSION_GALLERY_REQUEST);
        }
        else{
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 1);
        }
    }

    private void setPhoto(String photoPath) throws IOException {
        File imgFile = new File(photoPath);

        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        myBitmap = adjustPhoto(myBitmap, photoPath);

        ((ImageView)findViewById(R.id.img_profile)).setImageBitmap(myBitmap);
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

    private static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File( storageDir + File.separator +
                imageFileName + /* prefix */
                ".jpg"
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();

        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_GALLERY_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Permission Run Time: ", "Obtained");

                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 1);
                } else {
                    Log.d("Permission Run Time: ", "Denied");

                    Toast.makeText(getApplicationContext(), "Access to media files denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //Log.d("Photo path: ", picturePath);
            currentPhotoPath = picturePath;
        }

        if((requestCode == 1 || requestCode == 2) && resultCode == RESULT_OK){
            File imgFile = new File(currentPhotoPath);
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            try {
                myBitmap = adjustPhoto(myBitmap, currentPhotoPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((ImageView)findViewById(R.id.img_profile)).setImageBitmap(myBitmap);
        }
    }
}

package com.example.oliviermedec.pducmaterial.Cache;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.example.oliviermedec.pducmaterial.Fragment.Categories.Categorie;
import com.example.oliviermedec.pducmaterial.Fragment.ProductList.Product;
import com.example.oliviermedec.pducmaterial.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by oliviermedec on 10/01/2017.
 */

public class Cache {
    private Context context;
    private Gson gson;
    ObjectMapper mapper = new ObjectMapper();

    public Cache(Context context){
        this.context = context;
        gson = new Gson();
    }

    public void serealize(Object object, String id) throws IOException {
        File file = new File(context.getFilesDir(), id);
        mapper.writeValue(file, object);
    }

    public Object deserialize(String id, Class type) throws IOException {
        File file = new File(context.getFilesDir(), id);
        Object object = mapper.readValue(file, type);
        return object;
    }

    public List<Categorie> getListCategory(String id){
        CacheFileReader cacheFileReader = new CacheFileReader();
        String jsonCat = cacheFileReader.read(context.getFilesDir() + "/" + id);

        if (jsonCat == null) {
            return null;
        }
        return gson.fromJson(jsonCat, new TypeToken<List<Categorie>>(){}.getType());
    }

    public List<Product> getListProduct(String id){
        CacheFileReader cacheFileReader = new CacheFileReader();
        String jsonCat = cacheFileReader.read(context.getFilesDir() + "/" + id);

        if (jsonCat == null) {
            return null;
        }
        return gson.fromJson(jsonCat, new TypeToken<List<Product>>(){}.getType());
    }

    public void delFile(String id) {
        File file = new File(context.getFilesDir(), id);
        file.delete();
    }


    public void storePicture(Bitmap bitmapImage, String id) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory, id);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //bitmapImage.recycle();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getPictureFile(String id){
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, id);
        System.gc();
        if (mypath.exists()){
            return mypath;
        }
        return null;
    }

    public void loadPicture(final ImageView imageView, final String image) {
        if (imageView.getDrawable() == null) {
            File img = getPictureFile(image);
            if (img != null) {
                Picasso.with(context).load(img).into(imageView);
            } else {
                Picasso.with(context).load(context.getResources().getString(R.string.server_url) +
                        "/images/" + image).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        storePicture(bitmap, image);
                        //bitmap.recycle();
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        }
    }
}
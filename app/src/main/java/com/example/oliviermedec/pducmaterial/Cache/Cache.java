package com.example.oliviermedec.pducmaterial.Cache;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.oliviermedec.pducmaterial.Fragment.Categories.Categorie;
import com.example.oliviermedec.pducmaterial.Fragment.ProductList.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
        Log.w(Cache.class.toString(), jsonCat);
        return gson.fromJson(jsonCat, new TypeToken<List<Categorie>>(){}.getType());
    }

    public List<Product> getListProduct(String id){
        CacheFileReader cacheFileReader = new CacheFileReader();
        String jsonCat = cacheFileReader.read(context.getFilesDir() + "/" + id);

        if (jsonCat == null) {
            return null;
        }
        Log.w(Cache.class.toString(), jsonCat);
        return gson.fromJson(jsonCat, new TypeToken<List<Product>>(){}.getType());
    }


    public void storePicture(Bitmap bitmapImage, String id){
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
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getPicture(String id){
        try {
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File mypath = new File(directory, id);

            System.out.println("Before");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));
            System.out.println("after");
            System.out.println(id);
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("File not found");
            return null;
        }
    }
}
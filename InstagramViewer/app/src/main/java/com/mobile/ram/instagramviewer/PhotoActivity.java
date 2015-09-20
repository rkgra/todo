package com.mobile.ram.instagramviewer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotoActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "8ff4b8c86c0f402cb4469c8b93513b6a";

    private ArrayList<InstagramPhoto> alPhotos;

    private InstagramPhotoAdapter aPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        alPhotos=new ArrayList<>();

        // Create adaptor linking to source

        aPhoto=new InstagramPhotoAdapter(this,alPhotos);

        // Find a list view from layout

        ListView lvPhotos= (ListView)findViewById(R.id.lvPhotos);

        //Set the adaptor binding it to the listview

        lvPhotos.setAdapter(aPhoto);

        // Get Popular photo
        fetchPopularPhoto();
    }
/*

https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN

Data : caption--> text
Data : user --> username
Data images-standard_resolution --> URL
Auther Name --User --username

 */


    private void fetchPopularPhoto() {

        String url= "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;

        // create network client
        AsyncHttpClient client =new AsyncHttpClient();
        // trigger the get request
         client.get(url,null, new JsonHttpResponseHandler(){


             // Working status code 200

               @Override
             public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   JSONArray photosJSON=null;


                   try {
                       // Get JSON array
                       photosJSON =response.getJSONArray("data");
                      // iterate Array
                       for (int i=0;i<photosJSON.length();i++){
                           JSONObject photoJSON= photosJSON.getJSONObject(i);

                           InstagramPhoto photo= new InstagramPhoto();

                           photo.username= photoJSON.getJSONObject("user").getString("username");

                           photo.caption=photoJSON.getJSONObject("caption").getString("text");

                           photo.imageUrl=photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");

                           photo.likesCount=photoJSON.getJSONObject("likes").getString("count");

                           photo.userProfileImageUrl=photoJSON.getJSONObject("caption").getJSONObject("from").getString("profile_picture");

                           photo.created_time=photoJSON.getJSONObject("caption").getString("created_time");




                           alPhotos.add(photo);


                       }

                   }catch (JSONException e){
                       e.printStackTrace();

                   }
                   //callback
                  aPhoto.notifyDataSetChanged();

                // Log.i("DEBUG", response.toString());
             }


             // Failed


             @Override
             public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

             }
         });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

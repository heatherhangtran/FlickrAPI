package com.randybiglow.apicall;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by RandyBiglow on 5/22/16.
 */
public class FlickrService {

    private static FlickrService instance;
    private static PhotoServiceCallback callback;

    private FlickrService(){
    //Method needs to be empty.
    }

    public static FlickrService getInstance(PhotoServiceCallback call) {
        callback = call;
        if(instance == null) {
            instance = new FlickrService();
        }
        return instance;
    }

    public void doRequest(String string){
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(
                //"https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_m.jpg"
                //"https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=05bdd790fdcd89f9344003e0f47d7c86&format=json&auth_token=72157668660429436-05706e41a0eafecf&api_sig=38cfa2c9020ba002592549763c5290f8",
                "https://www.flickr.com/photos" +
                null,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        String photoResult = null;

                        try {
                            JSONArray results = response.getJSONArray("photos");
                            JSONObject post = (JSONObject) results.get(0);
                            photoResult = post.getString("photo");

                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                        callback.handleCallback(photoResult);
                    }



                }
        );
    }
}

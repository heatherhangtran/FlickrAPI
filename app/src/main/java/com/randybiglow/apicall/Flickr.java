package com.randybiglow.apicall;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by RandyBiglow on 5/22/16.
 */
public class Flickr {

    private static Flickr instance;
    private static PhotoServiceCallback callback;
    private String id, secret, server, farm;
    public String link;

    private Flickr(){
    //Method needs to be empty.
    }

    public static Flickr getInstance(PhotoServiceCallback call) {
        callback = call;
        if(instance == null) {
            instance = new Flickr();
        }
        return instance;
    }

    public void doRequest(String input){
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(
                //"https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_m.jpg"
                //"https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=05bdd790fdcd89f9344003e0f47d7c86&format=json&auth_token=72157668660429436-05706e41a0eafecf&api_sig=38cfa2c9020ba002592549763c5290f8",
                //"https://www.flickr.com/photos" +
                "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=736d19d58703d5da37a1c87aeed71f96&tags=" + input + "&format=json&nojsoncallback=1",
                null,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        String photoResult = null;

                        try {
                            JSONObject results = response.getJSONObject("photos");
                            JSONArray post = results.getJSONArray("photo");
                            JSONObject somethingDifferent = post.getJSONObject(0);
                            id = somethingDifferent.getString("id");
                            secret = somethingDifferent.getString("secret");
                            server = somethingDifferent.getString("server");
                            farm = somethingDifferent.getString("farm");
                            photoResult = "https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_m.jpg";
                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                        callback.handleCallback(photoResult);
                    }
                }
        );
    }
}

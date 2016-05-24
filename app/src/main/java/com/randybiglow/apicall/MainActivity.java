package com.randybiglow.apicall;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements PhotoServiceCallback{

    private EditText mEditText;
    private ImageView mImageView;
    private Button mButton;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){

        }else {
            Toast.makeText(MainActivity.this, "Internet Connection Not Available", Toast.LENGTH_LONG).show();
        }

        mEditText = (EditText) findViewById(R.id.editText);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mButton = (Button) findViewById(R.id.button);
        mDialog = new ProgressDialog(this);

    }

    public void buttonClicked(View view) {
        String input = mEditText.getText().toString();
        if (input.length() == 0) {
            Toast.makeText(MainActivity.this, "What are you looking for?", Toast.LENGTH_LONG).show();

        } else {
            FlickrService.getInstance(MainActivity.this).doRequest();
            mEditText.setText("");
        }

        mDialog.setMessage("Loading...");
        mDialog.show();

        try {
            InputMethodManager hideKeyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            hideKeyboard.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }catch (Exception e){
        }
    }

    @Override
    public void handleCallback(String response) {
        Picasso.with(this)
                .load(response)
                .into(mImageView);
    }
}

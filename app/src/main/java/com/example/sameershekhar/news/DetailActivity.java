package com.example.sameershekhar.news;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    CollapsingToolbarLayout collapsingToolbar;

    FloatingActionButton floatingActionButton;
    Button readMore;
    TextView description;
    ImageView imageView;

    TextView updatesat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String theme=PreferenceManager.getDefaultSharedPreferences(this).getString("units", "na");
        Log.v("bona3",theme);
        if(theme.equals("Night"))
            setTheme(R.style.AppTheme1);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);


        setContentView(R.layout.activity_detail);
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        collapsingToolbar=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        description=(TextView)findViewById(R.id.place_detail);
        imageView= (ImageView) findViewById(R.id.image);
        updatesat=(TextView)findViewById(R.id.place_location);
         readMore=(Button)findViewById(R.id.readMore);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            //NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        final String Description,title,url,updated,imageUrl;
        Drawable image;
        Intent intent=getIntent();
       Description=intent.getStringExtra("DES");
        title=intent.getStringExtra("TITLE");
        imageUrl=intent.getStringExtra("IMAGEURL");
        updated=intent.getStringExtra("UPDATED");
        url=intent.getStringExtra("LINK");
       String[] publish=updated.split("T");

        collapsingToolbar.setTitle(title);

        updatesat.setText(publish[0]);
        description.setText(title+". "+Description);
        Picasso.with(getApplicationContext()).load(imageUrl).into(imageView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareInfromation(title,url);
            }
        });

        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openWebView(url,title);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public void openWebView(String url, String title)
    {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progreesbar, null);
        alert.setView(dialogView);



        final WebView myWebView = (WebView)dialogView.findViewById(R.id.webview);
        final ProgressBar myPB = (ProgressBar)dialogView.findViewById(R.id.progressbar);

        // Add WebViewClient to be notified of page load
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                myWebView.setVisibility(View.INVISIBLE);
                myPB.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url){
                // Show webview and hide progress bar
                myWebView.setVisibility(View.VISIBLE);
                myPB.setVisibility(View.INVISIBLE);
            }
        });
        myWebView.loadUrl(url);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();


    }





    public void shareInfromation(String name,String url) {
        String mimeType = "text/plain";
        String Title = "Choose From...";
        Uri myUri = Uri.parse(url);
        ShareCompat.IntentBuilder.from(DetailActivity.this)
                .setType(mimeType)
                .setChooserTitle(Title)
                .setText(name+" "+myUri)
                .startChooser();


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        recreate();
    }

}

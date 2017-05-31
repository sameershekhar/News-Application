package com.example.sameershekhar.news;


import android.content.Context;
import android.content.Intent;


import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.client.utils.URIBuilder;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import com.example.sameershekhar.news.DataUtils.DataClass;
import com.example.sameershekhar.news.DataUtils.Netutils;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchedResult extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
  public  Context context=this;
    public List<DataClass> busineessArray;
   public MoviesAdapter adapter;
    RecyclerView recyclerView;
    public ProgressBar prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        String theme=PreferenceManager.getDefaultSharedPreferences(this).getString("units", "na");
        Log.v("bona3",theme);
        if(theme.equals("Night"))
            setTheme(R.style.AppTheme1);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_result);
        Intent intent = getIntent();


        String news=intent.getStringExtra("NEWSTYPE").toLowerCase();
        Toast.makeText(getApplicationContext(),news,Toast.LENGTH_LONG).show();
        Toolbar tool=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prog=(ProgressBar)findViewById(R.id.progressbar1);
        recyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setVisibility(View.GONE);
        String url=null;
        Map<String, String> queries = new HashMap<String, String>();


       queries.put("q",news+" language:(english) performance_score:>5 (site_type:news)");

        try {
            URIBuilder builder = new URIBuilder(String.format("%s/%s?token=%s&format=json", "http://webhose.io", "filterWebData", "028420f0-186e-4fad-978e-03ee46f1a072"));
            for (String key : queries.keySet()) {
                builder.addParameter(key, queries.get(key));
            }

           url=builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // builder = new URIBuilder("https://api.cognitive.microsoft.com/bing/v5.0/news/");

        RequestQueue requestQueue= Volley.newRequestQueue(context);


        StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final List<DataClass> list;
               list=Netutils.extractSearchedData(response);
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                Log.v("hoka",response);
                prog.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                adapter=new MoviesAdapter(list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            //NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        recreate();
    }

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

        List<DataClass> businessArrayList;
        List<DataClass> originalList;
        public MoviesAdapter(List<DataClass> moviesList) {
            this.businessArrayList = moviesList;
            this.originalList=moviesList;
            busineessArray=moviesList;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name,description;
            public ImageView imageView;
             public Button read;
            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.card_title);
                description = (TextView) view.findViewById(R.id.card_text);
                imageView= (ImageView) view.findViewById(R.id.card_image);
                read=(Button)view.findViewById(R.id.action_button);
                read.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();
                        int pos = getAdapterPosition();

                        Intent intent=new Intent(context,DetailActivity.class);
                        intent.putExtra("DES",busineessArray.get(pos).getDescription());
                        intent.putExtra("TITLE",busineessArray.get(pos).getTitle());
                        intent.putExtra("IMAGEURL",busineessArray.get(pos).getUrltoImage());
                        intent.putExtra("UPDATED",busineessArray.get(pos).getUpdates());
                        intent.putExtra("LINK",busineessArray.get(pos).getUrl());
                        context.startActivity(intent);
                    }
                });
            }
        }




        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.searched_result_single_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            DataClass movie = originalList.get(position);
            holder.name.setText(movie.getTitle());
            holder.description.setText(movie.getDescription());
            Glide.with(context).load(originalList.get(position).getUrltoImage()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return originalList.size();
        }
    }
}

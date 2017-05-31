package com.example.sameershekhar.news;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sameershekhar.news.DataUtils.DataClass;
import com.example.sameershekhar.news.DataUtils.Netutils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class cricket extends Fragment {


    public static Activity activity;
    public static Context context;

    ProgressDialog progress;
    public static List<DataClass> cricketArray1;
    public static RecyclerView recyclerView;
    public static ContentAdapter adapter;
    public cricket() {

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        this.context = activity.getApplicationContext();
        progress = ProgressDialog.show(getActivity(), "",
                "loading....", true);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainActivity.flag&&progress!=null&&progress.isShowing())
            progress.dismiss();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);


        recyclerView.setAdapter(adapter);
        RequestQueue requestQueue= Volley.newRequestQueue(context);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final ArrayList<DataClass> cricketDataStorages1;
                cricketDataStorages1 = Netutils.extractCricketData(response);

                RequestQueue requestQueue1=Volley.newRequestQueue(context);
                StringRequest stringRequest1=new StringRequest(Request.Method.GET, Netutils.getUsgsCricketTopUrl(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<DataClass> cricketDataStorages2;
                        cricketDataStorages2 = Netutils.extractCricketData(response);
                        cricketDataStorages1.addAll(cricketDataStorages2);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                requestQueue1.add(stringRequest1);

                Collections.shuffle(cricketDataStorages1);
                 adapter = new ContentAdapter(recyclerView.getContext(),cricketDataStorages1);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(stringRequest);
        progress.dismiss();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        Button action;
        ProgressBar progressBar;
        ImageButton share_button, favourite_button;

        public ViewHolder(final LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_cricket, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            action = (Button) itemView.findViewById(R.id.action_button);
            share_button = (ImageButton) itemView.findViewById(R.id.share_button);


            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareInfromation(getAdapterPosition(), name.getText().toString(), description.getText().toString(),
                            cricketArray1.get(getAdapterPosition()).getUrl());
                }
            });

            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int pos = getAdapterPosition();

                    Intent intent=new Intent(context,DetailActivity.class);
                    intent.putExtra("DES",cricketArray1.get(pos).getDescription());
                    intent.putExtra("TITLE",cricketArray1.get(pos).getTitle());
                    intent.putExtra("IMAGEURL",cricketArray1.get(pos).getUrltoImage());
                    intent.putExtra("UPDATED",cricketArray1.get(pos).getUpdates());
                    intent.putExtra("LINK",cricketArray1.get(pos).getUrl());
                    context.startActivity(intent);
                }
            });

        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable{

        List<DataClass> cricketData;
        List<DataClass> originalList;

        public ContentAdapter(Context context,ArrayList<DataClass> cricketData) {


            this.cricketData=cricketData;
            cricketArray1=cricketData;
           originalList=cricketData;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Glide.with(context).load(cricketData.get(position).getUrltoImage()).into(holder.picture);
            holder.name.setText(cricketData.get(position).getTitle());
            holder.description.setText(cricketData.get(position).getDescription());

        }

        @Override
        public int getItemCount() {
            if(cricketData==null)
                return 0;
            else
            return cricketData.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    cricketData = (List<DataClass>) results.values;
                    adapter.notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<DataClass> filteredResults = null;
                    if (constraint.length() == 0) {
                        {
                            filteredResults = originalList;
                            cricketArray1=originalList;

                        }
                    } else {
                        filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                    }

                    FilterResults results = new FilterResults();
                    results.values = filteredResults;

                    return results;
                }
            };
        }

        protected List<DataClass> getFilteredResults(String constraint) {
            List<DataClass> results = new ArrayList<>();

            for (int i=0;i<originalList.size();i++) {
                String s=originalList.get(i).getTitle();
                if (s.toLowerCase().contains(constraint)) {
                    results.add(originalList.get(i));


                }
            }
            cricketArray1=results;

            return results;
        }

    }


    public static void shareInfromation(int pos, String name, String description,String url) {
        String mimeType = "text/plain";
        String Title = "Choose From...";
        Uri myUri = Uri.parse(url);
        ShareCompat.IntentBuilder.from(activity)
                .setType(mimeType)
                .setChooserTitle(Title)
                .setText(name+" "+myUri)
                .startChooser();


    }

    public static void SearchPlayer(String text)
    {
        Log.v("2nd",text+"2");
        adapter.getFilter().filter(text);

    }

}
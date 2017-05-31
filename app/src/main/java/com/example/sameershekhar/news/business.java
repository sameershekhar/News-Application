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
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class business extends Fragment {


    public static Activity activity;
    public static Context context;
    private static ContentAdapter adapter;
    public static RecyclerView recyclerView;
    private static List<DataClass> busineessArray;
    ProgressDialog progress;

    public business() {

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        this.context = activity.getApplicationContext();
        progress = ProgressDialog.show(getActivity(), "",
                "loading....", true);
         // Global varibale

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




            RequestQueue requestQueue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Netutils.getUsgsBusinessUrl(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    final ArrayList<DataClass> cricketDataStorages1;
                    cricketDataStorages1 = Netutils.extractCricketData(response);

                    RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                    StringRequest stringRequest1 = new StringRequest(Request.Method.GET, Netutils.getUsgsBusinessUrlTOP(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ArrayList<DataClass> cricketDataStorages2;
                            cricketDataStorages2 = Netutils.extractCricketData(response);
                            cricketDataStorages1.addAll(cricketDataStorages2);

                            RequestQueue requestQueue2 = Volley.newRequestQueue(context);
                            final StringRequest stringRequest2 = new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlBusinessTop1(), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    ArrayList<DataClass> cricketDataStorages3;
                                    cricketDataStorages3 = Netutils.extractCricketData(response);
                                    cricketDataStorages1.addAll(cricketDataStorages3);
                                    RequestQueue requestQueue3 = Volley.newRequestQueue(context);
                                    StringRequest stringRequest3 = new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlBusinessTop2(), new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            ArrayList<DataClass> cricketDataStorages4;
                                            cricketDataStorages4 = Netutils.extractCricketData(response);
                                            cricketDataStorages1.addAll(cricketDataStorages4);
                                            RequestQueue requestQueue4 = Volley.newRequestQueue(context);
                                            StringRequest stringRequest4 = new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlBusinessTop3(), new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    ArrayList<DataClass> cricketDataStorages5;
                                                    cricketDataStorages5 = Netutils.extractCricketData(response);
                                                    cricketDataStorages1.addAll(cricketDataStorages5);
                                                    RequestQueue requestQueue5 = Volley.newRequestQueue(context);
                                                    StringRequest stringRequest5 = new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlBusinessTop4(), new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            ArrayList<DataClass> cricketDataStorages6;
                                                            cricketDataStorages6 = Netutils.extractCricketData(response);
                                                            cricketDataStorages1.addAll(cricketDataStorages6);

                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {

                                                        }
                                                    });
                                                    requestQueue5.add(stringRequest5);


                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            });
                                            requestQueue4.add(stringRequest4);

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });
                                    requestQueue3.add(stringRequest3);


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            requestQueue2.add(stringRequest2);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    requestQueue1.add(stringRequest1);

                    progress.dismiss();
                    Collections.shuffle(cricketDataStorages1);
                    adapter = new ContentAdapter(recyclerView.getContext(), cricketDataStorages1);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });


            Log.v("2nd", "business1");
            requestQueue.add(stringRequest);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        Button action;
        ImageButton share_button, favourite_button;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_business, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            action = (Button) itemView.findViewById(R.id.action_button);
            share_button = (ImageButton) itemView.findViewById(R.id.share_button);


            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareInfromation(getAdapterPosition(), name.getText().toString(), description.getText().toString(),
                            busineessArray.get(getAdapterPosition()).getUrl());
                }
            });

            action.setOnClickListener(new View.OnClickListener() {
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

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable{
        List<DataClass> businessArrayList;
        List<DataClass> originalList;

        public ContentAdapter(Context context, ArrayList<DataClass> businessArrayList) {
            this.businessArrayList = businessArrayList;
            busineessArray = businessArrayList;
            originalList=businessArrayList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Glide.with(context).load(businessArrayList.get(position).getUrltoImage()).into(holder.picture);
            holder.name.setText(businessArrayList.get(position).getTitle());
            holder.description.setText(businessArrayList.get(position).getDescription());
        }

        @Override
        public int getItemCount() {
            return businessArrayList.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    businessArrayList = (List<DataClass>) results.values;
                    adapter.notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<DataClass> filteredResults = null;
                    if (constraint.length() == 0) {
                        {
                            filteredResults =originalList;
                            busineessArray=originalList;

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
            busineessArray=results;
            return results;
        }

    }


    public static void shareInfromation(int pos, String name, String description,String url) {

        Uri uri=Uri.parse(url);
        String mimeType = "text/plain";
        String Title = "Place Information";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ShareCompat.IntentBuilder.from(activity)
                //.addStream(imageUri)
                .setType(mimeType)
                .setChooserTitle(Title)
                .setText(name +" "+uri)
                .startChooser();


    }

    public static void SearchPlayer(String text)
    {
        Log.v("2nd",text+"2");
        adapter.getFilter().filter(text);

    }
}

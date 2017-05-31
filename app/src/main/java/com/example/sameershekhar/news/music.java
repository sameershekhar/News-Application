package com.example.sameershekhar.news;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class music extends Fragment {


    public static Activity activity;
    public static Context context;
    ProgressDialog progress;
    public static List<DataClass> musicArray;
    public static RecyclerView recyclerView;
    public static ContentAdapter adapter;
    public music() {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);



        recyclerView.setAdapter(adapter);
        RequestQueue requestQueue= Volley.newRequestQueue(context);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, Netutils.getUsgsMusicUrlTOP(),new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final ArrayList<DataClass> cricketDataStorages1;
                cricketDataStorages1 = Netutils.extractCricketData(response);

                RequestQueue requestQueue1=Volley.newRequestQueue(context);
                StringRequest stringRequest1=new StringRequest(Request.Method.GET, Netutils.getUsgsMusicUrl(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<DataClass> cricketDataStorages2;
                        cricketDataStorages2 = Netutils.extractCricketData(response);
                        cricketDataStorages1.addAll(cricketDataStorages2);
                        RequestQueue requestQueue2=Volley.newRequestQueue(context);
                        StringRequest stringRequest2=new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlMusicTop1(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                ArrayList<DataClass> cricketDataStorages3;
                                cricketDataStorages3 = Netutils.extractCricketData(response);
                                cricketDataStorages1.addAll(cricketDataStorages3);
                                RequestQueue requestQueue3=Volley.newRequestQueue(context);
                                StringRequest stringRequest3=new StringRequest(Request.Method.GET, Netutils.getUsgsRequestUrlMusicTop2(), new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        ArrayList<DataClass> cricketDataStorages4;
                                        cricketDataStorages4 = Netutils.extractCricketData(response);
                                        cricketDataStorages1.addAll(cricketDataStorages4);
                                        RequestQueue requestQueue4=Volley.newRequestQueue(context);
                                        StringRequest stringRequest4=new StringRequest(Request.Method.GET, Netutils.getUsgsMusicUrlTOP3(), new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                ArrayList<DataClass> cricketDataStorages5;
                                                cricketDataStorages5 = Netutils.extractCricketData(response);
                                                cricketDataStorages1.addAll(cricketDataStorages5);

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
                Collections.shuffle(cricketDataStorages1,new Random(System.nanoTime()));
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
        ImageButton share_button, favourite_button;

        public ViewHolder(final LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_music, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            action = (Button) itemView.findViewById(R.id.action_button);
            share_button = (ImageButton) itemView.findViewById(R.id.share_button);


            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareInfromation(getAdapterPosition(), name.getText().toString(), description.getText().toString(),
                            musicArray.get(getAdapterPosition()).getUrl());
                }
            });

            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int pos = getAdapterPosition();

                    Intent intent=new Intent(context,DetailActivity.class);
                    intent.putExtra("DES",musicArray.get(pos).getDescription());
                    intent.putExtra("TITLE",musicArray.get(pos).getTitle());
                    intent.putExtra("IMAGEURL",musicArray.get(pos).getUrltoImage());
                    intent.putExtra("UPDATED",musicArray.get(pos).getUpdates());
                    intent.putExtra("LINK",musicArray.get(pos).getUrl());
                    context.startActivity(intent);
                }
            });

        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable{

        List<DataClass> musicData;
        List<DataClass> originalList;
        public ContentAdapter(Context context,ArrayList<DataClass> musicData) {

            this.musicData=musicData;
            musicArray=musicData;
            originalList=musicData;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Glide.with(context).load(musicData.get(position).getUrltoImage()).into(holder.picture);
            holder.name.setText(musicData.get(position).getTitle());
            holder.description.setText(musicData.get(position).getDescription());
        }

        @Override
        public int getItemCount() {
            if(musicData==null)
                return 0;
            else
                return musicData.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    musicData = (List<DataClass>) results.values;
                    adapter.notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<DataClass> filteredResults = null;
                    if (constraint.length() == 0) {
                        {
                            filteredResults =originalList;
                            musicArray=originalList;

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
            musicArray=results;
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

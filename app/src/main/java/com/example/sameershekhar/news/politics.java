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
import com.example.sameershekhar.news.DataUtils.FeedItem;
import com.example.sameershekhar.news.DataUtils.HandleXml;
import com.example.sameershekhar.news.DataUtils.Netutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class politics extends Fragment {


    public static Activity activity;

    public static Context context;

    private HandleXml obj;
    ProgressDialog progress;
    public static List<FeedItem> scienceArray;
    public static RecyclerView recyclerView;
    public static ContentAdapter adapter;
    public politics() {

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



     HandleXml handleXml=new HandleXml(context);
        handleXml.execute();
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
            super(inflater.inflate(R.layout.fragment_politics, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            action = (Button) itemView.findViewById(R.id.action_button);
            share_button = (ImageButton) itemView.findViewById(R.id.share_button);


            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareInfromation(getAdapterPosition(), name.getText().toString(), description.getText().toString(),
                            scienceArray.get(getAdapterPosition()).getThumbNailUrl());
                }
            });

            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int pos = getAdapterPosition();

                    Intent intent=new Intent(context,DetailActivity.class);
                    intent.putExtra("DES",scienceArray.get(pos).getDescription());
                    intent.putExtra("TITLE",scienceArray.get(pos).getTitle());
                    intent.putExtra("IMAGEURL",scienceArray.get(pos).getThumbNailUrl());
                    intent.putExtra("UPDATED",scienceArray.get(pos).getDate());
                    intent.putExtra("LINK",scienceArray.get(pos).getLink());
                    context.startActivity(intent);
                }
            });

        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

        List<FeedItem> scienceData;
        List<FeedItem> originalList;
        public ContentAdapter(Context context,List<FeedItem> scienceData) {

            this.scienceData=scienceData;
            scienceArray=scienceData;
            originalList=scienceData;
        }

        @Override
        public politics.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {



            Glide.with(context).load(scienceData.get(position).getThumbNailUrl()).into(holder.picture);
            //Picasso.with(context).load(scienceData.get(position).getUrltoImage()).into(holder.picture);
            holder.name.setText(scienceData.get(position).getTitle());
            holder.description.setText(scienceData.get(position).getDescription());
        }

        @Override
        public int getItemCount() {
            if(scienceData==null)
                return 0;
            else
                return scienceData.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    scienceData = (List<FeedItem>) results.values;
                    adapter.notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<FeedItem> filteredResults = null;
                    if (constraint.length() == 0) {
                        {
                            filteredResults =originalList;
                            scienceArray=originalList;

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

        protected List<FeedItem> getFilteredResults(String constraint) {
            List<FeedItem> results = new ArrayList<>();

            for (int i=0;i<originalList.size();i++) {
                String s=originalList.get(i).getTitle();
                if (s.toLowerCase().contains(constraint)) {
                    results.add(originalList.get(i));

                }
            }
            scienceArray=results;
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

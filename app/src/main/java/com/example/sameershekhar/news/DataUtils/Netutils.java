package com.example.sameershekhar.news.DataUtils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by sameershekhar on 20-Mar-17.
 */

public class Netutils {
    private static final String USGS_REQUEST_URL_MUSIC_TOP2="https://newsapi.org/v1/articles?source=daily-mail&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_MUSIC_TOP1="https://newsapi.org/v1/articles?source=entertainment-weekly&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_MUSIC_TOP3="https://newsapi.org/v1/articles?source=mashablen&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_CRICKET = "https://newsapi.org/v1/articles?source=espn-cric-info&sortBy=latest&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_FOOTBALL= "https://newsapi.org/v1/articles?source=football-italia&sortBy=latest&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_BUSINESS=" https://newsapi.org/v1/articles?source=business-insider&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_BUSINESS_TOP1=" https://newsapi.org/v1/articles?source=fortune&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_BUSINESS_TOP2="https://newsapi.org/v1/articles?source=the-wall-street-journal&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_BUSINESS_TOP3=" https://newsapi.org/v1/articles?source=cnbc&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_BUSINESS_TOP4=" https://newsapi.org/v1/articles?source=the-economist&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_FINANCIAL="https://newsapi.org/v1/articles?source=ars-technica&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_FINANCIAL_TOP=" https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_FINANCIAL_TOP1=" https://newsapi.org/v1/articles?source=the-verge&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_FINANCIAL_TOP2="https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_MUSIC=" https://newsapi.org/v1/articles?source=the-lad-bible&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    static final String USGS_REQUEST_URL_BUSINESS_TOP=" https://newsapi.org/v1/articles?source=bloomberg&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_SCIENCE_TOP=" https://newsapi.org/v1/articles?source=new-scientist&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_SCIENCE_TOP1=" https://newsapi.org/v1/articles?source=new-scientist&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_FOOTBALL_TOP= "https://newsapi.org/v1/articles?source=football-italia&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_SCIENCE=" https://newsapi.org/v1/articles?source=new-scientist&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_CRICKET_top = "https://newsapi.org/v1/articles?source=espn-cric-info&sortBy=popular&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URl_GENERAL1 = " https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_GENERAL2 = " https://newsapi.org/v1/articles?source=the-hindu&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_GENERAL3 = "https://newsapi.org/v1/articles?source=usa-today&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_GENERAL4= "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_GENERAL5 = "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_GENERAL6 = "https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_GENERAL7 = " https://newsapi.org/v1/articles?source=time&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    private static final String USGS_REQUEST_URL_GENERAL8 = "https://newsapi.org/v1/articles?source=abc-news-au&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    public static String getUsgsRequestUrlGeneral7()
    {
        return USGS_REQUEST_URL_GENERAL7;
    }
    public static String getUsgsRequestUrlGeneral1()
    {
        return USGS_REQUEST_URl_GENERAL1;
    }
    public static String getUsgsRequestUrlGeneral2()
    {
        return USGS_REQUEST_URL_GENERAL2;
    }
    public static String getUsgsRequestUrlGeneral3()
    {
        return USGS_REQUEST_URL_GENERAL3;
    }
    public static String getUsgsRequestUrlGeneral4()
    {
        return USGS_REQUEST_URL_GENERAL4;
    }
    public static String getUsgsRequestUrlGeneral5()
    {
        return USGS_REQUEST_URL_GENERAL5;
    }
    public static String getUsgsRequestUrlGeneral6()
    {
        return USGS_REQUEST_URL_GENERAL6;
    }
    public static String getUsgsRequestUrlBusinessTop1()
    {
        return USGS_REQUEST_URL_BUSINESS_TOP1;

    }

    public static String getUsgsRequestUrlBusinessTop2()
    {
        return USGS_REQUEST_URL_BUSINESS_TOP2;
    }

    public static String getUsgsRequestUrlBusinessTop3()
    {
        return USGS_REQUEST_URL_BUSINESS_TOP3;
    }

    public static String getUsgsRequestUrlBusinessTop4()
    {
        return USGS_REQUEST_URL_BUSINESS_TOP4;

    }
    public static String getUsgsBusinessUrl()
    {
        return USGS_REQUEST_URL_BUSINESS;
    }
    public static String getUsgsBusinessUrlTOP()
    {
        return USGS_REQUEST_URL_BUSINESS_TOP;
    }

    public static String getUsgsRequestUrlFinancialTop2()
   {
       return USGS_REQUEST_URL_FINANCIAL_TOP2;
   }
    public static String getUsgsRequestUrlScienceTop1()
    {
        return USGS_REQUEST_URL_SCIENCE_TOP1;
    }

    public static String getUsgsRequestUrlMusicTop2()
    {
        return USGS_REQUEST_URL_MUSIC_TOP2;
    }
    public static String getUsgsRequestUrlFinancialTop1()
    {
        return USGS_REQUEST_URL_FINANCIAL_TOP1;
    }
    public static String getUsgsScienceUrl() {
        return USGS_REQUEST_URL_SCIENCE;
    }

    public static String getUsgsRequestUrlMusicTop1()
    {
        return USGS_REQUEST_URL_MUSIC_TOP1;
    }

    public static String getUsgsScienceUrlTOP() {
        return USGS_REQUEST_URL_SCIENCE_TOP;
    }
    public static String getUsgsMusicUrlTOP() {
        return USGS_REQUEST_URL_MUSIC_TOP;
    }
    public static String getUsgsMusicUrlTOP3() {
        return USGS_REQUEST_URL_MUSIC_TOP3;
    }
    private static final String USGS_REQUEST_URL_MUSIC_TOP=" https://newsapi.org/v1/articles?source=ign&sortBy=top&apiKey=2ef13745755243d882bd4c78b4035add";
    public static String getUsgsMusicUrl() {
        return USGS_REQUEST_URL_MUSIC;
    }
    public static String getUsgsCricketTopUrl() {
        return USGS_REQUEST_URL_CRICKET_top;
    }
    public static String getUsgsFootballTopUrl() {
        return USGS_REQUEST_URL_FOOTBALL_TOP;
    }
    public static String getUsgsFinancialUrl() {
        return USGS_REQUEST_URL_FINANCIAL;
    }
    public static String getUsgsFinancialUrlTOP() {
        return USGS_REQUEST_URL_FINANCIAL_TOP;
    }
    public static String getUsgsRequestUrl() {
        return USGS_REQUEST_URL_CRICKET;
    }
    public static String getUsgsFootballUrl()
    {
        return USGS_REQUEST_URL_FOOTBALL;
    }






    public static ArrayList<DataClass> extractCricketData(String SAMPLE_JSON_RESPONSE) {
        Log.d("ruku",SAMPLE_JSON_RESPONSE);



        ArrayList<DataClass> cricketDataStorageArrayList = new ArrayList<>();


        try {
            JSONObject root=new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray articles=root.getJSONArray("articles");

            String title;
            String description;
            String url;
            String urlImage;
             String updates;


            for(int i=0;i<articles.length();i++)
            {
                JSONObject forcastJSONObject=articles.getJSONObject(i);

                title=forcastJSONObject.getString("title");
                description=forcastJSONObject.getString("description");
                url=forcastJSONObject.getString("url");
                urlImage=forcastJSONObject.getString("urlToImage");
                updates=forcastJSONObject.getString("publishedAt");


                cricketDataStorageArrayList.add(i,new DataClass(title,description,url,urlImage,updates));
            }




        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }


        return cricketDataStorageArrayList;
    }


    public static ArrayList<DataClass> extractSearchedData(String response)
    {
        ArrayList<DataClass> cricketDataStorageArrayList = new ArrayList<>();


        try {
            JSONObject root=new JSONObject(response);
            JSONArray articles=root.getJSONArray("posts");

            String title;
            String description;
            String url;
            String urlImage;
            String updates;


            for(int i=0;i<articles.length();i++)
            {
                JSONObject forcastJSONObject=articles.getJSONObject(i);

                title=forcastJSONObject.getString("title");
                description=forcastJSONObject.getString("text");
                url=forcastJSONObject.getString("url");
                urlImage=forcastJSONObject.getJSONObject("thread").getString("main_image");
                updates=forcastJSONObject.getString("published");


                cricketDataStorageArrayList.add(i,new DataClass(title,description,url,urlImage,updates));
            }




        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }


        return cricketDataStorageArrayList;


    }

}

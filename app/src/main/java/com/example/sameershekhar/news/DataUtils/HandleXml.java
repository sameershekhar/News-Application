package com.example.sameershekhar.news.DataUtils;

/**
 * Created by sameershekhar on 11-Apr-17.
 */
import java.io.IOException;
import java.io.InputStream;
    import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
    import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.util.Log;

import com.example.sameershekhar.news.politics;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class HandleXml extends AsyncTask<Void,Void,Void> {
    private final Context context;
    List<FeedItem> list;
    private String finalUrl="view-source:http://www.bhaskar.com/rss-feed/2338/";

    public HandleXml(Context context) {
        this.context=context;
    }
    

    @Override
     protected void onPreExecute() {
         super.onPreExecute();
     }

    @Override
    protected void onPostExecute(Void aVoid) {

        politics.adapter=new politics.ContentAdapter(context,list);
        politics.adapter.notifyDataSetChanged();
        super.onPostExecute(aVoid);
    }

    @Override
     protected Void doInBackground(Void... voids) {
         Document document=null;
         try {
             URL url=new URL("http://rss.jagran.com/rss/news/national.xml");
             HttpURLConnection connection=(HttpURLConnection)url.openConnection();
             connection.setRequestMethod("GET");
             InputStream inputStream=connection.getInputStream();
             DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
             DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
             document=documentBuilder.parse(inputStream);
         } catch (MalformedURLException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } catch (ParserConfigurationException e) {
             e.printStackTrace();
         } catch (SAXException e) {
             e.printStackTrace();
         }
         Log.v("xml1",document.toString());
        Element root=document.getDocumentElement();
         Node channel=root.getChildNodes().item(1);
         NodeList items=channel.getChildNodes();

         for(int i=0;i<items.getLength();i++)
         {
             Node currentChild=items.item(i);
             if (currentChild.getNodeName().equalsIgnoreCase("item"))
             {
                 FeedItem item=new FeedItem();
                 NodeList itemChilds=currentChild.getChildNodes();
                 for(int j=0;j<itemChilds.getLength();j++)
                 {
                     Node current=itemChilds.item(j);
                     if(current.getNodeName().equalsIgnoreCase("title"))
                         item.setTitle(current.getTextContent());
                     else if(current.getNodeName().equalsIgnoreCase("description"))
                         item.setDescription(current.getTextContent());
                     else if(current.getNodeName().equalsIgnoreCase("pubDate"))
                         item.setDate(current.getTextContent());
                     else if(current.getNodeName().equalsIgnoreCase("link"))
                         item.setLink(current.getTextContent());
                 }

                 list.add(item);
             }
         }

         return null;
     }
 }


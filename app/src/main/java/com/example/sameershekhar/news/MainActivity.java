package com.example.sameershekhar.news;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,SharedPreferences.OnSharedPreferenceChangeListener{

    public static boolean flag=false;
    public static String language="English";
    public static String pageTitle="xxx";
    private String news_Type = "";
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String theme=PreferenceManager.getDefaultSharedPreferences(this).getString("units", "na");
        Log.v("bona3",theme);
        if(theme.equals("Night"))
            setTheme(R.style.AppTheme1);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.fab1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Search news...");
                LayoutInflater li = LayoutInflater.from(context);
               final View dialogView = li.inflate(R.layout.news_type, null);

                alert.setView(dialogView);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final EditText editText=(EditText)dialogView.findViewById(R.id.text);
                        Intent intent=new Intent(context,SearchedResult.class);
                        intent.putExtra("NEWSTYPE",editText.getText().toString());
                        startActivity(intent);
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = alert.create();


                alertDialog.show();
            }
        });


        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        //TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);



        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pageTitle=tab.getText().toString();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar,menu);
        //MenuItem menuItem=menu.getItem(R.id.action_search);
        MenuItem menuItem=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.action_settings)
        {
            Intent startSettingsActivity = new Intent(this,SettingActivity.class);
            startActivity(startSettingsActivity);
            return true;
            //PlayerStats.ContentAdapter.getFilter().filter(text);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        Toast.makeText(context,newText,Toast.LENGTH_LONG).show();
        newText=newText.toLowerCase();
        if(pageTitle.equals("Business"))
            business.SearchPlayer(newText);

        //else if(pageTitle.equals("Business"))
          // business.SearchPlayer(newText);
        else if (pageTitle.equals("Tech/Science"))
            financial.SearchPlayer(newText);
        else if(pageTitle.equals("Entertainment"))
            music.SearchPlayer(newText);
        else  if(pageTitle.equals("Cricket"))
            cricket.SearchPlayer(newText);
        else if(pageTitle.equals("Football"))
            football.SearchPlayer(newText);
        else
            science.SearchPlayer(newText);


        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        //adapter.addFragment(new business(), "Business");
        adapter.addFragment(new politics(),"HindiNews");
        adapter.addFragment(new science(), "General");
        adapter.addFragment(new business(), "Business");
        adapter.addFragment(new music(), "Entertainment");
        adapter.addFragment(new financial(), "Tech/Science");

        adapter.addFragment(new cricket(), "Cricket");
        adapter.addFragment(new football(), "Football");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        flag=true;
        language=PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.pref_language_key),"na");

        recreate();

    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}

package com.jogoler.jogolmaps;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Arief Wijaya on 06-Oct-16.
 */

public class SearchActivity extends AppCompatActivity {

    private CustomAdapter customAdapter;
    ListView listView;
    Cursor cursor;
    LocationRepo locationRepo ;
    private final static String TAG = SearchActivity.class.getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        locationRepo = new LocationRepo(this);
        cursor=locationRepo.getLocationList();
        customAdapter = new CustomAdapter(SearchActivity.this,  cursor, 0);
        listView = (ListView) findViewById(R.id.listLocation);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                TextView location_=(TextView) view.findViewById(R.id.txtId);
                TextView longitude = (TextView) view.findViewById(R.id.txtLongitude);
                TextView latitude = (TextView) view.findViewById(R.id.txtLatitude);
                /*Toast.makeText(SearchActivity.this,location_.getText().toString() +","+
                        longitude.getText().toString()+","+
                        latitude.getText().toString(),Toast.LENGTH_LONG).show();*/

//                Intent intent = new Intent(SearchActivity.this, UnsriMapListClickActivity.class);
                Intent intent = new Intent(SearchActivity.this, MapsResultActivity.class);
                // Pass all data rank
                intent.putExtra("id",(location_.getText().toString()));
                // Pass all data country
                intent.putExtra("longitude",(longitude.getText().toString()));
                // Pass all data population
                intent.putExtra("latitude",(latitude.getText().toString()));
                // Pass all data flag
                // Start SingleItemView Class
                startActivity(intent);
            }
        });

        if(cursor==null) insertDummy();

    }


    private void insertDummy(){
        Location location= new Location();

        location.longitude=0;
        location.latitude=0;
        location.desc="";
        location.name="Database Empty";
        locationRepo.insert(location);

    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            MenuItem searchMenuItem = menu.findItem(R.id.searchA);
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) searchMenuItem.getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            search.setIconifiedByDefault(false);
            search.setMaxWidth(Integer.MAX_VALUE);
            searchMenuItem.expandActionView();

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, "onQueryTextSubmit ");
                    cursor=locationRepo.getLocationListByKeyword(s);
                    if (cursor==null){
                        Toast.makeText(SearchActivity.this,"No location found !",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(SearchActivity.this, cursor.getCount() + " locations found !",Toast.LENGTH_LONG).show();
                    }
                    customAdapter.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d(TAG, "onQueryTextChange ");
                    cursor=locationRepo.getLocationListByKeyword(s);
                    if (cursor!=null){
                        customAdapter.swapCursor(cursor);
                    }
                    return false;
                }

            });

        }

        return true;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
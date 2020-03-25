package ca.ulaval.ima.tp3;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ca.ulaval.ima.tp3.ui.main.Offers;
import ca.ulaval.ima.tp3.ui.main.SectionsPagerAdapter;
import org.json.*;

public class MainActivity extends AppCompatActivity implements Offers.OffersFragmentListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

    @Override
    public void displayBrand() {
        final ArrayList<String> item = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);  // this

        final String url = "http://68.183.207.74/api/v1/brand/";

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        try {
                            final JSONArray jsonArray = response.getJSONArray("content");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                String name = c.getString("name");
                                item.add(name);
                                Log.i("DEBUG", name);
                            }
                            //Log.e("DEBUG", item.get(1));

                            ListView listView = findViewById(R.id.brand_list);

                            final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(
                                    MainActivity.this, android.R.layout.simple_list_item_1, item
                            );

                            listView.setAdapter(stringArrayAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject c;
                                        try {
                                            c = jsonArray.getJSONObject(i);
                                            String myId = c.getString("id");
                                            if(position==i){
                                                getModel(myId);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                }

                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("DEBUG", error.toString());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }

    private void getModel(String id){
        final ArrayList<String> arrayListModel = new ArrayList<>();
        int myId = Integer.parseInt(id);
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://68.183.207.74/api/v1//brand/"+myId+"/models/";

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        try {
                            final JSONArray jsonArray = response.getJSONArray("content");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                String name = c.getString("name");
                                arrayListModel.add(name);
                                Log.i("DEBUG", name);
                            }
                            Log.e("DEBUG", jsonArray.toString());

                            Intent intent = new Intent(MainActivity.this,Model.class);

                            intent.putExtra("data",arrayListModel);

                            startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }

}
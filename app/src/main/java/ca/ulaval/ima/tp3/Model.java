package ca.ulaval.ima.tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ca.ulaval.ima.tp3.domain.Car;
import ca.ulaval.ima.tp3.ui.main.fragmentpaquets.ModelFragment;

public class Model extends AppCompatActivity implements ModelFragment.ModeleFragmentListener {
    private ArrayList<String> models;
    private ArrayList<Car> carArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);


        /**Intent intent = getIntent();
        ListView listView = findViewById(R.id.model_list);
        models = intent.getStringArrayListExtra("data");

        final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,models
        );

        Log.e("DEBUG", stringArrayAdapter.getItem(1));

        listView.setAdapter(stringArrayAdapter);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        /**models= new ArrayList<>();
         Intent intent = getIntent();
         if (intent != null){
             models = intent.getStringArrayListExtra("data");
             if (models != null){
                 ListView listView = findViewById(R.id.model_list);
                 final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(Model.this,
                         android.R.layout.simple_list_item_1,models
                 );
                 Log.e("DEBUG", models.get(1));
                 listView.setAdapter(stringArrayAdapter);
                 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         for (int i = 0; i < models.size(); i++) {
                             JSONObject c = null;
                             try {
                                 Intent intent = getIntent();
                                 final JSONArray modelDescription = new JSONArray(intent.getStringExtra("jsonArray"));
                                 c = modelDescription.getJSONObject(i);
                                 String myId = c.getString("id");
                                 if(position==i){
                                     getModelOffers(myId);
                                 }
                             } catch (JSONException e) {
                                 e.printStackTrace();
                             } }
                     }
                 });

             }
         }*/
    }

    @Override
    public void displayModel() {
        models= new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null){
             models = intent.getStringArrayListExtra("data");
            if (models != null){
                ListView listView = findViewById(R.id.model_list);

                final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(Model.this,
                        android.R.layout.simple_list_item_1,models
                );

                Log.e("DEBUG", models.get(1));

                listView.setAdapter(stringArrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (int i = 0; i < models.size(); i++) {
                            if(position==i){
                                getModelOffers(models.get(i));
                            }
                        }
                    }
                });

            }
        }


    }


    private void getModelOffers(final String name){
        carArrayList = new ArrayList<>();
        final ArrayList<Integer> carId = new ArrayList<>();
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://68.183.207.74/api/v1/offer/search/" ;

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
                                String modelName = c.getJSONObject("model").getString("name");
                                if(modelName.equals(name)){
                                    String img = c.getString("image");
                                    int year =  c.getInt("year");
                                    int kilometers =  c.getInt("kilometers");
                                    int price =  c.getInt("price");
                                    JSONObject brandName = c.getJSONObject("model");
                                    int myCarId = c.getInt("id");
                                    JSONObject modelByBrand = brandName.getJSONObject("brand");
                                    String modelBrandName = modelByBrand.getString("name");
                                    Car car = new Car(img,year,kilometers,price,modelBrandName,modelName);
                                    carId.add(myCarId);
                                    carArrayList.add(car);
                                }
                            }
                            if(!carArrayList.isEmpty()){
                                Intent intent = new Intent(Model.this,OffersByVehicles.class);

                                intent.putParcelableArrayListExtra("data",carArrayList);
                                intent.putIntegerArrayListExtra("dataId",carId);

                                startActivity(intent);
                            }else {
                                Toast.makeText(Model.this, "Pas d'offres, Désolé", Toast.LENGTH_LONG).show();
                            }
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



    /**for (int i = 0; i < jsonArray.length(); i++) {
     JSONObject c = jsonArray.getJSONObject(i);
     String modelName = c.getJSONObject("model").getString("name");
     Log.e("DEBUG", name);
     Log.e("DEBUG", modelName);
     if(modelName.contains("Mustang")){
     String img = c.getString("image");
     int year =  Integer.parseInt(c.getString("year"));
     int kilometers =  Integer.parseInt(c.getString("kilometers"));
     int price =  Integer.parseInt(c.getString("price"));
     JSONObject brandName = c.getJSONObject("model");
     JSONObject modelByBrand = brandName.getJSONObject("brand");
     String modelBrandName = modelByBrand.getString("name");
     Car car = new Car(img,year,kilometers,price,modelBrandName,modelName);
     arrayListModelOffer.add(car);
     }else {
     Toast.makeText(Model.this, "Pas d'offres, Désolé", Toast.LENGTH_LONG).show();
     }
     }*/
}

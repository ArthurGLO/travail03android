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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ca.ulaval.ima.tp3.domain.Car;
import ca.ulaval.ima.tp3.domain.Salor;
import ca.ulaval.ima.tp3.ui.main.fragmentpaquets.OfferByModel;
import ca.ulaval.ima.tp3.ui.main.utils.CustomListview;

public class OffersByVehicles extends AppCompatActivity implements OfferByModel.OfferByModelFragmentListener {

    ListView myListView;
    private ArrayList<Car> offers;
    private ArrayList<Integer>carsId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_by_vehicles);
    }

    @Override
    public void displayOfferByModel() {
        offers= new ArrayList<>();
        carsId = new ArrayList<>();
        ArrayList<String> offerTitle = new ArrayList<>();
        ArrayList<String> offerYear = new ArrayList<>();
        ArrayList<String> offerKilometer = new ArrayList<>();
        ArrayList<String> offerPrice = new ArrayList<>();
        ArrayList<String> offerImg = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null){
            offers = intent.getParcelableArrayListExtra("data");
            carsId = intent.getIntegerArrayListExtra("dataId");
            if (offers != null && carsId!=null){
                myListView = findViewById(R.id.offer_list_byModel);
                for (int i = 0; i < offers.size();i++){
                    String name = offers.get(i).getBrandName()+" "+offers.get(i).getModelName();
                    String year = String.valueOf(offers.get(i).getYear());
                    String kilometer = String.valueOf(offers.get(i).getKilometers());
                    String price = String.valueOf(offers.get(i).getPrice());
                    String img = offers.get(i).getImg();
                    offerImg.add(img);
                    offerYear.add(year);
                    offerKilometer.add(kilometer);
                    offerPrice.add(price);
                    offerTitle.add(name);
                }

                CustomListview customListview = new CustomListview(this,offerTitle,offerYear,offerKilometer,offerPrice,offerImg);

                myListView.setAdapter(customListview);
                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        for (int i = 0; i < offers.size(); i++) {
                            if(position==i){
                                getDescriptionOffers(carsId.get(i));
                            }
                        }
                    }
                });
            }
        }

    }

    private void getDescriptionOffers(int id){
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url =  "http://68.183.207.74/api/v1/offer/"+id+"/details/" ;

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        try {
                            final JSONObject jsonContent = response.getJSONObject("content");
                            JSONObject objectModel = jsonContent.getJSONObject("model");
                            String modelName = objectModel.getString("name");
                            JSONObject brandObject = objectModel.getJSONObject("brand");
                            String brandName = brandObject.getString("name");
                            String img = jsonContent.getString("image");
                            int year = jsonContent.getInt("year");
                            int kilo = jsonContent.getInt("kilometers");
                            String trans = jsonContent.getString("transmission");
                            int price = jsonContent.getInt("price");

                            JSONObject objectSalor = jsonContent.getJSONObject("seller");
                            String salorLastName = objectSalor.getString("last_name");
                            String salorFirstName = objectSalor.getString("first_name");
                            String salorMail = objectSalor.getString("email");
                            Boolean owner = jsonContent.getBoolean("from_owner");
                            String desc = jsonContent.getString("description");

                            Car car = new Car(img,year,kilo,trans,owner,price,brandName,modelName,desc);
                            Salor salor = new Salor(salorFirstName,salorLastName,salorMail);
                            Intent intent = new Intent(OffersByVehicles.this,Description.class);

                            intent.putExtra("descriptionData",car);
                            intent.putExtra("salorData",salor);

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

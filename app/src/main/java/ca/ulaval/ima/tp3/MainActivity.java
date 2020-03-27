package ca.ulaval.ima.tp3;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ca.ulaval.ima.tp3.domain.Car;
import ca.ulaval.ima.tp3.ui.main.Offers;
import ca.ulaval.ima.tp3.ui.main.Sales;
import ca.ulaval.ima.tp3.ui.main.SectionsPagerAdapter;
import ca.ulaval.ima.tp3.ui.main.fragmentpaquets.UserDialog;

import org.json.*;

public class MainActivity extends AppCompatActivity implements Offers.OffersFragmentListener, UserDialog.DialogListener, Sales.SaleFragmentListenner {

    private ArrayList<String> carArrayLst;
    private TextView textModelChoice;
    private EditText textkiloM;
    private EditText textPrice;
    private CheckBox checkBox;
    private Button buttonSupply;
    private Spinner spinner;
    private TextView textviewYear;
    private String record = "";

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
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        try {
                            final JSONArray jsonArray = response.getJSONArray("content");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                String name = c.getString("name");
                                item.add(name);
                            }

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
                                            if (position == i) {
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
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("DEBUG", error.toString());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }

    private void getModel(String id) {
        final ArrayList<String> arrayListModel = new ArrayList<>();
        int myId = Integer.parseInt(id);
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://68.183.207.74/api/v1//brand/" + myId + "/models/";

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        try {
                            final JSONArray jsonArray = response.getJSONArray("content");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                String name = c.getString("name");
                                arrayListModel.add(name);
                            }

                            Intent intent = new Intent(MainActivity.this, Model.class);

                            intent.putExtra("data", arrayListModel);

                            startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }

    @Override
    public void displayModelAndBrand() {
        carArrayLst = new ArrayList<>();
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://68.183.207.74/api/v1/offer/search/";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        try {
                            final JSONArray jsonArray = response.getJSONArray("content");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                String modelName = c.getJSONObject("model").getString("name");
                                String brandName = c.getJSONObject("model").getJSONObject("brand").getString("name");
                                String car = brandName + " " + modelName;

                                carArrayLst.add(car);
                            }

                            Intent intent = new Intent(MainActivity.this, BrandAndModelCar.class);

                            intent.putStringArrayListExtra("dataModelAndBrand", carArrayLst);
                            Sales sales = new Sales();
                            startActivityFromFragment(sales,intent,12345);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

    }

    @Override
    public void setModel() {
        final Intent intent = getIntent();
        if (intent != null) {
            String e = intent.getStringExtra("modelChoice");
            if (e != null) {
                Log.e("DEBUG",e);
                TextView textView = findViewById(R.id.text_list);
                textView.setText(e);
            }
        }

    }

    @Override
    public void getAuthorize(final Car car){
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://68.183.207.74/api/v1/account/me/";

        final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(MainActivity.this, "Veuillez vous authentifier", Toast.LENGTH_LONG).show();
                        openDialog();
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }


    private void openDialog(){
        UserDialog userDialog = new UserDialog();
        userDialog.show(getSupportFragmentManager(), "dialog");
    }


    private void addOffer(String token) {
        textModelChoice = findViewById(R.id.text_list);
        textkiloM = findViewById(R.id.edit_year);
        textPrice = findViewById(R.id.edit_price);
        checkBox = findViewById(R.id.item_switch);
        buttonSupply = findViewById(R.id.edit_button);
        textviewYear = findViewById(R.id.choice_year);
        spinner = findViewById(R.id.spinner);

        record = spinner.getSelectedItem().toString();

        if(record == "Manuel"){
            record = "MA";
        }else if(record.equals("Automatique")){
            record = "AT";
        }else if (record.equals("Semi Automatique")){
            record = "SM";
        }

        if (textModelChoice.getText().toString().isEmpty()
                || textviewYear.getText().toString().isEmpty() || Integer.parseInt(textviewYear.getText().toString()) < 1000
                || textkiloM.getText().toString().isEmpty() || Integer.parseInt(textPrice.getText().toString()) < 1
                || textPrice.getText().toString().isEmpty()) {
            Toast.makeText(this, "Some text as empty values, please edit it", Toast.LENGTH_SHORT).show();
        } else {
            Boolean aBoolean = checkBox.isChecked();
            String salorPrice = textPrice.getText().toString();
            String salorChoice = textModelChoice.getText().toString();
            String[] recipients = salorChoice.split(" ");
            String salorKilo = textkiloM.getText().toString();
            String salorYear = textviewYear.getText().toString();

            Car car = new Car(Integer.parseInt(salorYear), Integer.parseInt(salorKilo),
                    record, aBoolean, Integer.parseInt(salorPrice), recipients[0],
                    recipients[1]);
                    getIdForPost(car,token);
        }
    }

    private void getIdForPost(final Car car, final String token){

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://68.183.207.74/api/v1/model/";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        try {
                            final JSONArray jsonArray = response.getJSONArray("content");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                if(car.getModelName().equals(c.getString("name"))){
                                    int modelid = c.getInt("id");
                                    doPost(car,modelid,token);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }



    private void doPost(final Car car, final int idModel, final String token){

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://68.183.207.74/api/v1/offer/add/";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(MainActivity.this, "Félicitation vous avez réussi à soumettre votre offre", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("Authorization","Basic"+token);

                return params;
            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("from_owner", car.getSale().toString());
                params.put("kilometers", String.valueOf(car.getKilometers()));
                params.put("year",String.valueOf(car.getYear()));
                params.put("price",String.valueOf(car.getPrice()));
                params.put("transmission",car.getTransmission());
                params.put("model",String.valueOf(idModel));

                return params;
            }
        };
        queue.add(postRequest);
    }


    @Override
    public void getAuthenticate(final String userMail, final int userIdul) {
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://68.183.207.74/api/v1/account/login/";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);
                        try {
                            JSONObject c = new JSONObject(response);
                            JSONObject content = c.getJSONObject("content");
                            String token = content.getString("token");
                            addOffer(token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("email", userMail);
                params.put("identification_number", String.valueOf(userIdul));

                return params;
            }
        };
        queue.add(postRequest);
    }

}
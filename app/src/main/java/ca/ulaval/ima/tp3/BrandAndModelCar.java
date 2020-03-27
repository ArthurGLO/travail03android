package ca.ulaval.ima.tp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import ca.ulaval.ima.tp3.domain.Car;
import ca.ulaval.ima.tp3.domain.Salor;
import ca.ulaval.ima.tp3.ui.main.Sales;

public class BrandAndModelCar extends AppCompatActivity implements Sales.SaleFragmentListenner {
    private ArrayList<String> carlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_and_model_car);
        setModel();
    }

    private void setCarModel(String carModel){

    }

    @Override
    public void displayModelAndBrand() {

    }

    @Override
    public void setModel() {
        carlist = new ArrayList<>();
        final Intent intent = getIntent();
        if (intent != null){
            carlist = intent.getStringArrayListExtra("dataModelAndBrand");
            if (carlist != null){
                ListView listView = findViewById(R.id.modelBrand_list);

                final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(
                        this, android.R.layout.simple_list_item_1, carlist
                );

                listView.setAdapter(stringArrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (int i = 0; i < carlist.size(); i++) {
                            if(position==i){
                                /**Intent intent = new Intent(BrandAndModelCar.this,MainActivity.class);
                                intent.putExtra("modelChoice",carlist.get(i));
                                Sales sales = new Sales();
                                startActivityForResult(intent,12345);*/


                            }
                        }

                    }

                });
            }
        }
    }

    @Override
    public void getAuthorize(Car car) {

    }

}

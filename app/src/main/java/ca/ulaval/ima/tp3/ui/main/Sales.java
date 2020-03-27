package ca.ulaval.ima.tp3.ui.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import ca.ulaval.ima.tp3.R;
import ca.ulaval.ima.tp3.domain.Car;

public class Sales extends Fragment {

    private SaleFragmentListenner mListener;
    private TextView textView;

    private TextView textModelChoice;
    private EditText textkiloM;
    private EditText textPrice;
    private CheckBox checkBox;
    private Button buttonSupply;
    private Spinner spinner;
    private String arraySpiner [] = {"Manuel","Automatique","Semi Automatique "};
    private ArrayAdapter<String> arrayAdapter;
    private String record = "";
    private TextView textviewYear;
    private DatePickerDialog.OnDateSetListener datePickerDialog;




    public Sales() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textView = getView().findViewById(R.id.text_list);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.displayModelAndBrand();
            }
        });






    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.sales, container, false);

        textModelChoice = root.findViewById(R.id.text_list);
        textkiloM = root.findViewById(R.id.edit_year);
        textPrice = root.findViewById(R.id.edit_price);
        checkBox = root.findViewById(R.id.item_switch);
        buttonSupply = root.findViewById(R.id.edit_button);
        textviewYear = root.findViewById(R.id.choice_year);
        spinner = root.findViewById(R.id.spinner);
        arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,arraySpiner);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        record =  "MA";
                        break;
                    case 1:
                        record = "AT";
                        break;
                    case 2:
                        record =  "SM";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkBox.setChecked(true);
        textPrice.setText("34567");
        textModelChoice.setText("Acura MDX");
        textkiloM.setText("5982");
        textviewYear.setText("2020");

        textviewYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogWithoutDateField();
            }
        });

        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = String.valueOf(year);
                textviewYear.setText(date);
            }

        };

        buttonSupply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textModelChoice.getText().toString().isEmpty()
                        || textviewYear.getText().toString().isEmpty() || Integer.parseInt(textviewYear.getText().toString()) < 1000
                        || textkiloM.getText().toString().isEmpty() || Integer.parseInt(textPrice.getText().toString()) < 1
                        || textPrice.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Some text as empty values, please edit it", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean aBoolean = checkBox.isChecked();
                    String salorPrice = textPrice.getText().toString();
                    String salorChoice = textModelChoice.getText().toString();
                    String[] recipients = salorChoice.split(" ");
                    String salorKilo = textkiloM.getText().toString();
                    String salorYear = textviewYear.getText().toString();

                    Car car = new Car(Integer.parseInt(salorYear),Integer.parseInt(salorKilo),
                            record,aBoolean,Integer.parseInt(salorPrice),recipients[0],
                            recipients[1]);
                    mListener.getAuthorize(car);
                }
            }
        });


        return root;
    }



    private void createDialogWithoutDateField() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                datePickerDialog,year,month,day);

        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        //Log.e("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                        if ("mMonthpicker".equals(datePickerField.getName()) || "mMonthSpinner".equals(datePickerField
                                .getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = new Object();
                            dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }

        }
        catch (Exception ex) {
        }


        dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dpd.show();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Sales.SaleFragmentListenner) {
            mListener = (Sales.SaleFragmentListenner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.setModel();
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface SaleFragmentListenner {
        // TODO: Update argument type and name
        void displayModelAndBrand();
        void setModel();
        void getAuthorize(Car car);
    }
}

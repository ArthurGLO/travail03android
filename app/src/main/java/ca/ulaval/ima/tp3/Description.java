package ca.ulaval.ima.tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ca.ulaval.ima.tp3.domain.Car;
import ca.ulaval.ima.tp3.domain.Salor;
import ca.ulaval.ima.tp3.ui.main.fragmentpaquets.DescriptionFragment;

import static java.net.Proxy.Type.HTTP;

public class Description extends AppCompatActivity implements DescriptionFragment.DescriptionFragmentListener {
    private ImageView imageView;
    private TextView textBrand;
    private TextView textModel;
    private TextView textYear;
    private TextView textkilometer;
    private TextView textTransmission;
    private TextView textPrice;
    private TextView textName;
    private TextView textMail;
    private TextView textOwner;
    private TextView textDescription;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
    }

    @Override
    public void modelDescription() {
        Intent intent = getIntent();
        if (intent != null){
        }
            Car car = intent.getParcelableExtra("descriptionData");
            Salor salor = intent.getParcelableExtra("salorData");
            if (car != null && salor !=null){
                textBrand = findViewById(R.id.t1);
                textModel = findViewById(R.id.t2);
                textYear = findViewById(R.id.t3);
                textkilometer = findViewById(R.id.t4);
                textTransmission = findViewById(R.id.t5);
                textPrice = findViewById(R.id.t6);
                textName = findViewById(R.id.t7);
                textMail = findViewById(R.id.t8);
                textOwner = findViewById(R.id.t9);
                textDescription = findViewById(R.id.car_desc);
                imageView = findViewById(R.id.img);
                button = findViewById(R.id.button);

                Picasso.with(this).load(car.getImg()).resize(380,230).into(imageView);
                textBrand.setText(car.getBrandName());
                textModel.setText(car.getModelName());
                textYear.setText(String.valueOf(car.getYear()));
                textkilometer.setText(String.valueOf(car.getKilometers()));
                textTransmission.setText(car.getTransmission());
                textPrice.setText(String.valueOf(car.getPrice()));
                textName.setText(salor.getSalorFirstName()+" "+salor.getSalorLastName());
                textMail.setText(salor.getSalorMail());
                if(car.getSale() == true){
                    textOwner.setText("Oui");
                }else {
                    textOwner.setText("Non");
                }
                textDescription.setText(car.getDescription());

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendMail();
                    }
                });
            }
    }

    private void sendMail(){
        textMail = findViewById(R.id.t8);
        String mail = textMail.getText().toString();
        String[] recipients = mail.split(",");
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , recipients);
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT   , "body of email");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }
}

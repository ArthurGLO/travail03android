package ca.ulaval.ima.tp3.ui.main.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ca.ulaval.ima.tp3.R;

public class CustomListview extends ArrayAdapter<String> {
    private  ArrayList<String> offerTitle ;
    private ArrayList<String> offerYear ;
    private ArrayList<String> offerKilometer ;
    private ArrayList<String> offerPrice ;
    private ArrayList<String> offerImg;
    private Activity context;

    public CustomListview(Activity context, ArrayList<String> offerTitle,
                          ArrayList<String> offerYear,
                          ArrayList<String> offerKilometer,
                          ArrayList<String> offerPrice,
                          ArrayList<String> offerImg) {
        super(context, R.layout.listviewofferbymodel,offerTitle);
        this.context = context;
        this.offerTitle = offerTitle;
        this.offerImg = offerImg;
        this.offerYear = offerYear;
        this.offerKilometer = offerKilometer;
        this.offerPrice = offerPrice;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
        ViewHolder viewHolder = null;

        if(r == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listviewofferbymodel,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) r.getTag();
        }

        viewHolder.name.setText(offerTitle.get(position));
        viewHolder.year.setText(offerYear.get(position));
        viewHolder.kilometer.setText(offerKilometer.get(position)+" "+"km");
        Picasso.with(context).load(offerImg.get(position)).resize(153,130).into(viewHolder.imageView);
        viewHolder.price.setText(offerPrice.get(position)+"$");


        return r;

    }

    class ViewHolder{
        TextView name;
        TextView year;
        TextView kilometer;
        TextView price;
        ImageView imageView;

        ViewHolder(View v){
            name = v.findViewById(R.id.model_name);
            year = v.findViewById(R.id.model_year_text);
             kilometer = v.findViewById(R.id.model_kilometer_text);
             price = v.findViewById(R.id.model_price_text);
             imageView = v.findViewById(R.id.imageView);
        }


    }
}

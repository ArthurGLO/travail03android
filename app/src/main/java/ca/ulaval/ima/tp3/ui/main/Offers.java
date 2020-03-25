package ca.ulaval.ima.tp3.ui.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.util.ArrayList;

import ca.ulaval.ima.tp3.MainActivity;
import ca.ulaval.ima.tp3.R;
import ca.ulaval.ima.tp3.ui.main.fragmentpaquets.ModelFragment;

public class Offers extends Fragment {
    private OffersFragmentListener offersFragmentListener;
    private ListView listView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.offers, container, false);

        offersFragmentListener.displayBrand();

        return root;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Offers.OffersFragmentListener) {
            offersFragmentListener = (Offers.OffersFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        offersFragmentListener = null;
    }


    public interface OffersFragmentListener {
        // TODO: Update argument type and name
        void displayBrand();
    }
}

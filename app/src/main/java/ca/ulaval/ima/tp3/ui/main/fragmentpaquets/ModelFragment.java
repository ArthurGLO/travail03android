package ca.ulaval.ima.tp3.ui.main.fragmentpaquets;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ca.ulaval.ima.tp3.Model;
import ca.ulaval.ima.tp3.R;

public class ModelFragment extends Fragment {

    private ModeleFragmentListener modelFragmentListener;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        modelFragmentListener.displayModel();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_model, container, false);

        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ModelFragment.ModeleFragmentListener) {
            modelFragmentListener = (ModelFragment.ModeleFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        modelFragmentListener = null;
    }


    public interface ModeleFragmentListener {
        // TODO: Update argument type and name
        void displayModel();
    }
}

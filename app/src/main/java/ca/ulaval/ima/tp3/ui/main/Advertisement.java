package ca.ulaval.ima.tp3.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import ca.ulaval.ima.tp3.R;
import ca.ulaval.ima.tp3.ui.main.fragmentpaquets.UserDialog;
import ca.ulaval.ima.tp3.ui.main.fragmentpaquets.UserDialogForAdvertisement;

public class Advertisement extends Fragment {
    private AdvertisementListener mListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener.showDialog();

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.advertisement, container, false);

        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && isResumed()) {
            mListener.showDialog();
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Advertisement.AdvertisementListener) {
            mListener = (Advertisement.AdvertisementListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            //mListener.showDialog();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface AdvertisementListener {
        // TODO: Update argument type and name
        void showDialog();
    }
}

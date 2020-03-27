package ca.ulaval.ima.tp3.ui.main.fragmentpaquets;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import ca.ulaval.ima.tp3.R;

public class UserDialog extends AppCompatDialogFragment {
    private EditText editTextMail;
    private EditText editTextIdul;
    private DialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        editTextMail = view.findViewById(R.id.edit_userMail);
        editTextIdul = view.findViewById(R.id.edit_idul);
        editTextMail.setText("wend-panga-armand-arthur.zagre.1@ulaval.ca");
        editTextIdul.setText("111087150");

        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String userMail = editTextMail.getText().toString();
                        int idul = Integer.parseInt(editTextIdul.getText().toString());
                        listener.getAuthenticate(userMail, idul);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (UserDialog.DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DialogListener");
        }
    }

    public interface DialogListener {
        void getAuthenticate(String userMail, int idul);
    }
}
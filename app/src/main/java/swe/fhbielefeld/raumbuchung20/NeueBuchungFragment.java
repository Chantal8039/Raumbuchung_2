package swe.fhbielefeld.raumbuchung20;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NeueBuchungFragment extends Fragment {


    public NeueBuchungFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_neue_buchung, container, false);
        Button button = view.findViewById(R.id.button_buchen);
        final EditText editText = view.findViewById(R.id.editText_buchen);
        final MainActivity mainActivity = (MainActivity) getActivity();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Dialogue pop-up warning for home list deletion
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Möchten Sie die Buchung hinzufügen?");
                builder1.setCancelable(true);

                // Set Button if user presses "Yes"
                builder1.setPositiveButton(
                        "Ja",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                assert mainActivity != null;
                                mainActivity.addItem(editText.getText().toString());
                                editText.setText("");
                                editText.requestFocus();
                                InputMethodManager mgr = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                assert mgr != null;
                                mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);
                                dialog.cancel();
                            }
                        });

                // Set Button if user presses "No"
                builder1.setNegativeButton(
                        "Nein",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                /*mainActivity.addItem(editText.getText().toString());
                editText.setText("");
                editText.requestFocus();
                InputMethodManager mgr = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(editText.getWindowToken(),0); */
            }
        });

        return view;

    }

}
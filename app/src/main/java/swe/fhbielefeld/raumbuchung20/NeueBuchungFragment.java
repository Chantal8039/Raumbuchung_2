package swe.fhbielefeld.raumbuchung20;


import android.content.Context;
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
                mainActivity.addItem(editText.getText().toString());
                editText.setText("");
                editText.requestFocus();
                InputMethodManager mgr = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);
            }
        });

        return view;

    }

}
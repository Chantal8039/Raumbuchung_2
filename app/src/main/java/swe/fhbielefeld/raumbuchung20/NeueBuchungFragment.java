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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;  // neu von marvins code
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import swe.fhbielefeld.raumbuchung20.modules.Buchung;
import swe.fhbielefeld.raumbuchung20.modules.Client;
import swe.fhbielefeld.raumbuchung20.modules.Raum;
import swe.fhbielefeld.raumbuchung20.modules.Server;

public class NeueBuchungFragment extends Fragment {


    private EditText editText_Raumnummer;
    private Button button_Buchen;
    private TextView textView_Datum;
    private Spinner spinner_Start;
    private Spinner spinner_End;
    private Buchung buchung;
    private MainActivity mainActivity;


    public NeueBuchungFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_neue_buchung, container, false);
        button_Buchen = view.findViewById(R.id.button_buchen);
        editText_Raumnummer = view.findViewById(R.id.editText_buchen);
        textView_Datum = view.findViewById(R.id.textView_datum);
        spinner_Start = view.findViewById(R.id.spinner_start);
        spinner_End = view.findViewById(R.id.spinner_end);
        mainActivity = (MainActivity) getActivity();

        spinner_Start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                // Object item = parentView.getItemAtPosition(position);

                // Depend on first spinner value set adapter to 2nd spinner

            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }
        });

        button_Buchen.setOnClickListener(new View.OnClickListener() {
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
                                // Check if user chosen times are logical.
                                if(spinner_Start.getSelectedItemPosition() < spinner_End.getSelectedItemPosition())
                                {
                                    // Check if user input rooms are available in the list.
                                    if(raumCheck() != null)
                                    {
                                        assert mainActivity != null;
                                        InputMethodManager mgr = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                        assert mgr != null;
                                        mgr.hideSoftInputFromWindow(editText_Raumnummer.getWindowToken(),0);
                                        dialog.cancel();
                                        buchung_erstellen();
                                        Toast.makeText(getContext(),"Raum ist gebucht!",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                        builder2.setMessage("Bitte einen gültigen Raum eingeben.");
                                        builder2.setCancelable(true);

                                        builder2.setNeutralButton(
                                                "Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                }
                                        );

                                        AlertDialog alertZeiten = builder2.create();
                                        alertZeiten.show();
                                    }

                                }
                                else
                                {
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                    builder2.setMessage("Bitte eine gültige Zeit auswählen.");
                                    builder2.setCancelable(true);

                                    builder2.setNeutralButton(
                                            "Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            }
                                    );

                                    AlertDialog alertZeiten = builder2.create();
                                    alertZeiten.show();
                                }

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
                InputMethodManager mgr = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                assert mgr != null;
                mgr.hideSoftInputFromWindow(editText_Raumnummer.getWindowToken(),0);
            }
        });

        return view;

    }
    private void buchung_erstellen(){
        String datum = textView_Datum.getText().toString();
        String start = spinner_Start.getSelectedItem().toString();
        String end = spinner_End.getSelectedItem().toString();
        String raumnummer = editText_Raumnummer.getText().toString();
        start = datum + " " + start;
        end = datum + " " + end;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime startZeit = LocalDateTime.parse(start,formatter);
        LocalDateTime endZeit = LocalDateTime.parse(end,formatter);
        Raum r = new Raum(raumnummer);
        Buchung buchung = new Buchung(r, startZeit, endZeit, Client.angemeldeterUser);

        Server.getInstance().addBuchung(buchung);
        editText_Raumnummer.setText("");
        editText_Raumnummer.requestFocus();
    }

    private Raum raumCheck(){
        Raum r = Server.getInstance().findRaum(editText_Raumnummer.getText().toString());
        if(r != null){
            if(r.getRaumnummer().equals(editText_Raumnummer.getText().toString())){
                return r;
            }
        }
        else
        {
            return null;
        }
        return null;
    }

}
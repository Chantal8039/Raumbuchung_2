package swe.fhbielefeld.raumbuchung20;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

    private static final String TAG = "NeueBuchungFragment";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    public NeueBuchungFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_neue_buchung, container, false);
        mDisplayDate = (TextView) view.findViewById(R.id.textView_datum);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                if (date.charAt(1) == '/') date = "0" + date;
                if (date.charAt(4) == '/') date = date.substring(0,3) + "0" + date.substring(3);
                mDisplayDate.setText(date);
            }
        };

        // Inflate the layout for this fragment
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

        // Actions if the booking button is pressed
        button_Buchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Check if user input times are logical. start < end
                if(spinner_Start.getSelectedItemPosition() < spinner_End.getSelectedItemPosition())
                {
                    // Check if user input rooms are available in the list.
                    if(raumCheck() != null) {
                        buchung_erstellen();
                        // Dialogue pop-up warning for home list deletion
                        /*AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setMessage("Möchten Sie die Buchung hinzufügen?");
                        builder1.setCancelable(true);

                        // Set Button if user presses "Yes"
                        builder1.setPositiveButton(
                                "Ja",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        assert mainActivity != null;
                                        InputMethodManager mgr = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                        assert mgr != null;
                                        mgr.hideSoftInputFromWindow(editText_Raumnummer.getWindowToken(), 0);
                                        dialog.cancel();
                                        buchung_erstellen();

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

                         */
                    }
                    else
                    {
                        // If user input wrong room number (doesn't exist)
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
                    // If user input wrong time (start >= end)
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
        return view;
    }


    private void buchung_erstellen(){
        String datum = textView_Datum.getText().toString();
        String start = spinner_Start.getSelectedItem().toString();
        String end = spinner_End.getSelectedItem().toString();
        String raumnummer = editText_Raumnummer.getText().toString();
        start = datum + " " + start;
        end = datum + " " + end;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime startZeit = LocalDateTime.parse(start,formatter);
        LocalDateTime endZeit = LocalDateTime.parse(end,formatter);
        Raum r = new Raum(raumnummer);
        Buchung buchung = new Buchung(r, startZeit, endZeit, Client.angemeldeterUser);
        if(!Server.getInstance().hatBuchungUeberschneidung(buchung)){
            Server.getInstance().addBuchung(buchung);
            Toast.makeText(getContext(), "Raum ist gebucht!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Raum ist in gewähltem Zeitraum bereits belegt!",Toast.LENGTH_SHORT).show();
        }
        //editText_Raumnummer.setText("");
        editText_Raumnummer.requestFocus();
    }

    private Raum raumCheck(){
        Raum r = Server.getInstance().findRaum(editText_Raumnummer.getText().toString());
        if(r != null){
            if(r.getRaumnummer().equals(editText_Raumnummer.getText().toString())){
                return r;
            }
        }
        return null;
    }
}
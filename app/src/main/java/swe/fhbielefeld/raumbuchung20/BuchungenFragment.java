package swe.fhbielefeld.raumbuchung20;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

import swe.fhbielefeld.raumbuchung20.modules.Buchung;
import swe.fhbielefeld.raumbuchung20.modules.Server;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuchungenFragment extends Fragment {

    private EditText edtRaumsuchen;
    private EditText edtDatumsuchen;
    private Button btnSuchen;
    private ListView lvListe;
    private ArrayAdapter<Buchung> arrayAdapter;
    private ArrayList<Buchung> buchungsListe;
    public BuchungenFragment() {
        // Required empty public constructor
    }
    private void initViews(View view){
        edtRaumsuchen = view.findViewById(R.id.editText_raumsuchen);
        edtDatumsuchen = view.findViewById(R.id.editText_datumsuchen);
        btnSuchen = view.findViewById(R.id.button_suchen);
        btnSuchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                filterBuchung();
            }
        });
        lvListe = view.findViewById(R.id.listView_home);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buchungen, container, false);
        initViews(v);

        return v;
    }
    private void filterBuchung(){
        String raumnummer = edtRaumsuchen.getText().toString();
        String datum = edtDatumsuchen.getText().toString();
        boolean filterRaum = false;
        boolean filterDatum = false;

        if(!raumnummer.equals("")){
            filterRaum = true;
        }
        if(!datum.equals("")){
            filterDatum = true;
        }
        if(!filterRaum && !filterDatum){
            Toast.makeText(getActivity(),"Bitte mindestens eine Angabe treffen!", Toast.LENGTH_SHORT).show();
            buchungsListe.clear();
            arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, buchungsListe);
            lvListe.setAdapter(arrayAdapter);
            return;
        }

        if (filterRaum){
            buchungsListe = Server.getInstance().findBuchungenByRaum(raumnummer);
        }
        if (filterDatum){
            //TODO Prüfen ob Datum im Richtigen Format eingegeben wurde sonst Exception
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("dd.MM.yyyy[ HH:mm:ss]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();
            LocalDateTime datumFilter = LocalDateTime.parse(datum,formatter);
            buchungsListe = Server.getInstance().findBuchungenByDatum(datumFilter);
        }
        if (buchungsListe.isEmpty()){Toast.makeText(getActivity(),"Keine Buchungen gefunden.", Toast.LENGTH_SHORT);}
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, buchungsListe);
        lvListe.setAdapter(arrayAdapter);

    }
}

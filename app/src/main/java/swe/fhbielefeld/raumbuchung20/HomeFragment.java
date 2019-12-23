package swe.fhbielefeld.raumbuchung20;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import swe.fhbielefeld.raumbuchung20.modules.Buchung;
import swe.fhbielefeld.raumbuchung20.modules.Server;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final ListView listView = view.findViewById(R.id.listView_home);
        final MainActivity mainActivity = (MainActivity) getActivity();
        final ArrayAdapter<Buchung> arrayAdapter = new ArrayAdapter<>
                (mainActivity, android.R.layout.simple_list_item_1, Server.getInstance().getBuchungen());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                // Dialogue pop-up warning for home list deletion
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Möchten Sie die Buchung löschen?");
                builder1.setCancelable(true);

                // Set Button if user presses "Yes"
                builder1.setPositiveButton(
                        "Ja",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Server.getInstance().deleteBuchung(position);
                                arrayAdapter.notifyDataSetChanged();
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
            }
        });
        return view;
    }
}

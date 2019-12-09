package swe.fhbielefeld.raumbuchung20;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


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
        ListView listView = view.findViewById(R.id.listView_home);
        final MainActivity mainActivity = (MainActivity) getActivity();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (mainActivity, android.R.layout.simple_list_item_1, mainActivity.arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mainActivity.deleteItem(position);
                arrayAdapter.notifyDataSetChanged();

            }
        });
        return view;
    }

}

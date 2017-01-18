package in.ac.iitp.anwesha;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cc15 on 19/12/16.
 */

public class ArtsEvents extends Fragment {


    public ArtsEvents newInstance( List<EventData> artsEvents) {
        ArtsEvents f = new ArtsEvents();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("artsevents", (Serializable) artsEvents);

        f.setArguments(args);
        return f;
    }

    public ArtsEvents() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventslist_list, container, false);
        Bundle bundle = getArguments();
        List <EventData> ITEMS = (List<EventData>) bundle.getSerializable("artsevents");

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyEventsListRecyclerViewAdapter(ITEMS, getContext()));
            return view;
        }
        return view;
    }

}

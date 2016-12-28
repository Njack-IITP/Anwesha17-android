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
import java.util.ArrayList;
import java.util.List;

public class EventsListFragment extends Fragment {

    WebSyncDB db;

     public EventsListFragment() {
    }

    public List<List<EventData>> GroupITEMS = new ArrayList<List<EventData>>(5);

    private String EventType[] = {"Cultural","Technical","Literary","Management","Eco"};
    int i;

    public static EventsListFragment newInstance(int id) {
        Bundle args = new Bundle();
        //ITEMS = new ArrayList<>();
        args.putInt("message", id);
        EventsListFragment fragment = new EventsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new WebSyncDB(getContext());
        //ITEMS.clear();
        if (getArguments() != null) {
        i = this.getArguments().getInt("message");
        }

        List<EventData> ITEMS = new ArrayList<>();
        if(!EventType[i].equals("Technical")) {
            Cursor cursor = db.getParticularEvents(EventType[i].replaceAll(" ", ""));
            int c = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ITEMS.add(new EventData(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6)));
                cursor.moveToNext();
                c++;
                if (c > 100) break;

            }
            GroupITEMS.add(ITEMS);
        }

        //Toast.makeText(getContext(),eventType,Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventslist_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            if(i!=1)
            recyclerView.setAdapter(new MyEventsListRecyclerViewAdapter(GroupITEMS.get(i),getContext()));
        }
        return view;
    }
}

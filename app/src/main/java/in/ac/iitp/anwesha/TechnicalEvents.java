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
import java.util.HashMap;
import java.util.List;

/**
 * Created by cc15 on 19/12/16.
 */

public class TechnicalEvents extends Fragment {

    public TechnicalEvents() {
    }
    public List<EventData> TechnicalEvents = new ArrayList<EventData>();

    public java.util.Map <Integer, EventData> m1  = new HashMap<>();
    WebSyncDB db;
    int maxId = 0;

    private void getAllEvents(){
        TechnicalEvents.clear();
        db = new WebSyncDB(getContext());
        Cursor cursor = db.getAllEvents();
        int c = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            m1.put(cursor.getInt(0),new EventData(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12)));
            maxId = Math.max(maxId,cursor.getInt(0));
            cursor.moveToNext();
            c++;
            if (c > 200) break;
        }

        for(int i=4;i<=maxId;i++){
            if(m1.containsKey(i)) {
                if (m1.get(m1.get(i).code).code == 0) {
                    TechnicalEvents.add(m1.get(i));
                }
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventslist_list, container, false);

        getAllEvents();

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyEventsListRecyclerViewAdapter(TechnicalEvents, getContext()));
            return view;
        }
        return view;
    }

}

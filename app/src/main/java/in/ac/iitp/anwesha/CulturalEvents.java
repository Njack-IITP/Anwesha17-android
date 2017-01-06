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

/**
 * Created by cc15 on 19/12/16.
 */

public class CulturalEvents extends Fragment {
    public List<EventData> ITEMS = new ArrayList<EventData>();
    WebSyncDB db;

    public CulturalEvents() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new WebSyncDB(getContext());
        Cursor cursor = db.getParticularEvents("Cultural".replaceAll(" ", ""));
        int c = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ITEMS.add(new EventData(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6)));
            cursor.moveToNext();
            c++;
            if (c > 100) break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventslist_list, container, false);

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

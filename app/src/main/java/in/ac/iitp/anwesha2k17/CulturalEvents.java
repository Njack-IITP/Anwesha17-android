package in.ac.iitp.anwesha2k17;

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
import java.util.HashMap;
import java.util.List;

/**
 * Created by cc15 on 19/12/16.
 */

public class CulturalEvents extends Fragment {

    public CulturalEvents() {
    }

    public List<EventData> AllEvents = new ArrayList<EventData>();
    public List<EventData> CulturalEvents = new ArrayList<EventData>();

    public java.util.Map< Integer ,List<EventData>> m1 = new HashMap<>();

    WebSyncDB db;
    int id;

    public static CulturalEvents newInstance(int eveid) {
        CulturalEvents culturalEvents = new CulturalEvents();
        Bundle args = new Bundle();
        args.putInt("id", eveid);
        culturalEvents.setArguments(args);
        return culturalEvents;
    }

    private void getAllEvents(){
        CulturalEvents.clear();
        db = new WebSyncDB(getContext());
        Cursor cursor = db.getAllEvents();
        int c = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AllEvents.add(new EventData(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12)));
            cursor.moveToNext();
            c++;
            if (c > 200) break;
        }

        for(int i=0;i<AllEvents.size();i++){
            if(AllEvents.get(i).code == id){
                int temp = AllEvents.get(i).id;
                int x=0;
                for(int j=0;j<AllEvents.size();j++){
                    if(temp == AllEvents.get(j).code){
                        CulturalEvents.add(AllEvents.get(j));
                        x++;
                    }
                }
                if(x==0){
                    CulturalEvents.add(AllEvents.get(i));
                }
            }
        }

        m1.put(id,CulturalEvents);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("id", 0);
        getAllEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventslist_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyEventsListRecyclerViewAdapter(m1.get(id), getContext()));
            return view;
        }
        return view;
    }

}

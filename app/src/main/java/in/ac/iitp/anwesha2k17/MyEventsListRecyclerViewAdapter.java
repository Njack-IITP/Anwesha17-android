package in.ac.iitp.anwesha2k17;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyEventsListRecyclerViewAdapter extends RecyclerView.Adapter<MyEventsListRecyclerViewAdapter.ViewHolder> {

    private final List<EventData> mValues;
    private Context context;

    public MyEventsListRecyclerViewAdapter(List<EventData> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_event_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.tv.setText(mValues.get(position).name);
        if(mValues.get(position).tagline.equals(" ") || mValues.get(position).tagline == null || mValues.get(position).tagline.equals("null") || mValues.get(position).tagline.equals(""))
            holder.tvdesc.setText(mValues.get(position).short_desc);
        else
            holder.tvdesc.setText(mValues.get(position).tagline);

        Log.v("chirag",holder.tvdesc.getText().toString());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, EventDetails.class);
                in.putExtra("eventID", mValues.get(position).id);
                in.putExtra("eventName", mValues.get(position).name);
                in.putExtra("eventDesc", mValues.get(position).long_desc);
                in.putExtra("eventCode", mValues.get(position).code);
                in.putExtra("eventDay", mValues.get(position).day);
                in.putExtra("eventSize", mValues.get(position).size);
                in.putExtra("eventFee", mValues.get(position).fee);
                in.putExtra("date", mValues.get(position).date);
                in.putExtra("time", mValues.get(position).time);
                in.putExtra("venue", mValues.get(position).venue);
                in.putExtra("short_desc", mValues.get(position).short_desc);
                in.putExtra("organisers", mValues.get(position).organisers);
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View v;
        public final TextView tv;
        public final TextView tvdesc;
        public EventData mItem;

        public ViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_event_list_element);
            tv.setTypeface(AllIDS.font_AnweshaSub);
            tvdesc = (TextView) view.findViewById(R.id.tv_event_list_element_desc);
            tvdesc.setTypeface(AllIDS.font_Sub1);
            v = view.findViewById(R.id.fab);
        }
    }
}

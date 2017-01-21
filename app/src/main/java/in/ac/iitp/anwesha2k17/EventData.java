package in.ac.iitp.anwesha2k17;

/**
 * Created by cc15 on 14/12/16.
 */

public class EventData {
    int fee, day, size, id, code;
    String name, tagline, date, time, venue, organisers, short_desc, long_desc;
    String toDisplay_short, toDisplay_long;

    EventData(int id, String name, int fee, int day, int size, int code, String tagline, String date, String time, String venue, String organisers, String short_desc, String long_desc ) {
        this.id = id;
        this.name = name;
        this.short_desc = short_desc;
        this.fee = fee;
        this.day = day;
        this.code = code;
        this.size = size;
        this.tagline = tagline;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.organisers = organisers;
        this.long_desc = long_desc;
        toDisplay_short = EventDetails.filterLongDesc((short_desc));
        toDisplay_long = EventDetails.filterLongDesc((long_desc));
    }
}


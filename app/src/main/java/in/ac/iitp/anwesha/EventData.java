package in.ac.iitp.anwesha;

/**
 * Created by cc15 on 14/12/16.
 */

public class EventData {
    int fee, day, size, id;
    String name, code, desc;
    String toDisplay;

    EventData(int id, String name, int fee, int day, int size, String code, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.fee = fee;
        this.day = day;
        this.code = code;
        this.size = size;
        toDisplay = EventDetails.filterLongDesc((desc));
    }
}


package watchlist.model;

import java.io.Serializable;
import java.util.List;

public class Watchlist implements Serializable {

    private List<String> contentIds;

    public Watchlist() {}

    public Watchlist(List<String> contentIds) {
        this.contentIds = contentIds;
    }

    public List<String> getContentIds() {
        return contentIds;
    }

}

package watchlist.repository;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import watchlist.model.Watchlist;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class WatchlistRepositoryImpl implements WatchlistRepository {


    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public WatchlistRepositoryImpl(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }


    @Override
    public void add(String userId, Watchlist watchlist) {

        IMap<String, Watchlist> watchlistIMap = hazelcastInstance.getMap("watchlist");

        Watchlist list = watchlistIMap.get(userId);

        if(list != null) {
            for(String item: watchlist.getContentIds()){
                if(!list.getContentIds().contains(item)){
                    list.getContentIds().add(item);
                }
            }
            watchlistIMap.put(userId, list);
        } else {
            watchlistIMap.put(userId, watchlist);
        }

    }

    @Override
    public Watchlist get(String userId) {
        IMap<String, Watchlist> watchlistIMap = hazelcastInstance.getMap("watchlist");
        return watchlistIMap.get(userId);
    }

    @Override
    public void delete(String userId, Watchlist toDeleteWatchlist) {

        IMap<String, Watchlist> watchlistIMap = hazelcastInstance.getMap("watchlist");

        Watchlist watchlist = watchlistIMap.get(userId);

        List<String> updatedList = watchlist.getContentIds().stream().filter(i -> !toDeleteWatchlist.getContentIds().contains(i)).collect(Collectors.toList());


        watchlistIMap.put(userId, new Watchlist(updatedList));

    }
}

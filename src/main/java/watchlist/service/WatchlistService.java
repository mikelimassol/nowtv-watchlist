package watchlist.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import watchlist.model.Watchlist;
import watchlist.repository.WatchlistRepository;


@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    public void add(String userId, Watchlist contentIDs) {

        watchlistRepository.add(userId, contentIDs);

    }


    public Watchlist get(String userId) {

        return watchlistRepository.get(userId);

    }


    public void delete(String userId, Watchlist toDeleteWatchlist) {

        watchlistRepository.delete(userId, toDeleteWatchlist);

    }
}

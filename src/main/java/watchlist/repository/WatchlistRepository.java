package watchlist.repository;


import watchlist.model.Watchlist;


public interface WatchlistRepository {

    void add(String userId, Watchlist contentIDs);

    Watchlist get(String userId);

    void delete(String userId, Watchlist toDeleteWatchlist);

}

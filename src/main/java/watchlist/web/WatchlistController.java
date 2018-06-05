package watchlist.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import watchlist.model.Watchlist;
import watchlist.service.WatchlistService;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @RequestMapping("/add/{userId}")
    public ResponseEntity addWatchlist(@PathVariable String userId, @RequestBody Watchlist watchlist) {

        watchlistService.add(userId, watchlist);

        return ResponseEntity.ok(200);

    }


    @RequestMapping("/get/{userId}")
    public ResponseEntity<Watchlist> getWatchlist(@PathVariable String userId) {

        Watchlist watchlist = watchlistService.get(userId);

        return ResponseEntity.ok().body(watchlist);

    }

    @RequestMapping("/delete/{userId}")
    public ResponseEntity deleteFromWatchlist(@PathVariable String userId, @RequestBody Watchlist watchlist) {

        watchlistService.delete(userId, watchlist);

        return ResponseEntity.ok(200);

    }

}

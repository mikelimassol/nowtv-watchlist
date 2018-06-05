package watchlist.web;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import watchlist.model.Watchlist;
import watchlist.service.WatchlistService;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WatchlistControllerTest {

    private static final String USER_ID = "123";
    private static final String[] CONTENT_IDS = {"zRE49", "wYqiZ", "15nW5", "srT5k", "FBSxr"};
    private static final String[] TO_DELETE_CONTENT_IDS = {"zRE49", "wYqiZ"};

    @InjectMocks
    WatchlistController watchlistController = new WatchlistController();

    @Mock
    WatchlistService watchlistService;

    private Watchlist watchlist;

    private Watchlist toDeleteWatchlist;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        watchlist = new Watchlist(Arrays.asList(CONTENT_IDS));

        toDeleteWatchlist = new Watchlist(Arrays.asList(TO_DELETE_CONTENT_IDS));


    }


    @Test
    public void addWatchlist_ShouldAddWatchlist(){

        watchlistController.addWatchlist(USER_ID, watchlist);

        verify(watchlistService, times(1)).add(USER_ID,  watchlist);

    }

    @Test
    public void deleteFromWatchlist_ShouldDeleteWatchlist(){

        watchlistController.deleteFromWatchlist(USER_ID, toDeleteWatchlist);

        verify(watchlistService, times(1)).delete(USER_ID,  toDeleteWatchlist);

    }


    @Test
    public void getWatchlist_ShouldReiceiveWatchlist(){

        when(watchlistService.get(USER_ID)).thenReturn(watchlist);

        ResponseEntity<Watchlist> watchlistResponse = watchlistController.getWatchlist(USER_ID);

        assertThat(watchlistResponse.getBody().getContentIds(), is(equalTo(this.watchlist.getContentIds())));

        verify(watchlistService, times(1)).get(USER_ID);

    }


}

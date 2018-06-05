package watchlist.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import watchlist.model.Watchlist;
import watchlist.repository.WatchlistRepository;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;


@RunWith(MockitoJUnitRunner.class)
public class WatchlistServiceTest {

    private static final String USER_ID = "123";
    private static final String[] CONTENT_IDS = {"zRE49", "wYqiZ", "15nW5", "srT5k", "FBSxr"};
    private static final String[] TO_DELETE_CONTENT_IDS = {"zRE49", "wYqiZ"};


    @InjectMocks
    WatchlistService watchlistService = new WatchlistService();

    @Mock
    WatchlistRepository watchlistRepository;

    private Watchlist watchlist;

    private Watchlist toDeleteWatchlist;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        watchlist = new Watchlist(Arrays.asList(CONTENT_IDS));

        toDeleteWatchlist = new Watchlist(Arrays.asList(TO_DELETE_CONTENT_IDS));
    }

    @Test
    public void add_ShouldAddWatchlist(){

        watchlistService.add(USER_ID, watchlist);

        verify(watchlistRepository, times(1)).add(USER_ID,  watchlist);

    }

    @Test
    public void delete_ShouldDeleteWatchlist(){

        watchlistService.delete(USER_ID, toDeleteWatchlist);

        verify(watchlistRepository, times(1)).delete(USER_ID,  toDeleteWatchlist);

    }


    @Test
    public void get_ShouldReiceiveWatchlist(){

        when(watchlistRepository.get(USER_ID)).thenReturn(watchlist);

        Watchlist watchlist = watchlistService.get(USER_ID);

        assertThat(watchlist.getContentIds(), is(equalTo(watchlist.getContentIds())));

        verify(watchlistRepository, times(1)).get(USER_ID);

    }


}
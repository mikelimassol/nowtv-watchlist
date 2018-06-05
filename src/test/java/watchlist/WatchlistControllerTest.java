package watchlist;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import watchlist.model.Watchlist;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Runs tests in order based on their name.
public class WatchlistControllerTest {

    private static final String[] USER_123_WATCHLIST = {"zRE49", "wYqiZ", "15nW5", "srT5k", "FBSxr"};
    private static final String[] USER_WATCHLIST_ID_TO_DELETE = {"15nW5"};
    private static final String[] USER_123_WATCHLIST_UPDATED_LIST = {"zRE49", "wYqiZ", "srT5k", "FBSxr"};

    private static final String[] USER_ABC_WATCHLIST = {"hWjNK", "U8jVg", "GH4pD", "rGIha"};
    private static final String[] USER_WATCHLIST_ID_TO_ADD = {"U8jVg", "15nW5"};
    private static final String[] USER_ABC_WATCHLIST__UPDATED_LIST = {"hWjNK", "U8jVg", "GH4pD", "rGIha", "15nW5"};

    @Autowired
    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Watchlist user123WatchList;
    private Watchlist user123WatchListIdsToRemove;
    private Watchlist user123WatchUpdatedList;


    private Watchlist userABCWatchList;
    private Watchlist userABCWatchListIDToADD;
    private Watchlist userABCUpdatedList;


    @Before
    public void setUp(){

        user123WatchList = new Watchlist(Arrays.asList(USER_123_WATCHLIST));
        user123WatchListIdsToRemove = new  Watchlist(Arrays.asList(USER_WATCHLIST_ID_TO_DELETE));
        user123WatchUpdatedList = new Watchlist(Arrays.asList(USER_123_WATCHLIST_UPDATED_LIST));

        userABCWatchList = new Watchlist(Arrays.asList(USER_ABC_WATCHLIST));
        userABCWatchListIDToADD = new Watchlist(Arrays.asList(USER_WATCHLIST_ID_TO_ADD));
        userABCUpdatedList = new Watchlist(Arrays.asList(USER_ABC_WATCHLIST__UPDATED_LIST));


    }


    @Test
    public void test1_shouldAddWatchlistForUser123() throws Exception {
        mockMvc.perform(post("/watchlist/add/123")
                 .content(this.json(user123WatchList))
                 .contentType(contentType)).andDo(print()).andExpect(status().isOk());
    }


    @Test
    public void test2_shouldRetrieveWatchlistForUser123() throws Exception {

        mockMvc.perform(get("/watchlist/get/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contentIds", hasSize(5)))
                .andExpect(jsonPath("$.contentIds", is(equalTo(user123WatchList.getContentIds()))));

    }

    @Test
    public void test3_shouldDeleteItemsForUser123() throws Exception {

        mockMvc.perform(post("/watchlist/delete/123")
                .content(this.json(user123WatchListIdsToRemove))
                .contentType(contentType)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void test4_shouldRetrieveUpdatedlistForUser123() throws Exception {

        mockMvc.perform(get("/watchlist/get/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contentIds", hasSize(4)))
                .andExpect(jsonPath("$.contentIds", is(equalTo(user123WatchUpdatedList.getContentIds()))));

    }



    @Test
    public void test5_shouldAddWatchlistForUserABC() throws Exception {

        //Add Items for ABC user.
        mockMvc.perform(post("/watchlist/add/ABC")
                .content(this.json(userABCWatchList))
                .contentType(contentType)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void test6_shouldRetrieveWatchlistForUserABC() throws Exception {

        mockMvc.perform(get("/watchlist/get/ABC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contentIds", hasSize(4)))
                .andExpect(jsonPath("$.contentIds", is(equalTo(userABCWatchList.getContentIds()))));


    }

    @Test
    public void test7_shouldAddMoreItemsForUserABC() throws Exception {

        //Add more items to ABC user
        mockMvc.perform(post("/watchlist/add/ABC")
                .content(this.json(userABCWatchListIDToADD))
                .contentType(contentType)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void test8_shouldGetUpdatedlistForUserABC() throws Exception {

        mockMvc.perform(get("/watchlist/get/ABC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contentIds", hasSize(5)))
                .andExpect(jsonPath("$.contentIds", is(equalTo(userABCUpdatedList.getContentIds()))));

    }



    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }



}
package watchlist.config;


import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    private static final String HAZELCAST_INSTANCE = "hazelcast-instance";
    private static final String WATCHLIST = "watchlist";

    @Bean
    public Config hazelCastConfig(){
        return new Config()
                .setInstanceName(HAZELCAST_INSTANCE)
                .addMapConfig(
                        new MapConfig()
                                .setName(WATCHLIST)
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(20));
    }



}

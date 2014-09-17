package hello.security;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class ApiPreAuthenticatedTokenCacheService {

	@Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("tokens")));
        return cacheManager;
    }
	
	public void putInCache(String xAuthToken, User user) {
		cacheManager().getCache("tokens").put(xAuthToken, user);
	}
	
	public User getFromCache(String xAuthToken) {
		return cacheManager().getCache("tokens").get(xAuthToken, User.class);
	}
	
}

package com.example.personinfo;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;

/**
* This is the main configuration class to configure the EHCache manager
* to leverage custom cache functionalities in our application.
*
* @author  Asim shahzad
* @since   2020-08-15 
*/
@Configuration
@EnableCaching
public class EhCacheConfig {

    /**
     * This bean is used to inject the dependency for EhCache manger factory along with its configuration.
     * 
     * @return EhCacheManagerFactoryBean
     */
    @Bean
    public EhCacheManagerFactoryBean cacheManager() {
        return new EhCacheManagerFactoryBean();
    }

    /**
     * This bean is used to inject the dependency for EhCache manger along with its configuration,
     * We configured two custom caches for our application to store entries and aslo they use custom
     * configurations for better consistency and performance.
     * 
     * @return EhCacheCacheManager
     */
    @Bean
    public EhCacheCacheManager ehCacheManager() {
        CacheConfiguration personCacheByIdConfig = new CacheConfiguration()
            .eternal(false)
            .timeToIdleSeconds(10000)
            .timeToLiveSeconds(5000)
            .maxEntriesLocalHeap(10000)
            .memoryStoreEvictionPolicy("LRU")
            .name("getPersonByIdCache");

        CacheConfiguration allPersonsCacheByIdConfig = new CacheConfiguration()
                .eternal(false)
                .timeToIdleSeconds(10000)
                .timeToLiveSeconds(5000)
                .maxEntriesLocalHeap(10000)
                .memoryStoreEvictionPolicy("LRU")
                .name("getAllPersonsCache");

        Cache personCacheByID = new Cache(personCacheByIdConfig);
        Cache allPersonsCache = new Cache(allPersonsCacheByIdConfig);

        cacheManager().getObject().addCache(personCacheByID);
        cacheManager().getObject().addCache(allPersonsCache);
        return new EhCacheCacheManager(cacheManager().getObject());
    }
}

package com.thinkstep.test.onlineusers.config;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class EhCacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<? extends Object, ? extends Object> event) {
        log.info("cache changed: ", event.getKey(), event.getOldValue(), event.getNewValue());
    }
}

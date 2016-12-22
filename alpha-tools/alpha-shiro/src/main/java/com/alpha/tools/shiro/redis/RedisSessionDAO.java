package com.alpha.tools.shiro.redis;

/**
 * Created by cw on 15-11-19.
 */
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("redisSessionDAO")
@Order(1)
public class RedisSessionDAO extends AbstractSessionDAO {
    @Autowired
    private RedisTemplate<String,Session> redisTemplate;


    @Value("#{shiro.session.timeout}")
    public Long timeout;

    private TimeUnit timeUnit = TimeUnit.MINUTES;
    
    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "shiro_redis_session:";


    public RedisSessionDAO(){
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    /**
     * save session
     *
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            return;
        }
        this.redisTemplate.opsForValue().set(getKey(session), session, timeout, timeUnit);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            return;
        }
        this.redisTemplate.delete(getKey(session));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();

        Set<String> keys = this.redisTemplate.keys(this.keyPrefix + "*");
        if (keys != null && keys.size() > 0) {
            sessions.addAll(keys.stream().map(key -> this.redisTemplate.opsForValue().get(key)).collect(Collectors.toList()));
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            log.error("session id is null");
            return null;
        }
        return this.redisTemplate.opsForValue().get(getKey(sessionId));
    }

    private String getKey(Session session){
        return getKey(session.getId());
    }

    private String getKey(Serializable sessionId){
        return String.format("%s%s",getKeyPrefix(),sessionId.toString());
    }

    /**
     * Returns the Redis session keys
     * prefix.
     *
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key
     * prefix.
     *
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}

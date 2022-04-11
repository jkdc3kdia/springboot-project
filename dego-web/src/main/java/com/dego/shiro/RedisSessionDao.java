package com.dego.shiro;

import com.dego.constants.SystemConstant;
import groovy.util.logging.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.*;

@Slf4j
public class RedisSessionDao extends AbstractSessionDAO {

    private RedisTemplate redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.storeSession(sessionId, session);
        System.out.println("sessionId为：" + sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        if (serializable != null) {
            return (Session) redisTemplate.opsForHash().get(SystemConstant.REDIS_SESSION_KEY, serializable.toString());
        }
        return null;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session != null && session.getId() != null) {
            this.storeSession(session.getId(), session);
        }
    }

    @Override
    public void delete(Session session) {
        if (session != null && session.getId() != null) {
            redisTemplate.opsForHash().delete(SystemConstant.REDIS_SESSION_KEY, session.getId().toString());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Map<String, Object> maps = redisTemplate.opsForHash().entries(SystemConstant.REDIS_SESSION_KEY);
        List<Session> sessionList = new ArrayList<>();
        for (Iterator<Map.Entry<String, Object>> it = maps.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> entry = it.next();
            Session session = (Session) entry.getValue();
            sessionList.add(session);
        }
        return Collections.unmodifiableCollection(sessionList);
    }

    /**
     * 存储session
     *
     * @param id
     * @param session
     * @return
     */
    protected Session storeSession(Serializable id, Session session) {
        if (id == null) {
            throw new NullPointerException("id argument cannot be null.");
        } else {
            try {
                redisTemplate.opsForHash().put(SystemConstant.REDIS_SESSION_KEY, id.toString(), session);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return session;
        }
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}

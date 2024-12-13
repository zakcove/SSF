package vttp.batch5.ssf.noticeboard.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // SET notice:<id> '{"id":"abc123", "timestamp":111111111}'
    public void insertNotices(String id, String payload) {
        redisTemplate.opsForValue().set("notice:" + id, payload);
    }
}

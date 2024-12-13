package vttp.batch5.ssf.noticeboard.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // SET notice:<id> '{"id":"abc1234", "timestamp":1111111111111}'
    public void insertNotices(String id, String payload) {
        redisTemplate.opsForValue().set("notice:" + id, payload);
    }
	
    // RANDOMKEY
    public boolean isHealthy() {
        try {
            redisTemplate.randomKey();
            return true; 
        } catch (Exception e) {
            return false; 
        }
    } 
}

package com.zoyo.web.util;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: mxx
 * @Description: redis 工具类
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    public Long del(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    public Boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Long decr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public Boolean hSet(String key, String hashKey, Object value, long time) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return expire(key, time);
    }

    public void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Boolean hSetAll(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        return expire(key, time);
    }

    public void hSetAll(String key, Map<String, ?> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public void hDel(String key, Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public Boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public Long hIncr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    public Long hDecr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    public Long sAdd(String key, long time, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        expire(key, time);
        return count;
    }

    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    public Long lPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public Long lPush(String key, Object value, long time) {
        Long index = redisTemplate.opsForList().rightPush(key, value);
        expire(key, time);
        return index;
    }

    public Long lPushAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    public Long lPushAll(String key, Long time, Object... values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        expire(key, time);
        return count;
    }

    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 有序集合添加
     * score 权重：也叫分数（score）redis通过权重为集合中的元素进行升序排序（默认），权重可以重复
     */
    public void zAdd(String key, Object value, double score) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        zSet.add(key, value, score);
    }

    /**
     * 有序集合获取
     */
    public Set<Object> zRangeByScore(String key, double minScore, double maxScore) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        return zSet.rangeByScore(key, minScore, maxScore);
    }

    /**
     * 有序集合移除
     */
    public Long zRemove(String key, Object value) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        return zSet.remove(key, value);
    }

    /**
     * 有序集合中对指定成员的分数加上增量 increment
     */
    public double zSetIncrementScore(String key, String value, double i) {
        try {
            ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
            //返回新增元素后的分数
            return Optional.ofNullable(zSet.incrementScore(key, value, i)).orElse(0.0);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 通过前缀获取key
     *
     * @param prefix  前缀
     * @param scanNum 每次扫描的key个数
     */
    public Set<String> getListKey(String prefix, long scanNum) {
        Set<String> keys = null;
        try {
            keys = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
                Set<String> keysTmp = new HashSet<>();
                Cursor<byte[]> cursor = connection.scan(
                        new ScanOptions.ScanOptionsBuilder().match(prefix + "*")
                                .count(scanNum).build());
                while (cursor.hasNext()) {
                    keysTmp.add(new String(cursor.next()));
//                    keysTmp.add(new String(cursor.next()).replace(prefix, ""));
                }
                return keysTmp;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keys;
    }

    /**
     * 通过前缀获取key
     *
     * @param prefix 前缀
     * @param match  比配规则
     */
    public Set<String> getListKey(String prefix, String match) {
        Set<String> keys = null;
        try {
            keys = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
                Set<String> keysTmp = new HashSet<>();
                Cursor<byte[]> cursor = connection.scan(
                        new ScanOptions.ScanOptionsBuilder().match(prefix + match)
                                .count(1000).build());
                while (cursor.hasNext()) {
                    keysTmp.add(new String(cursor.next()));
//                    keysTmp.add(new String(cursor.next()).replace(prefix, ""));
                }
                return keysTmp;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keys;
    }

    /**
     * 获取hash表的 key和 value
     *
     * @param prefix 前缀
     * @param match  比配规则
     */
    public List<Map.Entry<Object, Object>> getListKeyAndValue(String prefix, String match) {
        List<Map.Entry<Object, Object>> list = null;
        try {
            Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(
                    prefix, ScanOptions.scanOptions().match(match).build());
            list = new ArrayList<>(16);
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> entry = cursor.next();
                // String key = (String) entry.getKey();Object value = entry.getValue();
                list.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

package org.pizza.event.repository;

import com.alibaba.fastjson.JSON;
import org.pizza.event.model.DomainEvent;
import org.pizza.event.model.EventLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 高巍
 * @since 2020/11/20 5:40 下午
 */
public class MysqlEventRepository implements EventRepository {

    private static final String DEFAULT_TABLE_NAME = "event_log";
    private String tableName;
    public MysqlEventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public boolean saveOrUpdate(DomainEvent event) {
        EventLog log = this.convert(event);
        String sql = "INSERT INTO " + tableName +
                " (event_type, " +
                "event_data, happen_time, create_time," +
                "update_time) " +
                "VALUES (?,?,?,?,?) " +
                "ON DUPLICATE KEY UPDATE " +
                "event_type = ?, " +
                "event_data = ?, " +
                "happen_time = ?, " +
                "update_time = ? ";

        int update = jdbcTemplate.update(sql, log.getType(),
                log.getData(), log.getTimestamp(), log.getCreateTime(),
                log.getUpdateTime(),
                //update
                log.getType(),
                log.getData(),
                log.getTimestamp(),
                new Date()
        );
        return update > 0;
    }

    private EventLog convert(DomainEvent event) {
        EventLog eventLog = new EventLog();
        eventLog.setTimestamp(event.getTimestamp());
        eventLog.setType(event.getClass().getName());
        eventLog.setData(JSON.toJSONString(event));
        eventLog.setCreateTime(new Date());
        eventLog.setUpdateTime(new Date());
        return eventLog;
    }

    @Override
    public Optional<EventLog> get(String eventId) {
        String selectSql = "select * from " + tableName + " where id=?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(selectSql, eventId);
        if (!CollectionUtils.isEmpty(list)) {
            return list.stream()
                    .filter(Objects::nonNull)
                    .map(this::buildByResultMap)
                    .findFirst();
        }
        return Optional.empty();
    }

    private EventLog buildByResultMap(Map<String, Object> map) {
        EventLog log = new EventLog();
        log.setId((Long) map.get("id"));
        log.setType((String) map.get("event_type"));
        log.setData((String) map.get("event_data"));
        log.setTimestamp((Long) map.get("happen_time"));
        log.setCreateTime((Date) map.get("create_time"));
        log.setUpdateTime((Date) map.get("update_time"));
        return log;
    }


    @Override
    public List<EventLog> page(int size, String type, Long timestamp) {
        if (size <= 0) {
            size = 10;
        }
        String sb = "select * from " + tableName + " where event_type =? and happen_time >? order by happen_time limit 0," + size;
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb, type, timestamp);
        if (!CollectionUtils.isEmpty(list)) {
            return list.stream().filter(Objects::nonNull)
                    .map(this::buildByResultMap)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private JdbcTemplate jdbcTemplate;
    @Override
    @PostConstruct
    public void init() {
        this.tableName = StringUtils.isEmpty(tableName) ? DEFAULT_TABLE_NAME : tableName;
        jdbcTemplate.update(this.buildMysql(this.tableName));
    }

    private String buildMysql(final String tableName) {
        return "CREATE TABLE IF NOT EXISTS `" +
                tableName +
                "` (" +
                "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT," +
                "  `event_type` varchar(150) ," +
                "  `event_data` varchar(1500)," +
                "  `happen_time` bigint(20)," +
                "  `create_time` datetime NOT NULL," +
                "  `update_time` datetime NOT NULL," +
                "  PRIMARY KEY (`id`))";
    }
}

package demo.api;

import demo.api.domain.ForumThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DeDatabase {

    /*
        C Create
        R Read
        U Update
        D Delete
     */

    @Autowired // Provide the template for use in this class
    private JdbcTemplate template;

    @EventListener(ApplicationStartedEvent.class)
    public void init()
    {
        //DDL
        template.update(
        "CREATE TABLE IF NOT EXISTS threads(" +
        "id SERIAL PRIMARY KEY, " +
        "title VARCHAR(255) NOT NULL, " +
        "body TEXT NOT NULL, " +
        "createdAt TIMESTAMP NOT NULL DEFAULT NOW())");

        template.update(
        "CREATE TABLE IF NOT EXISTS posts(" +
        "threadId INTEGER NOT NULL REFERENCES threads(id), " +
        "body TEXT NOT NULL," +
        "createdAt TIMESTAMP NOT NULL DEFAULT NOW())");
    }

    public Integer createThread(ForumThread thread)
    {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO threads(title, body) VALUES(?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, thread.getTitle());
            ps.setString(2, thread.getBody());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public List<ForumThread> readThreads()
    {
        // List that we are going to return
        List<ForumThread> threads = new ArrayList<>();

        // Query DB into a list of records
        List<Map<String, Object>> records = template.queryForList("SELECT * FROM threads");

        // For each record in records
        for(Map<String, Object> record : records)
        {
            threads.add(recordToThread(record));
        }

        return threads;
    }

    public ForumThread readThread(Integer id)
    {
        Map<String, Object> record = template.queryForMap("SELECT * FROM threads WHERE id=?", id);
        return recordToThread(record);
    }

    private static ForumThread recordToThread(Map<String, Object> record)
    {
        ForumThread thread = new ForumThread();
        thread.setId((Integer) record.get("id"));
        thread.setTitle((String) record.get("title"));
        thread.setBody((String) record.get("body"));
        thread.setCreatedAt(((Timestamp) record.get("createdAt")).toLocalDateTime());
        return thread;
    }
}

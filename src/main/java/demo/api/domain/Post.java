package demo.api.domain;

import java.time.LocalDateTime;

public class Post {

    private String body;
    private Integer threadId;
    private LocalDateTime createdAt;

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public Integer getThreadId()
    {
        return threadId;
    }

    public void setThreadId(Integer threadId)
    {
        this.threadId = threadId;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }
}

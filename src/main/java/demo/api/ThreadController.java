package demo.api;

import demo.api.domain.ForumThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/thread")
public class ThreadController {

    @Autowired
    private DeDatabase db;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer create(@RequestBody ForumThread thread)
    {
        return db.createThread(thread);
    }

    @GetMapping(value = "/all")
    public List<ForumThread> threads()
    {
        return db.readThreads();
    }

    @GetMapping
    public ForumThread threads(@RequestParam Integer id)
    {
        return db.readThread(id);
    }
}

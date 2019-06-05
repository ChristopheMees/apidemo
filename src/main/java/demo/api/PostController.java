package demo.api;

import demo.api.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private DeDatabase db;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Post post)
    {
        db.createPost(post);
    }
}

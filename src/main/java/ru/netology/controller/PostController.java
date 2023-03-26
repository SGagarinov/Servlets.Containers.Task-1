package ru.netology.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  @GetMapping
  public ConcurrentMap<Long, Post> all() {
    return service.all();
  }

  @GetMapping("/{id}")
  public Post getById(@PathVariable Long id) {
    try {
      return service.getById(id);
    }
    catch (Exception ex) {
      throw new ResponseStatusException(
              HttpStatus.NOT_FOUND, ex.getMessage()
      );
    }
  }

  @PostMapping
  public Post save(@RequestBody Post post) {
    try {
      return service.save(post);
    }
    catch (Exception ex) {
      throw new ResponseStatusException(
              HttpStatus.NOT_FOUND, ex.getMessage()
      );
    }
  }

  @DeleteMapping("/{id}")
  public void removeById(@PathVariable Long id) {
    service.removeById(id);
  }

  @GetMapping("/return/{id}")
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public Post returnById(@PathVariable Long id) {
    return service.returnById(id);
  }
}

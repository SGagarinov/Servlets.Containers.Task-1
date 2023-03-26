package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.concurrent.ConcurrentMap;

@Service
public class PostService {
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public ConcurrentMap<Long, Post> all() {
    return repository.all();
  }

  public Post getById(long id) {
    return repository.getById(id);
  }

  public Post save(Post post) {
    return repository.save(post);
  }

  public Long removeById(long id) {
    Long deletedId = getById(id).getId();
    if (deletedId != null) {
      repository.removeById(id);
      return deletedId;
    }
    return -1L;
  }

  public Post returnById(long id) {
    return repository.returnById(id);
  }
}


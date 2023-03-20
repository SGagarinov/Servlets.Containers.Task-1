package ru.netology.repository;

import ru.netology.model.Post;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
  private ConcurrentHashMap<Long, Post> list = new ConcurrentHashMap<>();
  private static AtomicLong COUNT = new AtomicLong();

  public ConcurrentHashMap<Long, Post> all() {
    return list;
  }

  public Post getById(long id) {
    return list.get(id);
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      COUNT.getAndIncrement();
      post.setId(COUNT.get());
      list.put(COUNT.get(), post);
    }
    else {
      list.put(post.getId(), post);
    }
    return post;
  }

  public void removeById(long id) {
    list.remove(id);
  }
}

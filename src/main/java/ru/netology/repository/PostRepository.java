package ru.netology.repository;

import ru.netology.model.Post;
import java.util.concurrent.ConcurrentHashMap;

public class PostRepository {
  private ConcurrentHashMap<Long, Post> list = new ConcurrentHashMap<>();
  private static long COUNT = 0;

  public ConcurrentHashMap<Long, Post> all() {
    return list;
  }

  public Post getById(long id) {
    return list.get(id);
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      COUNT++;
      post.setId(COUNT);
      list.put(COUNT, post);
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

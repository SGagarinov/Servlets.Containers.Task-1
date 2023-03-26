package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
  private ConcurrentHashMap<Long, Post> list = new ConcurrentHashMap<>();
  private static AtomicLong COUNT = new AtomicLong();

  public ConcurrentMap<Long, Post> all() {
    ConcurrentMap<Long, Post> result = list.entrySet()
            .stream()
            .filter(e -> !e.getValue().getDeleted())
            .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
    return result;
  }

  public Post getById(long id) {
    Post post = list.get(id);
    if (post == null || post.getDeleted())
      throw new NotFoundException("Данный пост не найден");
    else return post;
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      COUNT.getAndIncrement();
      post.setId(COUNT.get());
      list.put(COUNT.get(), post);
    }
    else {
      Post item = list.get(post.getId());
      if (item == null || item.getDeleted())
        throw new NotFoundException("Данный пост не найден");
      else
        list.put(post.getId(), post);
    }
    return post;
  }

  public void removeById(long id) {
    Post post = list.get(id);
    if (post != null) {
      post.setDeleted(true);
      list.put(id, post);
    }
  }

  public Post returnById(long id){
    Post post = list.get(id);
    if (post != null) {
      post.setDeleted(false);
      list.put(id, post);
    }
    else
      throw new NotFoundException("Данный пост не найден");
    return post;
  }
}

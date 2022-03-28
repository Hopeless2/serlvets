package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// Stub
public class PostRepository {
  private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
  private long newId = 1L;
  public List<Post> all() {
    return new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if(post.getId() == 0){
      post.setId(newId);
      posts.put(newId, post);
      newId++;
    }else if(post.getId() < newId && post.getId() > 0){
      posts.replace(post.getId(), post);
    }else{
      throw new NotFoundException("Пост не найден");
    }
    return post;
  }

  public void removeById(long id) {
    Optional.ofNullable(posts.remove(id)).orElseThrow(NotFoundException::new);
  }
}

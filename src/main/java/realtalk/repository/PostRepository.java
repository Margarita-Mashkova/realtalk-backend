package realtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import realtalk.model.Post;
import realtalk.model.User;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserOrderByDateDesc(User user);
    List<Post> findAllByTagsInOrderByDateDesc(Set<String> tags);
    List<Post> findAllByUserInOrderByDateDesc(Set<User> users);
}
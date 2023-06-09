package realtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import realtalk.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
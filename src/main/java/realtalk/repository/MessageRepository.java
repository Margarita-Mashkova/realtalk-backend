package realtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import realtalk.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
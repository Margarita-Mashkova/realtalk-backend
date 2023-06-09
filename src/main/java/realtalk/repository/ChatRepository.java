package realtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import realtalk.model.Chat;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c JOIN c.users u LEFT JOIN FETCH c.messages m WHERE u.id = :userId ORDER BY m.date ASC")
    List<Chat> findAllChatsByUser(Long userId);
    @Query("SELECT c FROM Chat c LEFT JOIN FETCH c.messages m WHERE c.id = :chatId ORDER BY m.date ASC")
    Chat findByIdOrderByMessagesDates(Long chatId);
}
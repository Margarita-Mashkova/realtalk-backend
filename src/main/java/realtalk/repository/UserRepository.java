package realtalk.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import realtalk.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"subscribers", "subscriptions", "chats"})
    User findByLogin(String login);
    @EntityGraph(attributePaths = {"subscribers", "subscriptions"})
//    List<User> findByLoginLikeIgnoreCaseOrNameLikeIgnoreCaseOrSurnameLikeIgnoreCase(String login, String name, String surname);
    List<User> findByLoginIgnoreCaseContainingOrNameIgnoreCaseContainingOrSurnameIgnoreCaseContaining(String login, String name, String surname);
}

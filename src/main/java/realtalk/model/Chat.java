package realtalk.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @NonNull
    public String name;
    public String image;
    @NonNull
    public Boolean isPrivate;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "chat_id")
    public List<Message> messages;
    @NonNull
    @ManyToMany(mappedBy = "chats")
    public List<User> users;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    public User creator;
    @PreRemove
    public void preRemove(){
        users.forEach(user -> user.getChats().remove(this));
    }
    public Date lastMessageDate;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "image = " + image + ", " +
                "isPrivate = " + isPrivate + ", " +
                "creator = " + creator + ", " +
                "lastMessageDate = " + lastMessageDate + ")";
    }
}

package realtalk.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @NonNull
    @Column(length = 1000)
    public String text;
    @NonNull
    public Date date;
    @NonNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "chat_id")
    public Chat chat;

    @NonNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    public User user;

    public String file;
    public Boolean isFileImage;
    @ManyToOne
    @JoinColumn(name = "reply_post_id")
    public Post replyPost;
    @PostPersist
    void updateChatLastMessageDate(){
        chat.setLastMessageDate(this.getDate());
    }
}

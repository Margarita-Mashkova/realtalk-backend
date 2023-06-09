package realtalk.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import realtalk.model.enums.Role;

import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    private String photo;
    private String city;
    private Date borthdate;
    private Set<String> tags;
    @ManyToMany
    @JoinTable(
            name = "user_subscribers",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "subscriber_id") }
    )
    private Set<User> subscribers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = { @JoinColumn(name = "subscription_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> subscriptions = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Post> posts;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Comment> comments;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Message> messages;

    @ManyToMany
    @JoinTable(
            name = "user_chat",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "chat_id") }
    )
    private List<Chat> chats;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(Role.USER.name()));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "login = " + login + ", " +
                "password = " + password + ", " +
                "name = " + name + ", " +
                "surname = " + surname + ", " +
                "photo = " + photo + ", " +
                "city = " + city + ", " +
                "borthdate = " + borthdate + ", " +
                "tags = " + tags + ")";
    }
}

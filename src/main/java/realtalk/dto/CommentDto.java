package realtalk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class CommentDto {
    private Long id;
    private String text;
    @JsonFormat(pattern="dd MMM yyyy в HH:mm")
    private Date date;
    private UserDto user;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long postId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<UserDto> likes;
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private int likesCount;
//    public int getLikesCount() {
//        return likes.size();
//    }
}

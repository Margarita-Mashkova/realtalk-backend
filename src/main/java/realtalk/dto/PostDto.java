package realtalk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class PostDto {
    private Long id;
    private String text;
    @JsonFormat(pattern="dd MMM yyyy в HH:mm")
    private Date date;
    private Set<String> tags;
    private UserDto user;
    private String photo;
    private List<CommentDto> comments;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<UserDto> likes;
}
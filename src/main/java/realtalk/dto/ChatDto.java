package realtalk.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatDto {
    private Long id;
    private String name;
    private String image;
    private Boolean isPrivate;
    private List<UserDto> users;
    private List<MessageOnReadDto> messages;
    private UserDto creator;
}

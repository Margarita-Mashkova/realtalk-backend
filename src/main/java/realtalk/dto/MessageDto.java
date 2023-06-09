package realtalk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.BinaryMessage;

import java.util.Date;
@Data
@NoArgsConstructor
public abstract class MessageDto {
    private Long id;
    private UserDto user;
    private String text;
    @JsonFormat(pattern="dd MMM yyyy в HH:mm")
    private Date date;
    private String file;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private FileDto binaryFile;
    private Boolean isFileImage;
    private PostDto replyPost;
    public MessageAction getAction() {
        return null;
    }
}

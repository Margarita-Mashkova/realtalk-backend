package realtalk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageOnUpdateDto extends MessageDto{
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private Boolean isFileDeleted;
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private Boolean isReplyDeleted;
    @Override
    public MessageAction getAction() {
        return MessageAction.ON_UPDATE;
    }
}

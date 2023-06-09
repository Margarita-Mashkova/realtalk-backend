package realtalk.dto;

import lombok.Data;

@Data
public class MessageOnDeleteDto extends MessageDto{
    @Override
    public MessageAction getAction() {
        return MessageAction.ON_DELETE;
    }
}

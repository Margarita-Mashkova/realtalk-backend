package realtalk.dto;

import lombok.Data;

@Data
public class MessageOnCreateDto extends MessageDto{
    @Override
    public MessageAction getAction() {
        return MessageAction.ON_CREATE;
    }
}

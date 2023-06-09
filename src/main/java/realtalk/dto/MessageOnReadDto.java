package realtalk.dto;

public class MessageOnReadDto extends MessageDto{
    @Override
    public MessageAction getAction() {
        return MessageAction.ON_READ;
    }
}

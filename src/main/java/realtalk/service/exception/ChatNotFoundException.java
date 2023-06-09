package realtalk.service.exception;

public class ChatNotFoundException extends RuntimeException{
    public ChatNotFoundException(Long id){
        super(String.format("Чат с ID %s не найден", id));
    }
}

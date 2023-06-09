package realtalk.service.exception;

public class MessageNotFoundException extends RuntimeException{

    public MessageNotFoundException(Long id) {
        super(String.format("Сообщение с ID %s не найдено", id));
    }
}

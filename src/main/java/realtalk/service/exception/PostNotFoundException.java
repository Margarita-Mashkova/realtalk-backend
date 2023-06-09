package realtalk.service.exception;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException(Long id) {
        super(String.format("Пост с ID %s не найден", id));
    }
}

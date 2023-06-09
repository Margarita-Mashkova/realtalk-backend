package realtalk.service.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(Long id) {
        super(String.format("Комментарий с ID %s не найден", id));
    }
}

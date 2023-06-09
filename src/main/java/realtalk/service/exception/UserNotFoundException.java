package realtalk.service.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super(String.format("Пользователь с ID %s не найден", id));
    }
}

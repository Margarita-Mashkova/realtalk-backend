package realtalk.service.exception;

public class UserLoginExistsException extends RuntimeException{

    public UserLoginExistsException(String login){
        super(String.format("Пользователь с логином %s уже существует", login));
    }
}

package realtalk.service.exception;

public class WrongLoginOrPasswordException extends RuntimeException{
    public WrongLoginOrPasswordException(){
        super("Неверный логин или пароль");
    }
}

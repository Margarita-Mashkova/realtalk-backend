package realtalk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.Set;

/**
 * Дто для упрощения информации о пользователе
 */
@Data
public class UserDto {
    private Long id;
    @Length(min = 3, max = 20)
    private String login;
    @Length(min = 3, max = 20)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Length(min = 1, max = 20)
    private String name;
    @Length(min = 3, max = 20)
    private String surname;
    private String photo;
    private String city;
    private Date borthdate;
}

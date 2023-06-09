package realtalk.mapper;

import org.mapstruct.*;
import org.springframework.data.jpa.repository.EntityGraph;
import realtalk.dto.*;
import realtalk.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User fromRegisterDto(RegisterDto dto);
    User fromLoginDto(LoginDto dto);
    UserDto toUserDto(User user);
    UserProfileInfoDto toUserProfileInfoDto(User user);
}
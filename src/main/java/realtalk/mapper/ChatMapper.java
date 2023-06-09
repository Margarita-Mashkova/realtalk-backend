package realtalk.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import realtalk.dto.ChatDto;
import realtalk.model.Chat;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapper {
    ChatDto toChatDto(Chat chat);
}

package realtalk.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import realtalk.dto.*;
import realtalk.model.Message;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {
    //Message fromMessageDto(MessageDto messageDto);
    MessageOnReadDto toMessageOnReadDto(Message messageDto);
    MessageOnCreateDto toMessageOnCreateDto(Message message);
    MessageOnDeleteDto toMessageOnDeleteDto(Message message);
    MessageOnUpdateDto toMessageOnUpdateDto(Message message);

}

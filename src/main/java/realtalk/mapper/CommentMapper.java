package realtalk.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import realtalk.dto.CommentDto;
import realtalk.model.Comment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comment fromCommentDto(CommentDto commentDto);
    CommentDto toCommentDto(Comment comment);
}

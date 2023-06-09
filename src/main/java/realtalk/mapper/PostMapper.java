package realtalk.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import realtalk.dto.PostDto;
import realtalk.model.Post;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    Post fromPostDto(PostDto postDto);
    PostDto toPostDto(Post post);
}

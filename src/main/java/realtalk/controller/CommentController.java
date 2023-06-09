package realtalk.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import realtalk.dto.CommentDto;
import realtalk.mapper.CommentMapper;
import realtalk.mapper.PostMapper;
import realtalk.model.User;
import realtalk.service.CommentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private PostMapper postMapper;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public CommentDto createComment(@AuthenticationPrincipal User user, @RequestBody CommentDto commentDto){
        return commentMapper.toCommentDto(commentService.addComment(user, commentDto.getPostId(), commentDto.getText()));
    }

    @PutMapping("/{id}")
    public CommentDto editComment(@PathVariable Long id, @RequestBody CommentDto commentDto){
        return commentMapper.toCommentDto(commentService.updateComment(id, commentDto.getText()));
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
    }

    /**
     * @return true - поставил лайк, false - убрал лайк
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/like/{id}")
    public boolean like(@AuthenticationPrincipal User user, @PathVariable Long id) {
        //TODO: В будущем нужно подумать о полиморфизме comment и post
        return commentService.likeComment(user, commentService.findComment(id));
    }

}

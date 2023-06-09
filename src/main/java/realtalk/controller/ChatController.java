package realtalk.controller;

import io.jsonwebtoken.security.SecurityException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import realtalk.dto.ChatCreateDto;
import realtalk.dto.ChatDto;
import realtalk.mapper.ChatMapper;
import realtalk.model.User;
import realtalk.service.ChatService;
import realtalk.service.exception.ChatNotFoundException;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMapper chatMapper;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ChatDto createChat(@AuthenticationPrincipal User user, @RequestBody ChatCreateDto chatCreateDto){
        return chatMapper.toChatDto(chatService.createChat(chatCreateDto.getName(),
                chatCreateDto.getIsPrivate(), chatCreateDto.getUserIds(), user));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public List<ChatDto> getChatsByUser(@AuthenticationPrincipal User user){
        return chatService.findAllChatsByUser(user).stream()
                .map(chat -> chatMapper.toChatDto(chat))
                .toList();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}")
    public ChatDto getChatById(@PathVariable Long id, @AuthenticationPrincipal User user){
        return chatMapper.toChatDto(chatService.findChat(id, user));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "edit-chat/{id}", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ChatDto editChat(@PathVariable Long id, @RequestParam(required = false) String name,
                            @RequestParam(required = false) MultipartFile image ){
        return chatMapper.toChatDto(chatService.editChat(id, name, image));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("{id}/add-members")
    public ChatDto addUsersToChat(@PathVariable Long id, @RequestBody List<Long> userIds){
        return chatMapper.toChatDto(chatService.addUsersToChat(userIds, id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("{id}/delete-members")
    public ChatDto deleteUsersFromChat(@PathVariable Long id, @RequestBody List<Long> userIds){
        return chatMapper.toChatDto(chatService.deleteUsersFromChat(userIds, id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/leave/{id}")
    public ChatDto leaveChat(@AuthenticationPrincipal User user, @PathVariable Long id){
        return chatMapper.toChatDto(chatService.leaveChat(user, id));
    }

    @DeleteMapping("/{id}")
    public ChatDto deleteChat(@PathVariable Long id){
        return chatMapper.toChatDto(chatService.deleteChat(id));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(SecurityException.class)
    public String chatSecurityException(SecurityException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ChatNotFoundException.class)
    public String chatNotFoundException(ChatNotFoundException e) {
        return e.getMessage();
    }
}

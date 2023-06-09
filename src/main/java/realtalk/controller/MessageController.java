package realtalk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.BinaryMessage;
import realtalk.dto.MessageOnCreateDto;
import realtalk.dto.MessageOnUpdateDto;
import realtalk.service.MessageService;

@CrossOrigin(origins = "*")
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("create-message/{id}")
    public void sendMessage(MessageOnCreateDto messageDto, @DestinationVariable Long id) {
        messageService.createMessage(messageDto.getUser().getLogin(), id, messageDto.getText(),
                messageDto.getBinaryFile(), messageDto.getReplyPost());
    }

    @MessageMapping("update-message/{id}")
    public void updateMessage(@DestinationVariable Long id, MessageOnUpdateDto messageDto) {
        messageService.updateMessage(id, messageDto.getText(), messageDto.getBinaryFile(),
                messageDto.getIsFileDeleted(), messageDto.getIsReplyDeleted());
    }

    @MessageMapping("delete-message/{id}")
    public void deleteMessage(@DestinationVariable Long id) {
        messageService.deleteMessage(id);
    }
}

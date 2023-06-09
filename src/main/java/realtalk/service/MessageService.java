package realtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import realtalk.bot.SimpleBot;
import realtalk.dto.FileDto;
import realtalk.dto.MessageDto;
import realtalk.dto.MessageOnCreateDto;
import realtalk.dto.PostDto;
import realtalk.mapper.MessageMapper;
import realtalk.model.*;
import realtalk.repository.ChatRepository;
import realtalk.repository.MessageRepository;
import realtalk.service.exception.ChatNotFoundException;
import realtalk.util.FileUploadUtil;

import java.util.Date;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileUploadUtil fileUploadUtil;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private SimpleBot bot;

    @Transactional(readOnly = true)
    public Message findMessage(Long id) {
        final Optional<Message> message = messageRepository.findById(id);
        return message.orElseThrow(() -> new ChatNotFoundException(id));
    }

    @Transactional
    public void createMessage(String userLogin, Long chatId, String text, FileDto file, PostDto replyPost) {
        Chat chat = chatService.findChat(chatId);
        User user = userService.findUserByLogin(userLogin);
        Date date = new Date();

        final Message message = new Message(text, date, chat, user);
        if(file!=null){
            message.setIsFileImage(file.getBase64().startsWith("data:image"));
            message.setFile(fileUploadUtil.uploadFile(file));
        }
        if(replyPost!=null){
           message.setReplyPost(postService.findPost(replyPost.getId()));
        }
        messageRepository.save(message);
        chat.setLastMessageDate(message.getDate());

        chatRepository.save(chat);

        simpMessagingTemplate.convertAndSend("/topic/"+chatId, messageMapper.toMessageOnCreateDto(message));

        User userBot = userService.findUserByLogin("bot");
        if(chat.getUsers().contains(userBot)){
            String textFromBot = bot.sayInReturn(text, true);
            Message messageFromBot = new Message(textFromBot, new Date(), chat, userBot);
            messageRepository.save(messageFromBot);
            chat.setLastMessageDate(messageFromBot.getDate());
            simpMessagingTemplate.convertAndSend("/topic/"+chatId, messageMapper.toMessageOnCreateDto(messageFromBot));
        }
    }

    @Transactional
    public void updateMessage(Long id, String text, FileDto file, boolean isFileDeleted, boolean isReplyDeleted){
        final Message message = findMessage(id);
        message.setText(text);
        if(file!=null){
            message.setIsFileImage(file.getBase64().startsWith("data:image"));
            message.setFile(fileUploadUtil.uploadFile(file));
        }
        if(isFileDeleted){
            message.setFile(null);
        }
        if(isReplyDeleted){
            message.setReplyPost(null);
        }
        messageRepository.save(message);

        simpMessagingTemplate.convertAndSend("/topic/"+message.getChat().getId(), messageMapper.toMessageOnUpdateDto(message));
    }

    @Transactional
    public void deleteMessage(Long id){
        final Message message = findMessage(id);
        messageRepository.delete(message);

        simpMessagingTemplate.convertAndSend("/topic/"+message.getChat().getId(), messageMapper.toMessageOnDeleteDto(message));
    }


}

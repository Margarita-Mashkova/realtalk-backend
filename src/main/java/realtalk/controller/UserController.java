package realtalk.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import realtalk.dto.*;
import realtalk.mapper.PostMapper;
import realtalk.mapper.UserMapper;
import realtalk.model.User;
import realtalk.service.PostService;
import realtalk.service.UserService;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostMapper postMapper;

    @PostMapping(value = "/register", consumes = {MULTIPART_FORM_DATA_VALUE})
    public void register(@RequestParam String name,
                         @RequestParam String surname, @RequestParam String password,
                         @RequestParam String login, @RequestParam(required = false) MultipartFile file,
                         @RequestParam(required = false) @DateTimeFormat(pattern= "yyyy-MM-dd") Date borthdate,
                         @RequestParam(required = false) String city){
        userService.registration(login, password, name, surname, file, borthdate, city);
    }
    @PostMapping("/auth")
    public String auth(@RequestBody LoginDto loginDto) {
        User user = userMapper.fromLoginDto(loginDto);
        return userService.authentication(user);
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    public UserProfileInfoDto me(@AuthenticationPrincipal User user) {
        return userMapper.toUserProfileInfoDto(user);
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/edit-profile", consumes = {MULTIPART_FORM_DATA_VALUE})
    public UserDto editProfile(@AuthenticationPrincipal User user, @RequestParam(required = false) String name,
                               @RequestParam(required = false) String surname, @RequestParam(required = false) String password,
                               @RequestParam(required = false) String login, @RequestParam(required = false) MultipartFile photo,
                               @RequestParam(required = false)@DateTimeFormat(pattern= "yyyy-MM-dd") Date borthdate,
                               @RequestParam(required = false) String city){
        return userMapper.toUserDto(userService.updateUser(user,login, password, name, surname, photo, borthdate, city));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/edit-preferences")
    public UserProfileInfoDto editPreferences(@AuthenticationPrincipal User user, @RequestBody TagsDto tags){
        return userMapper.toUserProfileInfoDto(userService.updateUserPreferences(user, tags.getTags()));
    }

    /**
     * @return true - подписался, false - отписался
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/subscribe/{id}")
    public boolean subscribe(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return userService.subscribe(user, userService.findUser(id));
    }

    @GetMapping("/users")
    public List<UserDto> findAllUsers(){
        return userService.findAllUsers().stream()
                .map(user -> userMapper.toUserDto(user))
                .toList();
    }

    @GetMapping("/find")
    public List<UserProfileInfoDto> findUsersByQuery(@RequestParam String query){
        return userService.findUsersByQuery(query)
                .stream()
                .map(user -> userMapper.toUserProfileInfoDto(user))
                .toList();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/preferences-find")
    public List<UserProfileInfoDto> findUsersByPreferences(@AuthenticationPrincipal User user){
        return userService.findUsersByPreferences(user)
                .stream()
                .map(userFind -> userMapper.toUserProfileInfoDto(userFind))
                .toList();
    }

    @GetMapping("/{login}")
    public UserProfileInfoDto viewProfile(@PathVariable String login){
        return userMapper.toUserProfileInfoDto(userService.findUserByLogin(login));
    }

    @GetMapping("/find-by-login")
    public UserProfileInfoDto findUserByLogin(@RequestParam String login){
        return userMapper.toUserProfileInfoDto(userService.findUserByLogin(login));
    }

    @GetMapping("/user-posts/{login}")
    public List<PostDto> viewUserPosts(@PathVariable String login) {
        return postService.getUserPosts(userService.findUserByLogin(login))
                .stream()
                .map(post -> postMapper.toPostDto(post))
                .toList();
    }
}

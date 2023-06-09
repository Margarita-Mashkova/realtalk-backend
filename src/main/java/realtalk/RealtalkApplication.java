package realtalk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import realtalk.model.User;
import realtalk.repository.UserRepository;

@SpringBootApplication
public class RealtalkApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserRepository userRepository;

		public static void main(String[] args) {
		SpringApplication.run(RealtalkApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setLogin("bot");
		user.setPassword(encoder.encode("bot"));
		user.setName("Чаттер");
		user.setSurname("Бот");
		user.setPhoto("bot.png");
		if(userRepository.findByLogin("bot")==null) userRepository.save(user);
	}
}

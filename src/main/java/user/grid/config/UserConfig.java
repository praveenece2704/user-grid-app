package user.grid.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import user.grid.model.UserData;
import user.grid.model.UserResponse;
import user.grid.repo.UserRepository;

@Configuration
@Profile({ "dev", "local" })
public class UserConfig {
	
	
	@Value("${user.api.url}")
    private String url;
	
	@Autowired
	UserRepository userRepository;

	@Bean
	public List<UserData> loadUsersFromApi() {
		RestTemplate restTemplate = new RestTemplate();

		UserResponse response = restTemplate.getForObject(url, UserResponse.class);

		List<UserData> users = response.getUsers();

		if (users != null) {
			userRepository.saveAll(users);
		}
		return users;
	}

	@Bean
	public String loadUsersToDB() {
		if (loadUsersFromApi() != null) {
			userRepository.saveAll(loadUsersFromApi());
		}
		return "Uploaded Successfully";
	}

}

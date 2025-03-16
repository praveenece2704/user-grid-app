package user.grid.service;

import java.util.List;
import java.util.Optional;

import user.grid.model.UserData;


public interface UserService {

	public List<UserData> getAllUsers();

	public List<UserData> getUsersByRole(String role);

	public List<UserData> getUsersSortedByAge(String sortOrder);

	public Optional<UserData> getUserByIdOrSsn(Long id, String ssn);


}

package user.grid.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import user.grid.exception.InvalidRoleException;
import user.grid.exception.UserNotFoundException;
import user.grid.model.UserData;
import user.grid.repo.UserRepository;
import user.grid.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserData> getAllUsers() {
        log.debug("Fetching all users from the repository");
        List<UserData> users = userRepository.findAll();
        
        if (users.isEmpty()) {
            log.error("No users found in the repository");
            throw new UserNotFoundException("No users found");
        }

        log.info("Retrieved {} users from the repository", users.size());
        return users;
    }

    public List<UserData> getUsersByRole(String role) {
        log.debug("Fetching users by role: {}", role);
        List<UserData> users = userRepository.findByRole(role);
        
        if (users.isEmpty()) {
            log.error("No users found for role: {}", role);
            throw new InvalidRoleException("No users found for role: " + role);
        }

        log.info("Retrieved {} users for role: {}", users.size(), role);
        return users;
    }

    public List<UserData> getUsersSortedByAge(String sortOrder) {
        log.debug("Fetching users sorted by age in '{}' order", sortOrder);

        if (sortOrder == null || (!sortOrder.equalsIgnoreCase("asc") && !sortOrder.equalsIgnoreCase("desc"))) {
            log.error("Invalid sort order: {}", sortOrder);
            throw new InvalidRoleException("Invalid sort order: " + sortOrder);
        }

        List<UserData> sortedUsers;
        if (sortOrder.equalsIgnoreCase("desc")) {
            sortedUsers = userRepository.findAllByOrderByAgeDesc();
            log.info("Retrieved {} users sorted by age in descending order", sortedUsers.size());
        } else {
            sortedUsers = userRepository.findAllByOrderByAgeAsc();
            log.info("Retrieved {} users sorted by age in ascending order", sortedUsers.size());
        }
        
        return sortedUsers;
    }

    public Optional<UserData> getUserById(Long id) {
        log.debug("Fetching user by ID: {}", id);
        Optional<UserData> user = userRepository.findById(id);
        
        if (user.isEmpty()) {
            log.error("User not found with ID: {}", id);
            throw new UserNotFoundException("User not found with ID: " + id);
        }

        log.info("User found with ID: {}", id);
        return user;
    }

    public Optional<UserData> getUserByIdOrSsn(Long id, String ssn) {
        if (id != null) {
            log.debug("Fetching user by ID: {}", id);
            Optional<UserData> user = userRepository.findById(id);
            if (user.isEmpty()) {
                log.error("User not found with ID: {}", id);
                throw new UserNotFoundException("User not found with ID: " + id);
            }
            log.info("User found with ID: {}", id);
            return user;
        } else if (ssn != null) {
            log.debug("Fetching user by SSN: {}", ssn);
            Optional<UserData> user = userRepository.findBySsn(ssn);
            if (user.isEmpty()) {
                log.error("User not found with SSN: {}", ssn);
                throw new UserNotFoundException("User not found with SSN: " + ssn);
            }
            log.info("User found with SSN: {}", ssn);
            return user;
        } else {
            log.error("Neither ID nor SSN was provided.");
            throw new IllegalArgumentException("Either ID or SSN must be provided.");
        }
    }
}

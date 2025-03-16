package user.grid.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import user.grid.exception.UserNotFoundException;
import user.grid.model.UserData;
import user.grid.service.UserService;

@RestController
@CrossOrigin(origins = "${cors.allowed.origin:'http://localhost:4200'}")
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserData>> getAllUsers() {
        log.debug("Request to get all users");
        try {
            List<UserData> users = userService.getAllUsers();
            log.info("Successfully retrieved {} users", users.size());
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error occurred while fetching users: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get users by role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserData>> getUsersByRole(@PathVariable String role) {
        log.debug("Request to get users by role: {}", role);
        try {
            List<UserData> users = userService.getUsersByRole(role);
            log.info("Successfully retrieved {} users for role: {}", users.size(), role);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error occurred while fetching users by role '{}': {}", role, ex.getMessage(), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get users sorted by age
    @GetMapping("/sorted")
    public ResponseEntity<List<UserData>> getUsersSortedByAge(@RequestParam String sortOrder) {
        log.debug("Request to get users sorted by age in '{}' order", sortOrder);
        try {
            List<UserData> users = userService.getUsersSortedByAge(sortOrder);
            log.info("Successfully retrieved {} users sorted by age in '{}' order", users.size(), sortOrder);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error occurred while fetching users sorted by age '{}': {}", sortOrder, ex.getMessage(), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserData> getUserById(@PathVariable Long id) {
        log.debug("Request to get user by ID: {}", id);
        try {
            Optional<UserData> user = userService.getUserByIdOrSsn(id, null);
            if (user.isPresent()) {
                log.info("Successfully found user with ID: {}", id);
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                log.warn("User not found with ID: {}", id);
                throw new UserNotFoundException("User not found with ID: " + id);
            }
        } catch (UserNotFoundException ex) {
            log.error("User not found with ID: {}", id);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("Error occurred while fetching user by ID '{}': {}", id, ex.getMessage(), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get user by SSN
    @GetMapping("/ssn/{ssn}")
    public ResponseEntity<UserData> getUserBySsn(@PathVariable String ssn) {
        log.debug("Request to get user by SSN: {}", ssn);
        try {
            Optional<UserData> user = userService.getUserByIdOrSsn(null, ssn);
            if (user.isPresent()) {
                log.info("Successfully found user with SSN: {}", ssn);
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                log.warn("User not found with SSN: {}", ssn);
                throw new UserNotFoundException("User not found with SSN: " + ssn);
            }
        } catch (UserNotFoundException ex) {
            log.error("User not found with SSN: {}", ssn);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("Error occurred while fetching user by SSN '{}': {}", ssn, ex.getMessage(), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

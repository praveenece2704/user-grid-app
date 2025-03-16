package user.grid.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import user.grid.exception.InvalidRoleException;
import user.grid.exception.UserNotFoundException;
import user.grid.model.UserData;
import user.grid.repo.UserRepository;
import user.grid.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserData user1;
    private UserData user2;

    @BeforeEach
    void setUp() {
        user1 = new UserData();
        user1.setId(1L);
        user1.setFirstName("Emily");
        user1.setLastName("Johnson");

        user2 = new UserData();
        user2.setId(2L);
        user2.setFirstName("Michael");
        user2.setLastName("Williams");
    }

    @Test
    void testGetAllUsers_Success() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        List<UserData> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("Emily", users.get(0).getFirstName());
        assertEquals("Michael", users.get(1).getFirstName());
    }

    @Test
    void testGetAllUsers_NoUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getAllUsers();
        });

        assertEquals("No users found", exception.getMessage());
    }

    @Test
    void testGetUsersByRole_Success() {
        when(userRepository.findByRole("admin")).thenReturn(Arrays.asList(user1));

        List<UserData> users = userService.getUsersByRole("admin");

        assertEquals(1, users.size());
        assertEquals("Emily", users.get(0).getFirstName());
    }

    @Test
    void testGetUsersByRole_NoUsers() {
        when(userRepository.findByRole("admin")).thenReturn(Arrays.asList());

        InvalidRoleException exception = assertThrows(InvalidRoleException.class, () -> {
            userService.getUsersByRole("admin");
        });

        assertEquals("No users found for role: admin", exception.getMessage());
    }

    @Test
    void testGetUsersSortedByAge_Asc() {
        when(userRepository.findAllByOrderByAgeAsc()).thenReturn(Arrays.asList(user1, user2));
        List<UserData> users = userService.getUsersSortedByAge("asc");

        assertEquals(2, users.size());
        assertEquals("Emily", users.get(0).getFirstName());
        assertEquals("Michael", users.get(1).getFirstName());
    }

    @Test
    void testGetUsersSortedByAge_Desc() {
        when(userRepository.findAllByOrderByAgeDesc()).thenReturn(Arrays.asList(user2, user1));

        List<UserData> users = userService.getUsersSortedByAge("desc");

        assertEquals(2, users.size());
        assertEquals("Michael", users.get(0).getFirstName());
        assertEquals("Emily", users.get(1).getFirstName());
    }

    @Test
    void testGetUsersSortedByAge_InvalidSortOrder() {
        InvalidRoleException exception = assertThrows(InvalidRoleException.class, () -> {
            userService.getUsersSortedByAge("invalid");
        });

        assertEquals("Invalid sort order: invalid", exception.getMessage());
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        Optional<UserData> user = userService.getUserById(1L);

        assertTrue(user.isPresent());
        assertEquals("Emily", user.get().getFirstName());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(99L);
        });

        assertEquals("User not found with ID: 99", exception.getMessage());
    }
    
    @Test
    void testGetUserByIdOrSsn_IdProvided_UserFound() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(user1));
        Optional<UserData> result = userService.getUserByIdOrSsn(id, null);

        assertTrue(result.isPresent());
        assertEquals(user1, result.get());
    }

    @Test
    void testGetUserByIdOrSsn_IdProvided_UserNotFound() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByIdOrSsn(id, null);
        });
        assertEquals("User not found with ID: " + id, exception.getMessage());
    }

    @Test
    void testGetUserByIdOrSsn_SsnProvided_UserFound() {
        String ssn = "123-45-6789";
        when(userRepository.findBySsn(ssn)).thenReturn(Optional.of(user1));
        Optional<UserData> result = userService.getUserByIdOrSsn(null, ssn);
        assertTrue(result.isPresent());
        assertEquals(user1, result.get());
    }

    @Test
    void testGetUserByIdOrSsn_SsnProvided_UserNotFound() {
        String ssn = "123-45-6789";
        when(userRepository.findBySsn(ssn)).thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByIdOrSsn(null, ssn);
        });
        assertEquals("User not found with SSN: " + ssn, exception.getMessage());
    }

    @Test
    void testGetUserByIdOrSsn_NeitherIdNorSsnProvided() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserByIdOrSsn(null, null);
        });
        assertEquals("Either ID or SSN must be provided.", exception.getMessage());
    }
}


package user.grid.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import user.grid.exception.UserNotFoundException;
import user.grid.model.UserData;
import user.grid.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService; 

    @InjectMocks
    private UserController userController;  

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
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        ResponseEntity<List<UserData>> result = userController.getAllUsers();

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(2, result.getBody().size());
        assertEquals("Emily", result.getBody().get(0).getFirstName());
        assertEquals("Michael", result.getBody().get(1).getFirstName());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        when(userService.getUserByIdOrSsn(1L, null)).thenReturn(Optional.of(user1));

        ResponseEntity<UserData> result = userController.getUserById(1L);

        assertTrue(result.hasBody());
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Emily", result.getBody().getFirstName());

        verify(userService, times(1)).getUserByIdOrSsn(1L, null);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userService.getUserByIdOrSsn(3L, null)).thenReturn(Optional.empty());

        ResponseEntity<UserData> result = userController.getUserById(3L);

        assertFalse(result.hasBody());
        assertEquals(404, result.getStatusCodeValue());

        verify(userService, times(1)).getUserByIdOrSsn(3L, null);
    }

    @Test
    void testGetUsersByRole() {
        when(userService.getUsersByRole("admin")).thenReturn(Arrays.asList(user2));

        ResponseEntity<List<UserData>> result = userController.getUsersByRole("admin");

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(1, result.getBody().size());
        assertEquals("Michael", result.getBody().get(0).getFirstName());

        verify(userService, times(1)).getUsersByRole("admin");
    }

    @Test
    void testGetUsersSortedByAge() {
        when(userService.getUsersSortedByAge("desc")).thenReturn(Arrays.asList(user2, user1));
        ResponseEntity<List<UserData>> result = userController.getUsersSortedByAge("desc");
        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(2, result.getBody().size());
        assertEquals("Michael", result.getBody().get(0).getFirstName());
        assertEquals("Emily", result.getBody().get(1).getFirstName());
        verify(userService, times(1)).getUsersSortedByAge("desc");
    }

    @Test
    void testGetUserById_UserNotFoundException() {
        when(userService.getUserByIdOrSsn(3L, null)).thenThrow(new UserNotFoundException("User with ID 3 not found"));
        ResponseEntity<UserData> result = userController.getUserById(3L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetUserById_ExceptionHandling() {
        when(userService.getUserByIdOrSsn(1L, null)).thenThrow(new RuntimeException("Unexpected error"));
        ResponseEntity<UserData> result = userController.getUserById(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetAllUsers_ExceptionHandling() {
        when(userService.getAllUsers()).thenThrow(new RuntimeException("Unexpected error"));
        ResponseEntity<List<UserData>> result = userController.getAllUsers();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetUsersByRole_ExceptionHandling() {
        when(userService.getUsersByRole("admin")).thenThrow(new RuntimeException("Unexpected error"));
        ResponseEntity<List<UserData>> result = userController.getUsersByRole("admin");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetUsersSortedByAge_ExceptionHandling() {
        when(userService.getUsersSortedByAge("desc")).thenThrow(new RuntimeException("Unexpected error"));
        ResponseEntity<List<UserData>> result = userController.getUsersSortedByAge("desc");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNull(result.getBody());
    }
    
    @Test
    void testGetUserBySsn_UserFound() {
        String ssn = "123-45-6789";
        when(userService.getUserByIdOrSsn(null, ssn)).thenReturn(Optional.of(user2));

        ResponseEntity<UserData> result = userController.getUserBySsn(ssn);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(user2, result.getBody());
    }

    @Test
    void testGetUserBySsn_UserNotFound() {
        String ssn = "123-45-6789";
  
        ResponseEntity<UserData> result = userController.getUserBySsn(ssn);
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetUserBySsn_GeneralException() {
   
        String ssn = "123-45-6789";
        when(userService.getUserByIdOrSsn(null, ssn)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<UserData> result = userController.getUserBySsn(ssn);
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNull(result.getBody());
    }


}

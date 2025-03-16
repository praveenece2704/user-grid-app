package user.grid.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
public class UserData {

    @Id
    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Size(max = 50, message = "Maiden name must be less than 50 characters")
    private String maidenName;

    @Min(value = 0, message = "Age must be a positive number")
    private int age;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    private String email;

    @Pattern(regexp = "^\\+?[1-9][0-9]{1,14}$", message = "Phone number should be valid")
    private String phone;

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "Birth date cannot be null")
    private String birthDate;

    private String image;

    private String bloodGroup;

    @Min(value = 0, message = "Height must be a positive number")
    private double height;

    @Min(value = 0, message = "Weight must be a positive number")
    private double weight;

    private String eyeColor;

    @Embedded
    private Hair hair;

    private String ip;

    @Embedded
    private Address address;

    private String macAddress;

    private String university;

    @Embedded
    private Bank bank;

    @Embedded
    private Company company;

    private String ein;

    @NotNull(message = "SSN cannot be null")
    private String ssn;

    private String userAgent;

    @Embedded
    private Crypto crypto;

    @NotNull(message = "Role cannot be null")
    private String role;
}

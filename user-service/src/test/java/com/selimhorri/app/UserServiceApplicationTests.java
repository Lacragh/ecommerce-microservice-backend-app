package com.selimhorri.app;

import com.selimhorri.app.dto.CredentialDto;
import com.selimhorri.app.dto.UserDto;
import com.selimhorri.app.domain.User;
import com.selimhorri.app.repository.UserRepository;
import com.selimhorri.app.service.UserService;
import com.selimhorri.app.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceApplicationTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserById() {
        User user = User.builder()
            .userId(1)
            .firstName("test")
            .email("test@mail.com")
            .credential(
                com.selimhorri.app.domain.Credential.builder()
                    .credentialId(1)
                    .username("user")
                    .password("pass")
                    .build()
            )
            .build();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        UserDto result = userService.findById(1);
        assertEquals("test", result.getFirstName());
    }

    @Test
    void testCreateUser() {
        CredentialDto credentialDto = CredentialDto.builder().credentialId(1).username("user").password("pass").build();
        UserDto userDto = UserDto.builder()
            .firstName("newuser")
            .email("new@mail.com")
            .credentialDto(credentialDto)
            .build();
        User user = User.builder()
            .userId(2)
            .firstName("newuser")
            .email("new@mail.com")
            .credential(
                com.selimhorri.app.domain.Credential.builder()
                    .credentialId(1)
                    .username("user")
                    .password("pass")
                    .build()
            )
            .build();
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto result = userService.save(userDto);
        assertEquals("newuser", result.getFirstName());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1);
        assertDoesNotThrow(() -> userService.deleteById(1));
    }

    @Test
    void testUpdateUser() {
        CredentialDto credentialDto = CredentialDto.builder()
            .credentialId(1)
            .username("user")
            .password("pass")
            .build();
        UserDto userDto = UserDto.builder()
            .userId(1)
            .firstName("updated")
            .email("updated@mail.com")
            .credentialDto(credentialDto) // <--- Agrega esto
            .build();
        User user = User.builder()
            .userId(1)
            .firstName("updated")
            .email("updated@mail.com")
            .credential(
                com.selimhorri.app.domain.Credential.builder()
                    .credentialId(1)
                    .username("user")
                    .password("pass")
                    .build()
            )
            .build();
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto result = userService.update(userDto);
        assertEquals("updated", result.getFirstName());
    }

    @Test
    void testFindAllUsers() {
        User user = User.builder()
            .userId(1)
            .firstName("a")
            .email("a@mail.com")
            .credential(
                com.selimhorri.app.domain.Credential.builder()
                    .credentialId(1)
                    .username("user")
                    .password("pass")
                    .build()
            )
            .build();
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDto> users = userService.findAll();
        assertFalse(users.isEmpty());
        assertEquals("a", users.get(0).getFirstName());
    }
}
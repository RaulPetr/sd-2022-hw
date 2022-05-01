package com.example.bookstore.user;

import com.example.bookstore.BaseControllerTest;
import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.security.dto.MessageResponse;
import com.example.bookstore.user.model.dto.UserDTO;
import com.example.bookstore.user.model.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.bookstore.TestCreationFactory.*;
import static com.example.bookstore.UrlMapping.USER;
import static com.example.bookstore.UrlMapping.USER_ID_PATH;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class UserControllerTest extends BaseControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void create() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password(encodePassword("password"))
                .build();

        when(userService.create(reqUser)).thenReturn(reqUser);

        ResultActions result = performPostWithRequestBody(USER, reqUser);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User created successfully")));
    }

    @Test
    void edit() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .id(randomLong())
                .username("username2")
                .email("email2@email.com")
                .password(encodePassword("password2"))
                .build();
        when(userService.create(reqUser)).thenReturn(reqUser);

        reqUser.setUsername(randomString());
        when(userService.edit(reqUser.getId(),reqUser)).thenReturn(reqUser);
        when(userService.findById(reqUser.getId())).thenReturn(reqUser);

        ResultActions result2 = performPatchWithRequestBodyAndPathVariables(USER+USER_ID_PATH, reqUser,reqUser.getId());
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User edited successfully")));
    }

    @Test
    void delete() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .id(1L)
                .username("username3")
                .email("email3@email.com")
                .password(encodePassword("password3"))
                .build();

        when(userService.create(reqUser)).thenReturn(reqUser);

        doNothing().when(userService).delete(reqUser.getId());

        ResultActions result2 = performDeleteWithPathVariable(USER+USER_ID_PATH, reqUser.getId());
        result2.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("User deleted successfully")));
        verify(userService, times(1)).delete(reqUser.getId());
    }
}

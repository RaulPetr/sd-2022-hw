package com.example.bookstore;

import com.example.bookstore.book.BookRepository;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.security.AuthService;
import com.example.bookstore.security.dto.SignupRequest;
import com.example.bookstore.user.RoleRepository;
import com.example.bookstore.user.UserRepository;
import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final BookRepository bookRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            bookRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("andrei@email.com")
                    .username("andrei")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex@email.com")
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of("EMPLOYEE"))
                    .build());
            bookRepository.save(Book.builder()
                    .title("aass")
                    .author("ads")
                    .genre("efwef")
                    .price(33)
                    .quantity(22)
                    .build());
            bookRepository.save(Book.builder()
                    .title("aasadss")
                    .author("aasdsads")
                    .genre("efaawef")
                    .price(323)
                    .quantity(222)
                    .build());
        }
    }
}
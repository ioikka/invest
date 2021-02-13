package io.ikka.invest.controller;

import io.ikka.invest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.invest.openapi.models.user.AccountsList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UsersController {
    private final UserService userService;

    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountsList> find() {
        return ResponseEntity.ok(userService.getAccounts());
    }
}

package io.ikka.invest.service.impl;

import io.ikka.invest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.invest.openapi.UserContext;
import ru.tinkoff.invest.openapi.models.user.AccountsList;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserServiceImpl implements UserService {
    private final UserContext userContext;

    @Override
    @SneakyThrows
    public AccountsList getAccounts() {
        return userContext.getAccounts().get();
    }
}

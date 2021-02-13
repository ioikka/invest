package io.ikka.invest.service;

import lombok.SneakyThrows;
import ru.tinkoff.invest.openapi.models.user.AccountsList;

public interface UserService {
    @SneakyThrows
    AccountsList getAccounts();
}

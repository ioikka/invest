package io.ikka.invest.service;

import io.ikka.invest.dto.ClosedPosition;
import lombok.SneakyThrows;
import ru.tinkoff.invest.openapi.models.operations.OperationsList;

import java.util.List;

public interface OperationsService {
    @SneakyThrows
    OperationsList getOperations();

    List<ClosedPosition> getClosedPositions();
}

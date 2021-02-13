package io.ikka.invest.service;

import io.ikka.invest.model.InstrumentEntity;

import java.util.List;

public interface InstrumentsService {
    List<InstrumentEntity> getAll();

    InstrumentEntity save(InstrumentEntity entity);

    List<InstrumentEntity> findByTicker(String ticker);

    List<InstrumentEntity> findByFigi(String figi);

}

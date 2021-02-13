package io.ikka.invest.service.impl;

import io.ikka.invest.model.InstrumentEntity;
import io.ikka.invest.repositories.InstrumentsRepository;
import io.ikka.invest.service.InstrumentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class InstrumentsServiceImpl implements InstrumentsService {
    private final InstrumentsRepository repository;

    @Override
    public List<InstrumentEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public InstrumentEntity save(InstrumentEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<InstrumentEntity> findByTicker(String ticker) {
        return repository.findByTicker(ticker);
    }

    @Override
    public List<InstrumentEntity> findByFigi(String figi) {
        return repository.findByFigi(figi);
    }
}

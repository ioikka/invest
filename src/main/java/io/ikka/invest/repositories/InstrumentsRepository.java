package io.ikka.invest.repositories;

import io.ikka.invest.model.InstrumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentsRepository extends JpaRepository<InstrumentEntity, Long> {
    List<InstrumentEntity> findByTicker(String ticker);

    List<InstrumentEntity> findByFigi(String figi);
}

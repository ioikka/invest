package io.ikka.invest.service

import io.ikka.invest.model.InstrumentEntity

interface KInstrumentsService {
    fun getAll(): List<InstrumentEntity?>?

    fun save(entity: InstrumentEntity?): InstrumentEntity?

    fun findByTicker(ticker: String?): List<InstrumentEntity?>?
    fun findByFigi(figi: String?): List<InstrumentEntity?>?
}

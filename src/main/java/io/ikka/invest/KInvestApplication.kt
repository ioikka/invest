package io.ikka.invest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication
open class KInvestApplication

fun main(args: Array<String>) {
    runApplication<KInvestApplication>(*args)
}

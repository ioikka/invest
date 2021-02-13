package io.ikka.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response<T> {
    private T payload;
    private String trackingId;
    /**
     * example 2020-11-30T21:38:07.735+03:00
     */
    private OffsetDateTime time;
    private StatusType status;
}

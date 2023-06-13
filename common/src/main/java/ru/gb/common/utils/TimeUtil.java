package ru.gb.common.utils;

import org.springframework.stereotype.Component;
import ru.gb.common.constants.Constant;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class TimeUtil {
    Constant constant;

    public LocalDateTime currentTime() {
        return Instant.ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.of(constant.SERVER_TIME_ZONE)).toLocalDateTime();
    }
}

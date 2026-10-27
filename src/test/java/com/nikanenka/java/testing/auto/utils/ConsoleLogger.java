package com.nikanenka.java.testing.auto.utils;

import com.codeborne.selenide.logevents.LogEventListener;
import com.codeborne.selenide.logevents.LogEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleLogger implements LogEventListener {
    @Override
    public void beforeEvent(LogEvent currentLogEvent) {
        log.info("[SELENIDE START] Action: {} {}",
                currentLogEvent.getElement(),
                currentLogEvent.getSubject());
    }

    @Override
    public void afterEvent(LogEvent currentLogEvent) {
        log.info("[SELENIDE DONE] Action: {} | Time spent: {}ms",
                currentLogEvent.getStatus(),
                currentLogEvent.getDuration());
    }
}

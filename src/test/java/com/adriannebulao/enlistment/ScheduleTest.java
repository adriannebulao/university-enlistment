package com.adriannebulao.enlistment;


import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Collections;

import static com.adriannebulao.enlistment.Days.MTH;
import static org.junit.jupiter.api.Assertions.*;


public class ScheduleTest {
    @Test
    void test_valid_period_30_minute_increment() {
        assertDoesNotThrow(() -> new Schedule(MTH, LocalTime.of(9, 0), LocalTime.of(9, 30)));
    }

    @Test
    void test_invalid_period_30_minute_increment() {
        assertThrows(NotThirtyIncrementException.class, () -> new Schedule(MTH, LocalTime.of(9, 0), LocalTime.of(9, 40)));
    }

    @Test
    void test_invalid_period_end_time_after_range() {
        assertThrows(InvalidSchedulePeriodException.class, () -> new Schedule(MTH, LocalTime.of(17, 0), LocalTime.of(18, 0)));
    }

    @Test
    void test_invalid_period_start_time_before_range() {
        assertThrows(InvalidSchedulePeriodException.class, () -> new Schedule(MTH, LocalTime.of(7, 0), LocalTime.of(9, 30)));
    }

    @Test
    void test_end_time_before_start_time() {
        assertThrows(InvalidEndPeriodException.class, () -> new Schedule(MTH, LocalTime.of(10, 0), LocalTime.of(9, 30)));
    }

    @Test
    void test_end_time_on_start_time() {
        assertThrows(InvalidEndPeriodException.class, () -> new Schedule(MTH, LocalTime.of(10, 0), LocalTime.of(10, 0)));
    }
}

package com.adriannebulao.enlistment;


import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Collections;

import static com.adriannebulao.enlistment.Days.MTH;
import static org.junit.jupiter.api.Assertions.*;


public class ScheduleTest {
    static Schedule defaultSchedule() {
        {
            return new Schedule(MTH, LocalTime.of(9, 0),  LocalTime.of(10, 0));
        }
    }

    static Subject defaultSubject() {
        return new Subject("Math", 3, Collections.emptySet(), false);
    }

    static Instructor defeaultInstructor() {
        return new Instructor("I1");
    }

    static Room defaultRoom() {
        return new Room("Room101", 30);
    }

    static Section defaultSection() {
        return new Section("S101", defaultSchedule(), defaultSubject(), defaultRoom(), defeaultInstructor());
    }



    @Test
    void test_valid_period_duration_30_minutes() {
        assertDoesNotThrow(() -> new Schedule(MTH, LocalTime.of(9, 0), LocalTime.of(9, 30)));
    }

    @Test
    void test_invalid_period_end_time_after_range() {
        assertThrows(InvalidSchedulePeriodException.class, () -> new Schedule(MTH, LocalTime.of(17, 0), LocalTime.of(18, 0)));
    }


    @Test
    void test_invalid_period_start_time_before_range() {
        assertThrows(InvalidSchedulePeriodException.class, () -> new Schedule(MTH, LocalTime.of(7, 0), LocalTime.of(9, 30)));
    }





}

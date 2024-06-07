package com.adriannebulao.enlistment;

import org.junit.jupiter.api.Test;
import static com.adriannebulao.enlistment.Days.*;
import static com.adriannebulao.enlistment.Period.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    static Room defaultRoom() {
        return new Room("A102", 30);
    }

    static Schedule defaultSchedule1() {
        return new Schedule(MTH, H0830);
    }

    static Schedule defaultSchedule2() {
        return new Schedule(TF, H1000);
    }

    static Subject defaultSubject() {
        return new Subject("Math", 3, Collections.emptyList(), false);
    }

    static Instructor defaultInstructor() {
        return new Instructor("Dr. Smith");
    }

    static Section defaultSection1() {
        return new Section("SEC1", defaultSchedule1(), defaultSubject(), defaultRoom(), defaultInstructor());
    }

    static Section defaultSection2() {
        return new Section("SEC2", defaultSchedule2(), defaultSubject(), defaultRoom(), defaultInstructor());
    }

    @Test
    void room_has_alphanumeric_name() {
        Room room = defaultRoom();
        assertEquals("A102", room.getRoomName());
    }

    @Test
    void room_has_invalid_alphanumeric_name() {
        assertThrows(IllegalArgumentException.class, () -> new Room("%!@#", 20));
    }


    @Test
    void room_has_capacity() {
        Room room = defaultRoom();
        assertEquals(30, room.getRoomCapacity());
    }

    @Test
    void room_has_non_zero_capacity() {
        assertThrows(IllegalArgumentException.class, () -> new Room("A102", -5));
    }

    @Test
    void test_room_conflict_with_overlapping_schedules() {
        Section section1 = defaultSection1();
        Section section2 = new Section("SEC2", defaultSchedule1(), defaultSubject(), defaultRoom(), defaultInstructor());

        assertThrows(RoomConflictException.class, () -> section1.checkForConflictRoom(section2));
    }

    @Test
    void test_no_room_conflict_with_different_schedules() {
        Section section1 = defaultSection1();
        Section section2 = defaultSection2();

        assertDoesNotThrow(() -> section1.checkForConflictRoom(section2));
    }

    @Test
    void test_room_conflict_with_different_subjects() {

    }
}

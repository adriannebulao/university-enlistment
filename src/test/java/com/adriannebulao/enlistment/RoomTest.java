package com.adriannebulao.enlistment;

import org.junit.jupiter.api.Test;
import static com.adriannebulao.enlistment.Days.*;
import static com.adriannebulao.enlistment.Period.*;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    //Given
    @Test
    void room_has_alphanumeric_name() {
        Room room = new Room("A102", 20);
        assertEquals("A102", room.getRoomName());
    }
    //When
    @Test
    void room_has_capacity() {
        Room room = new Room("A102", 20);
        assertEquals(20, room.getRoomCapacity());
    }
    //Then
    @Test
    void room_has_non_zero_capacity() {
        assertThrows(IllegalArgumentException.class, () -> new Room("A102", -5));
    }

    @Test
    void test_room_conflict_with_overlapping_schedules() {
        Room room = new Room("A102", 30);
        Schedule schedule = new Schedule(MTH, H0830);
        Subject subject = new Subject("Math", 3, new HashSet<>(), false);
        Instructor instructor = new Instructor("Dr. Smith");
        Section section1 = new Section("SEC1", schedule, subject, room, instructor);
        Section section2 = new Section("SEC2", schedule, subject, room, instructor);

        assertThrows(RoomConflictException.class, () -> section1.checkRoomConflict(section2));
    }

    @Test
    void test_no_room_conflict_with_different_schedules() {
        Room room = new Room("A102", 30);
        Schedule schedule1 = new Schedule(MTH, H0830);
        Schedule schedule2 = new Schedule(TF, H1000);
        Subject subject = new Subject("Math", 3, new HashSet<>(), false);
        Instructor instructor = new Instructor("Dr. Smith");

        Section section1 = new Section("SEC1", schedule1, subject, room, instructor);
        Section section2 = new Section("SEC2", schedule2, subject, room, instructor);

        assertDoesNotThrow(() -> section1.checkRoomConflict(section2));
    }

}

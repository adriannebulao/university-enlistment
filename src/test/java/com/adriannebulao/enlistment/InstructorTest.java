package com.adriannebulao.enlistment;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static com.adriannebulao.enlistment.Days.*;
import static com.adriannebulao.enlistment.Period.*;
import static org.junit.jupiter.api.Assertions.*;

public class InstructorTest {

    @Test
    void section_has_instructor() {
        Schedule schedule = new Schedule(MTH, H0830);
        Room room = new Room("A102", 30);
        Subject subject = new Subject("Math", 3, new HashSet<>(), false);

        Instructor instructor = new Instructor("I101");

        Section section = new Section("S101", schedule, subject, room, instructor);
        assertNotNull(section);
    }

    @Test
    void section_has_null_instructor() {
        Schedule schedule = new Schedule(MTH, H0830);
        Room room = new Room("A102", 30);
        Subject subject = new Subject("Math", 3, new HashSet<>(), false);

        assertThrows(NullPointerException.class, () -> {
            new Section("S101", schedule, subject, room, null);
        });
    }
}

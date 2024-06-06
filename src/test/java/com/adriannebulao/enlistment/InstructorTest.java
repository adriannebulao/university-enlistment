package com.adriannebulao.enlistment;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static com.adriannebulao.enlistment.Days.*;
import static com.adriannebulao.enlistment.Period.*;
import static org.junit.jupiter.api.Assertions.*;

public class InstructorTest {

    @Test
    void instructor_assigned_to_section() {
        Instructor instructor = new Instructor("I101");

        Schedule schedule = new Schedule(MTH, H0830);
        Subject subject = new Subject("Math", 3, new HashSet<>(), false);
        Room room = new Room("A102", 30);
        Section section = new Section("S101", schedule, subject, room, instructor);

        instructor.assignSection(section);

        assertEquals(instructor, section.getInstructor());
    }

    @Test
    void section_has_instructor() {
        Schedule schedule = new Schedule(MTH, H0830);
        Room room = new Room("room1", 30);
        Subject subject = new Subject("subject1", 3, new HashSet<>(), false);

        Instructor instructor = new Instructor("instructor1");

        Section section = new Section("section1", schedule, subject, room, instructor);
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

    @Test
    void instructor_has_schedule_conflict() {
        Instructor instructor = new Instructor("I101");
        Schedule schedule1 = new Schedule(MTH, H0830);
        Schedule schedule2 = new Schedule(MTH, H0830);

        Section section1 = new Section("S101", schedule1, null, null, instructor);
        Section section2 = new Section("S102", schedule2, null, null, instructor);

        instructor.assignSection(section1);
        instructor.assignSection(section2);

        assertTrue(instructor.hasScheduleConflict(section1));
    }

    @Test
    void instructor_has_no_schedule_conflict() {
        Instructor instructor = new Instructor("I101");

        Schedule schedule1 = new Schedule(TF, H1420);
        Schedule schedule2 = new Schedule(MTH, H1130);

        Section section1 = new Section("S101", schedule1, null, null, instructor);
        Section section2 = new Section("S102", schedule2, null, null, instructor);

        instructor.assignSection(section1);
        instructor.assignSection(section2);

        assertFalse(instructor.hasScheduleConflict(section1));
        assertFalse(instructor.hasScheduleConflict(section2));
    }

}

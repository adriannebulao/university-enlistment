package com.adriannebulao.enlistment;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;

import static com.adriannebulao.enlistment.Days.*;
import static org.junit.jupiter.api.Assertions.*;

public class InstructorTest {

    static Instructor defaultInstructor() {
        return new Instructor("I101");
    }

    static Schedule defaultSchedule1() {
        return new Schedule(MTH, LocalTime.of(9, 0),  LocalTime.of(11, 0));
    }

    static Schedule defaultSchedule2() {
       return new Schedule(MTH, LocalTime.of(9, 0),  LocalTime.of(11, 0));
    }

    static Subject defaultSubject1() {
        return new Subject("subject1", 3, Collections.emptyList(), false);
    }

    static Subject defaultSubject2() {
        return new Subject("subject2", 3, Collections.emptyList(), false);
    }

    static Room defaultRoom1() {
        return new Room("A102", 30);
    }

    static Room defaultRoom2() {
        return new Room("B102", 30);
    }

    static Section defaultSection1() {
        return new Section("S101", defaultSchedule1(), defaultSubject1(), defaultRoom1(), defaultInstructor());
    }

    static Section defaultSection2() {
        return new Section("S102", defaultSchedule2(), defaultSubject2(), defaultRoom2(), defaultInstructor());
    }

    @Test
    void instructor_assigned_to_section() {
        Instructor instructor = defaultInstructor();
        Section section = defaultSection1();

        instructor.assignSection(section);

        assertEquals(instructor, section.getInstructor());
    }

    @Test
    void section_has_instructor() {
        Section section = defaultSection1();

        assertNotNull(section);
    }

    @Test
    void section_has_null_instructor() {
        assertThrows(NullPointerException.class, () -> {
            new Section("S101", defaultSchedule1(), defaultSubject1(), defaultRoom1(), null);
        });
    }

    @Test
    void instructor_has_schedule_conflict() {
        Instructor instructor = defaultInstructor();

        Section section1 = defaultSection1();
        Section section2 = new Section("S102", defaultSchedule1(), defaultSubject2(), defaultRoom2(), instructor);

        instructor.assignSection(section1);
        instructor.assignSection(section2);

        assertTrue(instructor.hasScheduleConflict(section1));
    }

    @Test
    void instructor_has_no_schedule_conflict() {
        Instructor instructor = defaultInstructor();

        Section section1 = defaultSection1();
        Section section2 = defaultSection2();

        instructor.assignSection(section1);
        instructor.assignSection(section2);

        assertFalse(instructor.hasScheduleConflict(section1));
        assertFalse(instructor.hasScheduleConflict(section2));
    }

}

package com.adriannebulao.enlistment;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class SectionTest {

    static Schedule defaultSchedule() {
        return new Schedule(Days.MTH, Period.H0830);
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
    void constructor_valid_arguments() {
        Section section = defaultSection();

        assertNotNull(section);
        assertEquals(defaultSection(), section);
    }

    @Test
    void constructor_invalid_arguments() {
        assertThrows(NullPointerException.class, () -> new Section(null, null, null, null, null));
        assertThrows(IllegalArgumentException.class, () -> new Section("S-101", defaultSchedule(), defaultSubject(), defaultRoom(), defeaultInstructor()));
        assertThrows(NullPointerException.class, () -> new Section("S101", null, defaultSubject(), defaultRoom(), defeaultInstructor()));
        assertThrows(NullPointerException.class, () -> new Section("S102", defaultSchedule(), defaultSubject(), defaultRoom(), null));
    }

    @Test
    void section_equals_and_hashCode() {
        Section section1 = defaultSection();
        Section section2 = defaultSection();

        assertEquals(section1, section2);
        assertEquals(section1.hashCode(), section2.hashCode());

        assertNotEquals(section1, null);
    }

    @Test
    void to_string_test() {
        Section section = new Section("S101", defaultSchedule(), defaultSubject(), defaultRoom(), defeaultInstructor());
        assertEquals("S101", section.toString());
    }
}

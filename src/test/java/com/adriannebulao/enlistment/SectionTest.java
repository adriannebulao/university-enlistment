package com.adriannebulao.enlistment;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class SectionTest {

    private static final String SECTION_ID = "S101";
    private static final Schedule SCHEDULE = new Schedule(Days.MTH, Period.H0830);
    private static final Instructor INSTRUCTOR = new Instructor("I1");
    private static final Subject SUBJECT = new Subject("Math", 3, Collections.emptySet(), false);
    private static final Room ROOM = new Room("Room101", 30);

    @Test
    void constructor_valid_arguments() {
        Section section = new Section(SECTION_ID, SCHEDULE, SUBJECT, ROOM, INSTRUCTOR);

        assertNotNull(section);
        assertEquals(new Section(SECTION_ID, SCHEDULE, SUBJECT, ROOM, INSTRUCTOR), section);
    }

    @Test
    void constructor_invalid_arguments() {
        assertThrows(NullPointerException.class, () -> new Section(null, null, null, null, null));
        assertThrows(IllegalArgumentException.class, () -> new Section("S-101", SCHEDULE, SUBJECT, ROOM, INSTRUCTOR));
        assertThrows(NullPointerException.class, () -> new Section(SECTION_ID, null, SUBJECT, ROOM, INSTRUCTOR));
        assertThrows(NullPointerException.class, () -> new Section(SECTION_ID, SCHEDULE, SUBJECT, ROOM, null));
    }

    @Test
    void section_equals_and_hashCode() {
        Section section1 = new Section(SECTION_ID, SCHEDULE, SUBJECT, ROOM, INSTRUCTOR);
        Section section2 = new Section(SECTION_ID, SCHEDULE, SUBJECT, ROOM, INSTRUCTOR);

        assertEquals(section1, section2);
        assertEquals(section1.hashCode(), section2.hashCode());

        assertNotEquals(section1, null);
    }

    @Test
    void to_string_test() {
        Section section = new Section(SECTION_ID, SCHEDULE, SUBJECT, ROOM, INSTRUCTOR);
        assertEquals(SECTION_ID, section.toString());
    }
}

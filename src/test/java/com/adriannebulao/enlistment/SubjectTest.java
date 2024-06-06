package com.adriannebulao.enlistment;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SubjectTest {

    @Test
    void constructor_valid_arguments() {
        Subject subject = new Subject("123abc", 5, Collections.emptyList(), true);

        assertEquals(new Subject("123abc", 5, Collections.emptyList(), true), subject);
    }

    @Test
    void constructor_invalid_arguments() {
        assertThrows(NullPointerException.class, () -> new Subject(null, 5, Collections.emptyList(), false));
        assertThrows(IllegalArgumentException.class, () -> new Subject("123-abc", 5, Collections.emptyList(), false));
        assertThrows(IllegalArgumentException.class, () -> new Subject("123abc", 6, Collections.emptyList(), false));
        assertThrows(IllegalArgumentException.class, () -> new Subject("123abc", 0, Collections.emptyList(), false));
        assertThrows(NullPointerException.class, () -> new Subject("123abc", 5, null, false));
        assertThrows(NullPointerException.class, () -> new Subject("123abc", 5, Collections.emptyList(), (Boolean) null));
    }

    @Test
    void constructor_prerequisite_subjects_contains_null() {
        Collection<Subject> prerequisiteSubjects = new HashSet<>();
        prerequisiteSubjects.add(null);

        assertThrows(IllegalArgumentException.class, () -> new Subject("123abc", 5, prerequisiteSubjects, false));
    }

    @Test
    void to_string_test() {
        Subject subject = new Subject("123abc", 5, Collections.emptyList(), false);

        assertEquals("Subject ID: 123abc", subject.toString());
    }
}

package com.adriannebulao.enlistment;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubjectTest {

    @Test
    void constructor_valid_arguments() {
        Subject subject = new Subject("123abc", 5, Collections.emptyList(), false);

        assertEquals(new Subject("123abc", 5, Collections.emptyList(), false), subject);
    }
}

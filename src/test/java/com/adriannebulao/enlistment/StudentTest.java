package com.adriannebulao.enlistment;

import org.junit.jupiter.api.Test;

import java.util.*;

import static com.adriannebulao.enlistment.Days.*;
import static com.adriannebulao.enlistment.Period.*;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    static final Room F101 = new Room("F101", 2);
    static final Schedule MTH_830 = new Schedule(MTH, H0830);
    static final Schedule TF_1000 = new Schedule(TF, H1000);
    static Student defaultStudent() {
        return new Student(1, Collections.emptyList());
    }

    @Test
    void enlist_2_sections_with_no_conflict() {
        // Given: Student with no sections, 2 sections with no conflict
        Student student = defaultStudent();
        Section sec1 = new Section("A", MTH_830, F101);
        Section sec2 = new Section("B", TF_1000, F101);

        // When: Student enlists in both sections
        student.enlist(sec1);
        student.enlist(sec2);

        // Then: Both sections should be found in the student, and no other sections
        Collection<Section> sections = student.getSections();
        assertAll(
                () -> assertTrue(sections.containsAll(List.of(sec1, sec2))),
                () -> assertEquals(2, sections.size())
        );

    }

    @Test
    void enlist_2_sections_with_schedule_conflict() {
        // Given:A Student with no sections and 2 sections with the same schedule
        Student student = defaultStudent();
        Section sec1 = new Section("A", MTH_830, F101);
        Section sec2 = new Section("A", MTH_830, F101);

        // When: Student enlists in both sections
        student.enlist(sec1);

        // Then: On enlisting in the second question, throw exception
        assertThrows(Exception.class, () -> student.enlist(sec2));
    }
}

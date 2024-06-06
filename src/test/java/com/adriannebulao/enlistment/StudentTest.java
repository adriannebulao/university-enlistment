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
        return new Student(1, Collections.emptyList(), Collections.emptyList());
    }

    static Instructor defaultInstructor() {
        return new Instructor("1");
    }
    @Test
    void enlist_2_sections_with_no_conflict() {
        // Given: Student with no sections, 2 sections with no conflict
        Student student = defaultStudent();
        Subject subj1 = new Subject("123abc", 5, Collections.emptyList(), false);
        Subject subj2 = new Subject("456def", 3, Collections.emptyList(), true);
        Section sec1 = new Section("A", MTH_830, subj1, F101, defaultInstructor());
        Section sec2 = new Section("B", TF_1000, subj2, F101, defaultInstructor());

        // When: Student enlists in both sections
        student.enlist(sec1);
        student.enlist(sec2);

        System.out.println(student.calculateTotalExpenses());

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
        Subject subj1 = new Subject("123abc", 5, Collections.emptyList(), false);
        Subject subj2 = new Subject("456def", 3, Collections.emptyList(), true);
        Section sec1 = new Section("A", MTH_830, subj1, F101, defaultInstructor());
        Section sec2 = new Section("B", MTH_830, subj2, F101, defaultInstructor());

        // When: Student enlists in both sections
        student.enlist(sec1);

        // Then: On enlisting in the second question, throw exception
        assertThrows(Exception.class, () -> student.enlist(sec2));
    }

    @Test
    void enlist_meet_prerequisite_subjects() {
        Subject subj1 = new Subject("123abc", 5, Collections.emptyList(), false);
        Subject subj2 = new Subject("456def", 3, Collections.emptyList(), true);

        Collection<Subject> prerequisiteSubjects = new HashSet<>();
        prerequisiteSubjects.add(subj1);
        prerequisiteSubjects.add(subj2);

        Subject subj3 = new Subject("789ghi", 2, prerequisiteSubjects, true);

        Collection<Subject> takenSubjects = new HashSet<>();
        takenSubjects.add(subj1);
        takenSubjects.add(subj2);

        Student student = new Student(1, Collections.emptyList(), takenSubjects);
        Section section = new Section("A", MTH_830, subj3, F101, defaultInstructor());

        student.enlist(section);
        Collection<Section> sections = student.getSections();

        assertTrue(sections.contains(section));
    }

    @Test
    void enlist_does_not_meet_prerequisite_subjects() {
        Subject subj1 = new Subject("123abc", 5, Collections.emptyList(), false);
        Subject subj2 = new Subject("456def", 3, Collections.emptyList(), true);

        Collection<Subject> prerequisiteSubjects = new HashSet<>();
        prerequisiteSubjects.add(subj1);
        prerequisiteSubjects.add(subj2);

        Subject subj3 = new Subject("789ghi", 2, prerequisiteSubjects, true);

        Collection<Subject> takenSubjects = new HashSet<>();
        takenSubjects.add(subj1);

        Student student = new Student(1, Collections.emptyList(), takenSubjects);
        Section section = new Section("A", MTH_830, subj3, F101, defaultInstructor());

        assertThrows(MissingPrerequisiteSubjectException.class, ()-> student.enlist(section));
    }
}

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

    static Subject defaultSubject1() {
        return new Subject("123abc", 5, Collections.emptyList(), false);
    }

    static Subject defaultSubject2() {
        return new Subject("456def", 3, Collections.emptyList(), true);
    }

    static Section defaultSection1() {
        return new Section("A", MTH_830, defaultSubject1(), F101, defaultInstructor());
    }

    static Section defaultSection2() {
        return new Section("B", TF_1000, defaultSubject2(), F101, defaultInstructor());
    }

    static Instructor defaultInstructor() {
        return new Instructor("1");
    }

    @Test
    void enlist_2_sections_with_no_conflict() {
        // Given: Student with no sections, 2 sections with no conflict
        Student student = defaultStudent();
        Section sec1 = defaultSection1();
        Section sec2 = defaultSection2();

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
        // Given: A Student with no sections and 2 sections with the same schedule
        Student student = defaultStudent();
        Subject subj2 = defaultSubject2();
        Section sec1 = defaultSection1();
        Section sec2 = new Section("B", MTH_830, subj2, F101, defaultInstructor());

        // When: Student enlists in both sections
        student.enlist(sec1);

        // Then: On enlisting in the second question, throw exception
        assertThrows(ScheduleConflictException.class, () -> student.enlist(sec2));
    }

    @Test
    void enlist_meet_prerequisite_subjects() {
        Subject subj1 = defaultSubject1();
        Subject subj2 = defaultSubject2();

        Collection<Subject> prerequisiteSubjects = new HashSet<>();
        prerequisiteSubjects.add(subj1);
        prerequisiteSubjects.add(subj2);

        Subject subj3 = new Subject("789ghi", 2, prerequisiteSubjects, true);

        Collection<Subject> takenSubjects = new HashSet<>();
        takenSubjects.add(subj1);
        takenSubjects.add(subj2);

        Student student = new Student(1, Collections.emptyList(), takenSubjects);
        Section newSection = new Section("A", MTH_830, subj3, F101, defaultInstructor());

        student.enlist(newSection);
        Collection<Section> enlistedSections = student.getSections();

        assertTrue(enlistedSections.contains(newSection));
    }

    @Test
    void enlist_does_not_meet_prerequisite_subjects() {
        Subject subj1 = defaultSubject1();
        Subject subj2 = defaultSubject2();

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

    @Test
    void enlist_cancel_one_section(){
        Section sec1 = defaultSection1();
        Section sec2 = defaultSection2();

        Student student = defaultStudent();

        student.enlist(sec1);
        student.enlist(sec2);

        student.cancelEnlist(sec1);

        Collection<Section> sections = student.getSections();
        assertAll(
                () -> assertTrue(sections.contains(sec2)),
                () -> assertEquals(1, sections.size())
        );
    }

    @Test
    void enlist_section_have_same_subject(){
        Subject subj1 = defaultSubject1();
        Section sec1 = defaultSection1();
        Section sec2 = new Section("B", TF_1000, subj1, F101, defaultInstructor());

        Student student = defaultStudent();
        student.enlist(sec1);

        assertThrows(SectionsHaveSameSubjectException.class, () -> student.enlist(sec2));
    }
}

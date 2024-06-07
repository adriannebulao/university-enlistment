package com.adriannebulao.enlistment;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.*;

import static com.adriannebulao.enlistment.Days.*;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    public static final double UNIT_COST = 2345.67;
    public static final double MISC_FEES = 3456.78;
    public static final double LABORATORY_FEE = 1234.56;
    public static final double VALUE_ADDED_TAX = 0.12;

    static Student defaultStudent() {
        return new Student(1, Collections.emptyList(), Collections.emptyList());
    }

    static Subject defaultSubject1() {
        return new Subject("123abc", 5, Collections.emptyList(), false);
    }

    static Subject defaultSubject2() {
        return new Subject("456def", 3, Collections.emptyList(), true);
    }

    static Room defaultRoom() {
        return new Room("F101", 2);
    }

     static Schedule defaultSchedule1() {
        return new Schedule(MTH, LocalTime.of(5, 0),  LocalTime.of(6, 30));
    }

    static Schedule defaultSchedule2() {
        return new Schedule (TF, LocalTime.of(9, 0),  LocalTime.of(11, 0));
    }

    static Section defaultSection1() {
        return new Section("A", defaultSchedule1(), defaultSubject1(), defaultRoom(), defaultInstructor());
    }

    static Section defaultSection2() {
        return new Section("B", defaultSchedule2(), defaultSubject2(), defaultRoom(), defaultInstructor());
    }

    static Instructor defaultInstructor() {
        return new Instructor("I1");
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
        Section sec2 = new Section("B", defaultSchedule1(), subj2, defaultRoom(), defaultInstructor());

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
        Section newSection = new Section("A", defaultSchedule1(), subj3, defaultRoom(), defaultInstructor());

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
        Section section = new Section("A", defaultSchedule1(), subj3, defaultRoom(), defaultInstructor());

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
        Section sec2 = new Section("B", defaultSchedule2(), subj1, defaultRoom(), defaultInstructor());

        Student student = defaultStudent();
        student.enlist(sec1);

        assertThrows(SectionsHaveSameSubjectException.class, () -> student.enlist(sec2));
    }

    @Test
    void student_assessment_total_payment(){
        Section sec1 = defaultSection1();
        Section sec2 = defaultSection2();

        Student student = defaultStudent();

        student.enlist(sec1);
        student.enlist(sec2);

        double sec1_value = (5 * UNIT_COST) + MISC_FEES;
        double addedTax1 = VALUE_ADDED_TAX * sec1_value;
        sec1_value += addedTax1;

        double sec2_value = (3 * UNIT_COST) + MISC_FEES + LABORATORY_FEE;
        double addedTax2 = VALUE_ADDED_TAX * sec2_value;
        sec2_value += addedTax2;

        double totalCost = sec1_value + sec2_value;
        totalCost = new BigDecimal(totalCost).setScale(2, RoundingMode.HALF_UP).doubleValue();

        assertEquals(totalCost, student.requestAssessment());
    }

}

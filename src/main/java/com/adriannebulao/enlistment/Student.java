package com.adriannebulao.enlistment;

import static java.util.Objects.*;
import static org.apache.commons.lang3.Validate.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

class Student {
    private final int studentNumber;
    private final Collection<Section> sections = new HashSet<>();
    private final Collection<Subject> takenSubjects = new HashSet<>();

    Student(int studentNumber, Collection<Section> sections, Collection<Subject> takenSubjects) {
        isTrue(studentNumber >= 0, "studentNumber should be non-negative, was: " + studentNumber);
        this.studentNumber = studentNumber;
        requireNonNull(sections);
        this.sections.addAll(sections);
        noNullElements(this.sections, "sections contain one or more null elements");
        this.takenSubjects.addAll(takenSubjects);
    }

    void enlist(Section newSection) {
        requireNonNull(newSection, "section should not be null");
        sections.forEach(currSection -> currSection.checkForConflictSchedule(newSection));
        sections.forEach(currSection -> currSection.checkForConflictSubject(newSection));
        newSection.checkSubjectPrerequisites(takenSubjects);
        newSection.addEnlistNumber();
        sections.add(newSection);
    }

    void cancelEnlist(Section newSection){
        requireNonNull(newSection, "section should not be null");
        isTrue(sections.contains(newSection) , "section is not enlisted");
        sections.remove(newSection);
        newSection.removeEnlistNumber();

    }

    double requestAssessment() {
        double totalCost = 0;
        for (Section section : sections) {
            totalCost += section.getAmountToPay();
        }
        return new BigDecimal(totalCost).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    Collection<Section> getSections() {
        return new ArrayList<>(sections);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentNumber == student.studentNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentNumber);
    }

    @Override
    public String toString() {
        return "Student #: " + studentNumber;
    }

}

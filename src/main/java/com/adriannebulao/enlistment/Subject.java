package com.adriannebulao.enlistment;

import static java.util.Objects.*;
import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

class Subject {
    private final String subjectId;
    private final int units;
    private final Collection<Subject> prerequisiteSubjects = new HashSet<>();
    private final boolean isLaboratorySubject;

    private static final double UNIT_COST = 2345.67;
    private static final double LABORATORY_FEE = 1234.56;
    private static final double MISC_FEES = 3456.78;
    private static final double VALUE_ADDED_TAX = 0.12;

    public Subject(String subjectId, int units,
                   Collection<Subject> prerequisiteSubjects, boolean isLaboratorySubject) {
        requireNonNull(subjectId, "subject id must not be null");
        isTrue(isAlphanumeric(subjectId), "subject id must contain alphanumeric characters only");
        this.subjectId = subjectId;

        isTrue(units >= 1 && units <= 5, "subject units must be between 1 and 5 was: " + units);
        this.units = units;

        requireNonNull(prerequisiteSubjects);
        this.prerequisiteSubjects.addAll(prerequisiteSubjects);
        noNullElements(this.prerequisiteSubjects, "prerequisite subjects contain one or more null elements");

        this.isLaboratorySubject = isLaboratorySubject;
    }

    double getSubjectAmountToPay() {
        double cost = units * UNIT_COST;
        if (this.isLaboratorySubject) {
            cost += LABORATORY_FEE;
        }
        cost += MISC_FEES;
        double addedTax = VALUE_ADDED_TAX * cost;
        cost += addedTax;
        return cost;
    }

    boolean hasTakenPrerequisiteSubjects(Collection<Subject> takenSubjects) {
        return takenSubjects.containsAll(prerequisiteSubjects);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return units == subject.units && isLaboratorySubject == subject.isLaboratorySubject && Objects.equals(subjectId, subject.subjectId) && Objects.equals(prerequisiteSubjects, subject.prerequisiteSubjects);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(subjectId);
    }

    @Override
    public String toString() {
        return "Subject ID: " + subjectId;
    }
}

package com.adriannebulao.enlistment;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

class Instructor {
    private final String instructorId;
    private final Collection<Section> sections = new HashSet<>();

    Instructor(String instructorId) {
        requireNonNull(instructorId, "instructor id must not be null");
        this.instructorId = instructorId;
    }

    public void assignSection(Section section) {
        requireNonNull(section, "section must not be null");
        sections.add(section);
    }

    public boolean hasScheduleConflict(Section newSection) {
        requireNonNull(newSection, "newSection must not be null");
        for (Section section : sections) {
            if (!section.equals(newSection) && newSection.getSchedule().equals(section.getSchedule())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instructor that = (Instructor) o;
        return Objects.equals(instructorId, that.instructorId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(instructorId);
    }

    @Override
    public String toString() {
        return instructorId;
    }

}

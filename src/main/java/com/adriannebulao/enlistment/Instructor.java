package com.adriannebulao.enlistment;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

class Instructor {
    private final String instructorId;
    private final Collection<Section> sections = new HashSet<>();

    Instructor(String instructorId) {
        this.instructorId = instructorId;
    }

    public void assignSection(Section section) {
        sections.add(section);
    }

    public boolean hasScheduleConflict(Section newSection) {
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

package com.adriannebulao.enlistment;

import static java.util.Objects.*;
import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.Objects;

class Section {
    private final String sectionId;
    private final Schedule schedule;
    private final Instructor instructor;
    private final Subject subject;
    private final Room room;
    private int enlistmentNumber;

    Section(String sectionId, Schedule schedule, Subject subject, Room room, Instructor instructor) {
        requireNonNull(sectionId);
        notNull(schedule, "schedule must not be null");
        isTrue(isAlphanumeric(sectionId), "sectionId must be alphanumeric, was: " + sectionId);
        requireNonNull(instructor, "instructor must not be null");

        this.sectionId = sectionId;
        this.schedule = schedule;
        this.instructor = instructor;
        this.subject = subject;
        this.room = room;
    }

    void addEnlistNumber(){
        if(enlistmentNumber + 1 > room.getRoomCapacity()){
            throw new IllegalArgumentException("exceeding room");
        }
        enlistmentNumber += 1;
    }

    void removeEnlistNumber(){
        enlistmentNumber -= 1;
    }
    void checkForConflict(Section other) {
        requireNonNull(other);
        if (this.schedule.equals(other.schedule)) {
            throw new ScheduleConflictException("this section " + this + " has schedule conflict with section " + other + " at schedule " + schedule);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return Objects.equals(sectionId, section.sectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sectionId);
    }

    @Override
    public String toString() {
        return sectionId;
    }
}

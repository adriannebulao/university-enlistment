package com.adriannebulao.enlistment;

import static java.util.Objects.*;
import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.Collection;
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
        isTrue(isAlphanumeric(sectionId), "sectionId must be alphanumeric, was: " + sectionId);
        this.sectionId = sectionId;

        requireNonNull(schedule, "schedule must not be null");
        this.schedule = schedule;

        requireNonNull(instructor, "instructor must not be null");
        this.instructor = instructor;

        requireNonNull(subject, "subject must not be null");
        this.subject = subject;

        requireNonNull(room, "room must not be null");
        this.room = room;
    }

    Schedule getSchedule() {
        return schedule;
    }

    public Instructor getInstructor() {
        return instructor;
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

    double calculateExpense(){
        return(subject.requestAssessment());
    }

    void checkForConflict(Section other) {
        requireNonNull(other);
        if (this.schedule.equals(other.schedule)) {
            throw new ScheduleConflictException("this section " + this + " has schedule conflict with section " + other + " at schedule " + schedule);
        }
    }

    void checkRoomConflict(Section other) {
        requireNonNull(other);
        if (this.room.equals(other.room) && this.schedule.equals(other.schedule)) {
            throw new RoomConflictException("both sections have overlapping schedules in the same room.");
        }
    }

    void checkForConflictSubject(Section otherSubject){
        requireNonNull(otherSubject);
        if(this.subject.equals(otherSubject.subject)){
            throw new SectionsHaveSameSubjectException("cannot have same subject");
        }
    }

    void checkSubjectPrerequisites(Collection<Subject> takenSubjects) {
        requireNonNull(takenSubjects);
        if (!subject.hasTakenPrerequisiteSubjects(takenSubjects)) {
            throw new MissingPrerequisiteSubjectException("not all prerequisite subjects were taken");
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

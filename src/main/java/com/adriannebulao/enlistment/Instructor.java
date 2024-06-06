package com.adriannebulao.enlistment;

import java.util.Objects;

class Instructor {
    private final String instructorId;

    Instructor(String instructorId) {
        this.instructorId = instructorId;
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

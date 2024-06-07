package com.adriannebulao.enlistment;

import static java.util.Objects.*;
import java.time.LocalTime;
import java.util.Objects;
import java.time.format.DateTimeFormatter;


class Schedule {

    private final Days days;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mma");


    Schedule(Days days, LocalTime startTime, LocalTime endTime) {
        requireNonNull(days);
        requireNonNull(startTime);
        requireNonNull(endTime);

        if(startTime.getMinute() != 30 && startTime.getMinute() != 0 && endTime.getMinute() != 30 && endTime.getMinute()!= 0){
            throw new NotThirtyIncrementException("invalid start and end time");
        }

        if(startTime.getMinute() != 30 && startTime.getMinute() != 0){
            throw new NotThirtyIncrementException("invalid start time");
        };

        if(endTime.getMinute() != 30 && endTime.getMinute()!= 0){
            throw new NotThirtyIncrementException("invalid end time");
        };

        if(startTime.isAfter(LocalTime.of(17, 30)) || endTime.isAfter(LocalTime.of(17, 30))){
            throw new InvalidSchedulePeriodException("time cannot go beyond 5:30 PM");
        };

        if(startTime.isBefore(LocalTime.of(8, 30)) || endTime.isBefore(LocalTime.of(8, 30))){
            throw new InvalidSchedulePeriodException("time cannot go earlier than 8:30 AM");
        };

        if(startTime.isAfter(endTime)){
            throw new InvalidEndPeriodException("endTime can not be on or before startTime");
        }

        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    boolean isOverlapping(Schedule other) {
        requireNonNull(other, "other section must not be null");
        return ((other.startTime.isAfter(startTime) || other.startTime.equals(startTime))
                && (other.startTime.isBefore(endTime) || other.startTime.equals(endTime)))
                || ((other.endTime.isAfter(startTime) || other.endTime.equals(startTime))
                && (other.endTime.isBefore(endTime) || other.endTime.equals(endTime)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return days == schedule.days && Objects.equals(startTime, schedule.startTime) && Objects.equals(endTime, schedule.endTime);
    }

    @Override
    public int hashCode() {
        return hash(days, startTime, endTime);
    }

    @Override
    public String toString() {
        return "Days: " +  days + " | " + startTime.format(TIME_FORMATTER)  + " - " + endTime.format(TIME_FORMATTER);
    }

}

enum Days { MTH, TF, WS }



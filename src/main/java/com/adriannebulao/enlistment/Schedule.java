package com.adriannebulao.enlistment;

import static java.util.Objects.*;
import java.time.LocalTime;
import java.util.Objects;

class Schedule {

    private final Days days;
    private final LocalTime startTime;
    private final LocalTime endTime;

    Schedule(Days days, LocalTime startTime, LocalTime endTime) {
        requireNonNull(days);
        requireNonNull(startTime);
        requireNonNull(endTime);

        if(startTime.getMinute() != 30 && startTime.getMinute() != 0 && endTime.getMinute() != 30 && endTime.getMinute()!= 0){
            throw new IllegalArgumentException("invalid start and end time");
        }

        if(startTime.getMinute() != 30 && startTime.getMinute() != 0){
            throw new IllegalArgumentException("invalid start time");
        };

        if(endTime.getMinute() != 30 && endTime.getMinute()!= 0){
            throw new IllegalArgumentException("invalid end time");
        };

        //time  beyond 5:30 PM & time earlier than 8:30 AM
        if(startTime.isAfter(LocalTime.of(17, 30)) || endTime.isAfter(LocalTime.of(17, 30))){
            throw new IllegalArgumentException("time cannot go beyond 5:30 PM");
        };

        if(startTime.isBefore(LocalTime.of(8, 30)) || endTime.isBefore(LocalTime.of(8, 30))){
            throw new IllegalArgumentException("time cannot go earlier than 8:30 AM");
        };



        if(startTime.isAfter(endTime)){
            throw new IllegalArgumentException("bawal mas maaga ang after");
        }


        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
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
        return days + ":" + startTime  + "-" + endTime;
    }

}

enum Days { MTH, TF, WS }



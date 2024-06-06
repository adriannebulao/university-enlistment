package com.adriannebulao.enlistment;

import static java.util.Objects.*;

class Schedule {

    private final Days days;
    private final Period period;

    Schedule(Days days, Period period) {
        requireNonNull(days);
        requireNonNull(period);
        this.days = days;
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return days == schedule.days && period == schedule.period;
    }

    @Override
    public int hashCode() {
        return hash(days, period);
    }

    @Override
    public String toString() {
        return days + ":" + period;
    }
}

enum Days { MTH, TF, WS }

enum Period {
    H0830_H1000, H1000_H1130, H1130_H1300, H1300_H1430, H1430_H1600;
}
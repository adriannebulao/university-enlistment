package com.adriannebulao.enlistment;
import java.util.Objects;

import static java.util.Objects.*;
import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;


class Room {
    private final String roomName;
    private final int roomCapacity;

    public Room(String roomName, int roomCapacity) {
        isTrue(isAlphanumeric(roomName), "room name must be alphanumeric, was: " + roomName);
        isTrue(roomCapacity >= 0, "studentNumber should be non-negative, was: " + roomCapacity);

        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomName, room.roomName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roomName);
    }

    @Override
    public String toString(){
        return(roomName);
    }

}



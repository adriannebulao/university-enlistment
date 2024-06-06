package com.adriannebulao.enlistment;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    @Test
    void room_has_alphanumeric_name() {
        Room room = new Room("A102", 20);
        assertEquals("A102", room.getRoomName());
        assertEquals(20, room.getRoomCapacity());
    }

}

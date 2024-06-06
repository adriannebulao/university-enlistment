package com.adriannebulao.enlistment;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    @Test
    void room_has_alphanumeric_name() {
        Room room = new Room("A101", 20);
        assertEquals("A101", room.getRoomName());
        assertEquals(20, room.getRoomCapacity());
    }

}

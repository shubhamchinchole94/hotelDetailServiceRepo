package com.hotelConfigService.service;
import com.hotelConfigService.entity.Room;
import com.hotelConfigService.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(String id, Room updatedRoom) {
        Optional<Room> existingRoomOpt = roomRepository.findById(id);
        if (existingRoomOpt.isPresent()) {
            Room existingRoom = existingRoomOpt.get();
            existingRoom.setRoomNumber(updatedRoom.getRoomNumber());
            existingRoom.setStatus(updatedRoom.getStatus());
            existingRoom.setNote(updatedRoom.getNote());
            return roomRepository.save(existingRoom);
        } else {
            throw new RuntimeException("Room not found with id: " + id);
        }
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(String id) {
        return roomRepository.findById(id);
    }

    public void deleteRoom(String id) {
        roomRepository.deleteById(id);
    }


    // RoomService.java
    public void updateStatus(String roomNumber, String status) {
        Optional<Room> roomOptional = roomRepository.findByRoomNumber(roomNumber);

        if (roomOptional.isPresent()) {
            Room presentRoom = roomOptional.get();
            presentRoom.setStatus(status);
            roomRepository.save(presentRoom);
        } else {
            // Create a new Room with the given ID and status
            Room newRoom = new Room();

            newRoom.setStatus(status);
            newRoom.setRoomNumber(roomNumber);
            newRoom.setNote("Auto-created on status update");
            roomRepository.save(newRoom);
        }
    }

}

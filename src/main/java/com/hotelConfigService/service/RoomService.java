package com.hotelConfigService.service;
import com.hotelConfigService.entity.HotelDetails;
import com.hotelConfigService.entity.Room;
import com.hotelConfigService.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void saveDefaultRooms(HotelDetails hotelDetails) {
        int totalFloors = hotelDetails.getTotalFloors();
        int roomsPerFloor = hotelDetails.getRoomsPerFloor();
        int totalRooms = totalFloors * roomsPerFloor;

        List<Room> roomList = new ArrayList<>();
        List<HotelDetails.RoomType> roomTypes = hotelDetails.getRoomTypes();

        int floor = 1;
        int roomInFloor = 1;
        int generatedCount = 0;

        for (HotelDetails.RoomType roomType : roomTypes) {
            for (int i = 0; i < roomType.getTotalRooms(); i++) {
                // Generate room number like 101, 102, ..., 204, 301, etc.
                String roomNumber = floor + String.format("%02d", roomInFloor);

                Room room = new Room();
                room.setRoomNumber(roomNumber);
                room.setStatus("available");
                room.setNote("");
                room.setType(roomType.getName());
                room.setPrice(String.valueOf(roomType.getPrice()));

                roomList.add(room);
                generatedCount++;

                // Increment room number within floor
                roomInFloor++;
                if (roomInFloor > roomsPerFloor) {
                    roomInFloor = 1;
                    floor++;
                }
            }
        }

        if (generatedCount != totalRooms) {
            throw new IllegalArgumentException("Sum of RoomType.totalRooms must equal totalFloors * roomsPerFloor");
        }

        roomRepository.saveAll(roomList);
    }


}

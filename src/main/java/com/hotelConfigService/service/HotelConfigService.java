package com.hotelConfigService.service;

import com.hotelConfigService.entity.HotelDetails;
import com.hotelConfigService.repository.HotelRepository;
import com.hotelConfigService.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelConfigService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomService roomService;

    public HotelDetails saveConfig(HotelDetails hotelDetails) {
        String raw = hotelDetails.getLogoUrl();
        if (raw != null && !raw.startsWith("data:image")) {
            // Optional: You can try to detect content type dynamically if needed
            hotelDetails.setLogoUrl("data:image/png;base64," + raw);
        }
        roomService.saveDefaultRooms(hotelDetails);
        return hotelRepository.save(hotelDetails);
    }

    public List<HotelDetails> getAllConfigs() {
        return hotelRepository.findAll();
    }

    public HotelDetails getById(String id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        hotelRepository.deleteById(id);
    }
}

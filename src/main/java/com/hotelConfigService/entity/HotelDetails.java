package com.hotelConfigService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "hotel_configurations")
public class HotelDetails {

    @Id
    private String id;
    private String hotelName;
    private String address;
    private String phone;
    private String email;
    private String gstNumber;
    private String logoUrl; // base64 or image path
    private int totalFloors;
    private int roomsPerFloor;

    private List<RoomType> roomTypes;
    private int extraBedPrice;

    private Map<String, Integer> mealPrices;
    private Map<String, Boolean> enabledMealPlans;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomType {
        private String name;
        private int price;
    }


}

package com.hotelConfigService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "room_details")
public class Room {

    @Id
    private String id;
    private String status;
    private String roomNumber;
    private String note;
    private String price;
    private String type;

}

package com.hotelConfigService.repository;

import com.hotelConfigService.entity.HotelDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends MongoRepository<HotelDetails, String> {

}

package com.bike_server.dao;

import com.bike_server.pojo.Bike;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BikeMapper {
    List<Bike> getAllBike();

    Bike getBikeById(String id);

    int addBike(Bike bike);

    int deleteBike(String id);

    int updateBike(Bike bike);
}

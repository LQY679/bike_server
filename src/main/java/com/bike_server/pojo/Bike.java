package com.bike_server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bike {
    private String id;
    private int battery_Capacity;
    private String state;
    private String position;

}

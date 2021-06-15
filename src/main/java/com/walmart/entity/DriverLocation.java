package com.walmart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DriverLocation {
    @Id
    @Column
    private String driverID;
    @Column
    private Double longitude;
    @Column
    private Double latitude;

    @Column
    private Double distance;


}
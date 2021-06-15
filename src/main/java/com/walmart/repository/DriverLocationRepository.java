package com.walmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.walmart.entity.DriverLocation;

@Repository
public interface DriverLocationRepository extends JpaRepository<DriverLocation, String> {
	/*
	 * Query to calcuclate distance with latitude and longitude
	 @Query(value = "SELECT driverid,latitude,longitude, ( 3959 * acos(cos(radians(:storelat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:storelng)) + sin(radians(:storelat)) * sin(radians(latitude ))) ) AS distance FROM driver_location ORDER BY distance LIMIT 0, :maxRecords", nativeQuery = true)
	 List<DriverLocation> nearByDrivers(@Param("storelat") double storelat,@Param("storelng") double storelng,@Param("maxRecords") int maxRecords);
	*/ 

	 @Query(value = "SELECT driverid,latitude,longitude,SQRT(POWER(:storelat-latitude,2)+POWER(:storelng-longitude,2)) AS distance FROM driver_location ORDER BY distance LIMIT 0, :maxRecords", nativeQuery = true)
	 List<DriverLocation> nearByDrivers(@Param("storelat") double storelat,@Param("storelng") double storelng,@Param("maxRecords") int maxRecords);

}
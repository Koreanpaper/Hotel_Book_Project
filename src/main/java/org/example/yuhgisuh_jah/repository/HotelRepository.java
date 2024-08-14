package org.example.yuhgisuh_jah.repository;

import org.example.yuhgisuh_jah.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

}

package org.example.yuhgisuh_jah.repository;

import org.example.yuhgisuh_jah.model.Booking;
import org.example.yuhgisuh_jah.model.Hotel;
import org.example.yuhgisuh_jah.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelBookRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByHotelAndMember(Hotel hotel, Member member);

    @Query("SELECT b.hotel FROM Booking b WHERE b.member = :member")
    List<Hotel> findHotelsByMember(Member member);
}

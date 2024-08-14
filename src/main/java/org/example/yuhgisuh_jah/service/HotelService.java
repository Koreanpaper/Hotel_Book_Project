package org.example.yuhgisuh_jah.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.yuhgisuh_jah.model.Booking;
import org.example.yuhgisuh_jah.model.Hotel;
import org.example.yuhgisuh_jah.model.Member;
import org.example.yuhgisuh_jah.repository.HotelBookRepository;
import org.example.yuhgisuh_jah.repository.HotelRepository;
import org.example.yuhgisuh_jah.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final MemberRepository memberRepository;
    private final HotelBookRepository hotelBookRepository;

    public List<Hotel> getAllHotel() {return hotelRepository.findAll();}

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 호텔 Id"+id));}

    public Hotel addHotel(String hotelname, String address, int price, int count, LocalDate selDate){
        Hotel hotel = new Hotel();
        hotel.setHotelname(hotelname);
        hotel.setAddress(address);
        hotel.setPrice(price);
        hotel.setCount(count);
        hotel.setSelDate(selDate);
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(Long id, String hotelname, String address, int price, int count, LocalDate selDate){
        Hotel hotel = getHotelById(id);
        hotel.setHotelname(hotelname);
        hotel.setAddress(address);
        hotel.setPrice(price);
        hotel.setCount(count);
        hotel.setSelDate(selDate);
        return hotelRepository.save(hotel);
    }

    public Hotel deleteHotel(Long id){
        hotelRepository.deleteById(id);
        return hotelRepository.findById(id).orElse(null);
    }

    @Transactional
    public void bookHotel(Long id, String username) {
        Hotel hotel = getHotelById(id);
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username: " + username));

        if (hotel.getCount() > 0) {
            Booking booking = new Booking();
            booking.setHotel(hotel);
            booking.setMember(member);
            hotelBookRepository.save(booking);

            hotel.setCount(hotel.getCount() - 1); // 호텔의 방 수 감소
            hotelRepository.save(hotel);
        } else {
            throw new IllegalStateException("No rooms available for booking");
        }
    }

    public List<Hotel> getBookedHotelsByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
        return hotelBookRepository.findHotelsByMember(member);
    }


    public void unbookHotel(Long id, String username) {
        Hotel hotel = getHotelById(id);
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));

//        hotelBookRepository.findByHotelAndMember(hotel, member).ifPresent(hotelBookRepository::delete);

        hotelBookRepository.findByHotelAndMember(hotel, member).ifPresent(hotelBook -> {
            hotelBookRepository.delete(hotelBook);
            hotel.setCount(hotel.getCount() + 1);  // 방의 수 증가
            hotelRepository.save(hotel);  // 변경된 호텔 정보를 저장
        });
    }

    public List<Long> getBookedHotelIdsByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username: " + username));
        return hotelBookRepository.findHotelsByMember(member)
                .stream()
                .map(Hotel::getId)
                .collect(Collectors.toList());
    }
}

package org.example.yuhgisuh_jah.controller;

import org.example.yuhgisuh_jah.model.Booking;
import org.example.yuhgisuh_jah.model.Hotel;
import org.example.yuhgisuh_jah.model.Member;
import org.example.yuhgisuh_jah.service.HotelService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hotels")
public class HotelController {
    private final HotelService hotelService;
    public HotelController(HotelService hotelService) {this.hotelService = hotelService;}

    @GetMapping
    public String listHotels(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Hotel> hotels = hotelService.getAllHotel();
        List<Long> bookedHotelIds = hotelService.getBookedHotelIdsByUsername(userDetails.getUsername());
        model.addAttribute("hotels", hotels);
        model.addAttribute("bookedHotelIds", bookedHotelIds);
        return "hotel/list";
    }

    @GetMapping("/list")
    public String listHotels(@RequestParam(required = false) LocalDate date, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Hotel> hotels;

        if (date != null) {
            // 사용자가 입력한 날짜와 selDate가 일치하는 호텔들만 필터링
            hotels = hotelService.getAllHotel().stream().filter(hotel -> date.equals(hotel.getSelDate()))
                    .collect(Collectors.toList());
        } else {
            // 날짜가 입력되지 않은 경우 모든 호텔을 보여줌
            hotels = hotelService.getAllHotel();
        }

        List<Long> bookedHotelIds = hotelService.getBookedHotelIdsByUsername(userDetails.getUsername());
        model.addAttribute("hotels", hotels);
        model.addAttribute("bookedHotelIds", bookedHotelIds);

        return "hotel/list";
    }

    @PostMapping("/{id}/book")
    public String bookHotel(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        hotelService.bookHotel(id, userDetails.getUsername());
        return "redirect:/hotels";  // 예약 완료 후 호텔 리스트 페이지로 리다이렉트
    }

    @PostMapping("/{id}/unbook")
    public String unbookHotel(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        hotelService.unbookHotel(id, userDetails.getUsername());
        return "redirect:/hotels";  // 예약 취소 후 호텔 리스트 페이지로 리다이렉트
    }

    @GetMapping("/{id}")
    public String getHotelById(@PathVariable("id") Long id, Model model) {
        Hotel hotel = hotelService.getHotelById(id);
        if (hotel != null) {
            model.addAttribute("hotel", hotel);
            return "hotelDetails"; // hotelDetails.html 템플릿을 반환
        } else {
            return "redirect:/error"; // 호텔을 찾지 못하면 에러 페이지로 리다이렉트
        }
    }

    @GetMapping("/booked")
    public String bookedHotels(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Hotel> bookedHotels = hotelService.getBookedHotelsByUsername(userDetails.getUsername());
        model.addAttribute("bookedHotels", bookedHotels);
        return "hotel/book";
    }


    }



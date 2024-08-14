package org.example.yuhgisuh_jah.controller;

import org.example.yuhgisuh_jah.model.Hotel;
import org.example.yuhgisuh_jah.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/hotels")
public class AdminHotelController {
    private final HotelService hotelService;
    public AdminHotelController(HotelService hotelService) {this.hotelService = hotelService;}

    @GetMapping
    public String listHotels(Model model) {
        model.addAttribute("hotels", hotelService.getAllHotel());
        return "admin/hotel/list";
    }

    @GetMapping("/add")
    public String addHotel(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "admin/hotel/form";
    }

    @PostMapping("/add")
    public String addHotel(@ModelAttribute Hotel hotel) {
        hotelService.addHotel(hotel.getHotelname(), hotel.getAddress(), hotel.getPrice(), hotel.getCount(), hotel.getSelDate());
        return "redirect:/admin/hotels";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("hotel", hotelService.getHotelById(id));
        return "admin/hotel/form";
    }

    @PostMapping("/edit/{id}")
    public String updateHotel(@PathVariable Long id, @ModelAttribute Hotel hotel) {
        hotelService.updateHotel(id,hotel.getHotelname(),hotel.getAddress(),hotel.getPrice(),hotel.getCount(), hotel.getSelDate());
        return "redirect:/admin/hotels";
    }

    @PostMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return "redirect:/admin/hotels";
    }
}

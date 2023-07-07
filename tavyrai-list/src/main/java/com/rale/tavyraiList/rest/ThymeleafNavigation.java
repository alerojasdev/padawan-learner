package com.rale.tavyraiList.rest;
import com.rale.tavyraiList.spotifyapi.SpotifyApi;
import com.rale.tavyraiList.spotifyapi.modelsdto.ItemFromPlaylist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ThymeleafNavigation {
    private int clickCount = 0;

    @GetMapping("/")
    public String login(Model model) {
        clickCount = 0;
        return "login";
    }

    @GetMapping("/home-app")
    public String home(Model model) {
        model.addAttribute("clickCount", clickCount);
        model.addAttribute("message", "Nde tavyrai");
        return "home-app";
    }

    @PostMapping("/home-app")
    public String increment(Model model) {
        clickCount++;
        model.addAttribute("clickCount", clickCount);
        return "home-app";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        return "logout";
    }

    @GetMapping("/showError")
    public String showError(Exception e, Model model) {
        String error = e.getMessage();
        model.addAttribute("message", error);
        return "home-app";
    }
//    @GetMapping("/playlist")
//
//        private String convertUsingReflection(){
////        SpotifyApi spotifyApi = new SpotifyApi();
//        List<ItemFromPlaylist> playlist = spotifyApi.getUserPlayList();
//        Map<String, Object> map = new HashMap<>();
//        Field[] fields = playlist.getClass().getDeclaredFields();
//
//        for (Field field: fields) {
//            field.setAccessible(true);
//            try {
//                map.put(field.getName(), field.get(playlist));
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//            return map.toString();
//    }
}





































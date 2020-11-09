package com.bretgibson.spotifystats.controller;

import com.bretgibson.spotifystats.service.AuthorizationService;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("api/")
public class AppController {

    @GetMapping("login")
    @ResponseBody
    public String spotifyLogin(){
        return AuthorizationService.authorizationCodeUri_Sync();
//        AuthorizationService.authorizationCode_Sync();
    }
//
//    @RequestMapping("/to-be-redirected")
//    public RedirectView localRedirect() {
//        RedirectView redirectView = new RedirectView();
//        redirectView.setUrl(AuthorizationService.authorizationCodeUri_Sync());
//        return redirectView;
//    }
}

package com.bretgibson.spotifystats.controller;

import com.bretgibson.spotifystats.Keys;
import com.bretgibson.spotifystats.service.AuthorizationService;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

//@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("api/")
public class AppController {

    AuthorizationService authorizationService = new AuthorizationService();

    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api/user_code/");
    private String code = "";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(Keys.CLIENT_ID.getKey())
            .setClientSecret(Keys.CLIENT_SECRET.getKey())
            .setRedirectUri(redirectUri)
            .build();


    @GetMapping("login")
    @ResponseBody
    public String spotifyLogin(){
            AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//          .state("x4xkmn9pu3j6ukrs8n")
          .scope("user-read-private, user-read-email, user-top-read")
          .show_dialog(true)
            .build();
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("URI: " + uri.toString());
        return uri.toString();
//        AuthorizationService.authorizationCodeUri_Sync();
    }

    @GetMapping(value = "user_code")
    public Artist[] getSpotifyUserCode(@RequestParam("code") String userCode) {
        code = userCode;
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                .build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists().time_range("medium_term").limit(10).offset(5).build();

        try {
            // Execute the request synchronous
            final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();

            // Print something's name
            return artistPaging.getItems();
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
        }
//        AuthorizationService.authorizationCode_Sync();

//        authorizationService.setCode(code);
//        System.out.println(authorizationService.getCode());
//        authorizationService.authorizationCode_Sync();
        return new Artist[0];
    }
}

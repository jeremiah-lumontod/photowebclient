package com.appsdeveloperblog.ws.clients.photoappwebclient.controllers;

import java.util.Arrays;
import java.util.List;

import com.appsdeveloperblog.ws.clients.photoappwebclient.response.AlbumRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AlbumsController {
	
/*	@Autowired
	OAuth2AuthorizedClientService oauth2ClientService;*/
	
//	@Autowired
//	RestTemplate restTemplate;

/*
	@Autowired
	WebClient webClient;
*/

/*
	@GetMapping("/albums")
	public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal) {
*/

	@GetMapping("/albums")
	public String getAlbums(Model model) {

		AlbumRest album = new AlbumRest();
		album.setAlbumId("albumOne");
		album.setAlbumTitle("Album one title");
		album.setAlbumUrl("http://localhost:8082/albums/1");

		AlbumRest album2 = new AlbumRest();
		album2.setAlbumId("albumTwo");
		album2.setAlbumTitle("Album two title");
		album2.setAlbumUrl("http://localhost:8082/albums/2");

		model.addAttribute("albums", Arrays.asList(album, album2));

		
		
		return "albums";
	}
	
}

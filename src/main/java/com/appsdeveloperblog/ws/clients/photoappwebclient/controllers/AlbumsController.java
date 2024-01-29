package com.appsdeveloperblog.ws.clients.photoappwebclient.controllers;

import java.util.Arrays;

import com.appsdeveloperblog.ws.clients.photoappwebclient.response.AlbumRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;


@Controller
public class AlbumsController {

	private final OAuth2AuthorizedClientService oauth2ClientService;

	@Autowired
	public AlbumsController(OAuth2AuthorizedClientService oauth2ClientService) {
		this.oauth2ClientService = oauth2ClientService;
	}

	@GetMapping("/albums")
	public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal) {

		System.out.println("Principal: " + principal);
		OidcIdToken idToken = principal.getIdToken();
		String idTokenValue = idToken.getTokenValue();
		System.out.println("idTokenValue: " + idTokenValue);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

		OAuth2AuthorizedClient oauth2Client = oauth2ClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

		String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();
		System.out.println("\n\n\n");System.out.println("\n\n\n");System.out.println("\n\n\n");
		System.out.println("jwtAccessToken = " + jwtAccessToken);

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

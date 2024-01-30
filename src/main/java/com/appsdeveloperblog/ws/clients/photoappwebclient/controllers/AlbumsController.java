package com.appsdeveloperblog.ws.clients.photoappwebclient.controllers;

import java.util.Arrays;
import java.util.List;

import com.appsdeveloperblog.ws.clients.photoappwebclient.response.AlbumRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@Controller
public class AlbumsController {

	private final OAuth2AuthorizedClientService oauth2ClientService;
	//private final RestTemplate restTemplate;

//	@Autowired
//	RestTemplate restTemplate;

	@Autowired
	private final WebClient webClient;

	@Autowired
	public AlbumsController(OAuth2AuthorizedClientService oauth2ClientService, WebClient webClient) {
		this.oauth2ClientService = oauth2ClientService;
		//this.restTemplate = restTemplate;
		this.webClient = webClient;
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

		/**AlbumRest album = new AlbumRest();
		album.setAlbumId("albumOne");
		album.setAlbumTitle("Album one title");
		album.setAlbumUrl("http://localhost:8082/albums/1");

		AlbumRest album2 = new AlbumRest();
		album2.setAlbumId("albumTwo");
		album2.setAlbumTitle("Album two title");
		album2.setAlbumUrl("http://localhost:8082/albums/2");*/


		String url = "http://localhost:8082/albums";

		/**HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + jwtAccessToken);
		HttpEntity<List<AlbumRest>> entity = new HttpEntity<>(headers);
		ResponseEntity<List<AlbumRest>> responseEntity =  restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<AlbumRest>>() {});

		List<AlbumRest> albums = responseEntity.getBody();*/

		List<AlbumRest> albums = webClient.get()
				.uri(url)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<AlbumRest>>(){})
				.block();

		model.addAttribute("albums", albums);

		return "albums";

	}
	
}

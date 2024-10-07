package com.pw.webfluxintro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
class WebFluxIntroApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	private final WebClient webClient = WebClient.create("http://localhost:8080");

	@Test
	void reactiveEndpointSucceeds() {
		Flux<Integer> flux = webClient.get()
				.uri("/number")
				.retrieve()
				.bodyToFlux(Integer.class);
		StepVerifier.create(flux).assertNext(i -> assertEquals(1, i))
				.assertNext(i -> assertEquals(2, i))
				.thenCancel()
				.verify();
	}

	@Test
	void listEndpointSucceeds() {
		webTestClient.mutate().responseTimeout(Duration.ofMillis(30000)).build().get()
				.uri("/number/list")
				.exchange()
				.expectStatus().isOk()
				.expectBody(List.class).value(list -> assertEquals(10, list.size()));
	}
}

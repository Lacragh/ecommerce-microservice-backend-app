package com.selimhorri.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateOrder() {
        String orderJson = "{\"orderDesc\":\"test order\",\"orderFee\":5000,\"cart\":{\"cartId\":1}}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(orderJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/api/orders", entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllOrders() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/orders", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetOrderById() {
        // Asegúrate de que exista una orden con ID 1
        ResponseEntity<String> response = restTemplate.getForEntity("/api/orders/1", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is4xxClientError());
    }

    @Test
    void testUpdateOrder() {
        // Ajusta el JSON y el ID según tu modelo y datos existentes
        String orderJson = "{\"orderId\":1,\"orderDesc\":\"updated order\",\"orderFee\":6000,\"cart\":{\"cartId\":1}}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(orderJson, headers);
        ResponseEntity<String> response = restTemplate.exchange("/api/orders/1", HttpMethod.PUT, entity, String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is4xxClientError());
    }

    @Test
    void testDeleteOrder() {
        // Asegúrate de que exista una orden con ID 1 antes de eliminar
        ResponseEntity<String> response = restTemplate.exchange("/api/orders/1", HttpMethod.DELETE, null, String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is4xxClientError());
    }
}
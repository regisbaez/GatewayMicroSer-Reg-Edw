package com.practicafinal.gateway.controladores;

import com.practicafinal.gateway.entidades.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController()
public class ClientController {

        private RestTemplate restTemplate = new RestTemplate();

        @Autowired
        private MailController correocontroller;

        @GetMapping(value = "/api/clientes/todos", produces = { "application/json" })
        public ResponseEntity<List<Client>> listar() {
                ResponseEntity<List<Client>> listResponseEntity = restTemplate.exchange(
                                "http://localhost:8082/clientes", HttpMethod.GET, null,
                                new ParameterizedTypeReference<List<Client>>() {

                                });
                System.out.println("cod:" + listResponseEntity.getStatusCode());
                return new ResponseEntity<>(listResponseEntity.getBody(), HttpStatus.OK);
        }

        @GetMapping(value = "/api/clientes/nombre/{nombre}")
        public ResponseEntity<List<Client>> clientesPorNombre(@PathVariable String nombre) {
                ResponseEntity<List<Client>> listResponseEntity = restTemplate.exchange(
                                "http://localhost:8082/clientes/nombre/" + nombre, HttpMethod.GET, null,
                                new ParameterizedTypeReference<List<Client>>() {
                                });
                return new ResponseEntity<>(listResponseEntity.getBody(), HttpStatus.OK);
        }

        @PostMapping(value = "/api/clientes", consumes = "application/json")
        public ResponseEntity<Client> crear(@RequestBody Client cliente) {

                HttpEntity<Client> request = new HttpEntity<>(cliente);
                ResponseEntity<Client> exchange = restTemplate.exchange("http://localhost:8082/clientes/",
                                HttpMethod.POST, request, Client.class);
                return new ResponseEntity<>(exchange.getStatusCode());
        }

        @RequestMapping(value = "/api/clientes/devolver", method = RequestMethod.POST)
        public ResponseEntity<Client> devolverNuevoCliente(@RequestBody Client cliente) {
                HttpEntity<Client> request = new HttpEntity<>(cliente);
                // System.out.println("nombre: " + cliente.getNombre());
                ResponseEntity<Client> exchange = restTemplate.exchange("http://localhost:8082/clientes/devolver",
                                HttpMethod.POST, request, Client.class);

                restTemplate.exchange("http://localhost:8088/correo/nuevoCliente", HttpMethod.GET, request,
                                Client.class);
                return new ResponseEntity<>(exchange.getBody(), exchange.getStatusCode());
        }

        @RequestMapping(value = "/api/clientes", method = RequestMethod.PUT, consumes = "application/json")
        public ResponseEntity<Client> actualizar(@RequestBody Client cliente) {

                HttpEntity<Client> request = new HttpEntity<>(cliente);

                ResponseEntity<Client> exchange = restTemplate.exchange("http://localhost:8082/clientes/",
                                HttpMethod.PUT, request, Client.class);

                return new ResponseEntity<>(exchange.getStatusCode());
        }

        @RequestMapping(value = "/api/clientes", method = RequestMethod.DELETE)
        public ResponseEntity<Client> borrar(@RequestParam("id") Long id) {

                HttpEntity<Long> request = new HttpEntity<>(id);

                ResponseEntity<Long> exchange = restTemplate.exchange("http://localhost:8082/clientes/",
                                HttpMethod.DELETE, request, Long.class);

                return new ResponseEntity<>(exchange.getStatusCode());
        }

        @RequestMapping(value = "/api/cliente/paginacion", method = RequestMethod.GET, params = { "limit",
                        "offset" }, produces = { "application/json" })
        public ResponseEntity<Client> clientesPaginacion(@RequestParam("limit") int limit,
                                                         @RequestParam("offset") int offset) {

                String url = "http://localhost:8082/clientes/paginacion";
                UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("limit", limit)
                                .queryParam("offset", offset);

                ResponseEntity<Client> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                                null, new ParameterizedTypeReference<Client>() {
                                });

                return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        }

        @GetMapping("/api/clientes/{id}")
        public ResponseEntity<Client> buscarPorId(@PathVariable Long id) {

                HttpEntity<Long> request = new HttpEntity<>(id);

                ResponseEntity<Client> exchange = restTemplate.exchange("http://localhost:8082/clientes/" + id,
                                HttpMethod.GET, request, Client.class);

                return new ResponseEntity<>(exchange.getBody(), exchange.getStatusCode());
        }

        @GetMapping("/api/clientes/cantidad")
        public ResponseEntity<Long> contarClientes() {

                ResponseEntity<Long> exchange = restTemplate.exchange("http://localhost:8082/clientes/cantidad",
                                HttpMethod.GET, null, Long.class);

                return new ResponseEntity<>(exchange.getBody(), exchange.getStatusCode());
        }

}

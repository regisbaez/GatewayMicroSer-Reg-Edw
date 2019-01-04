package com.practicafinal.gateway.controladores;

import com.practicafinal.gateway.entidades.Art;
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
public class ArtController {

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value = "/api/articulos/todos", produces = { "application/json" })
    public ResponseEntity<List<Art>> listar() {

        ResponseEntity<List<Art>> listResponseEntity = restTemplate.exchange("http://localhost:8081/articulos",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Art>>() {
                });
        System.out.println("cod:" + listResponseEntity.getStatusCode());
        return new ResponseEntity<>(listResponseEntity.getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/api/articulos/nombre/{nombre}")
    public ResponseEntity<List<Art>> articulosPorNombre(@PathVariable String nombre) {

        ResponseEntity<List<Art>> listResponseEntity = restTemplate.exchange(
                "http://localhost:8081/articulos/nombre/" + nombre, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Art>>() {
                });

        return new ResponseEntity<>(listResponseEntity.getBody(), HttpStatus.OK);
    }

    @PostMapping(value = "/api/articulos", consumes = "application/json")
    public ResponseEntity<Art> crear(@RequestBody Art articulo) {

        HttpEntity<Art> request = new HttpEntity<>(articulo);

        ResponseEntity<Art> exchange = restTemplate.exchange("http://localhost:8081/articulos/", HttpMethod.POST,
                request, Art.class);

        return new ResponseEntity<>(exchange.getStatusCode());
    }

    @RequestMapping(value = "/api/articulos", method = RequestMethod.PUT)
    public ResponseEntity<Art> actualizar(@RequestBody Art articulo) {

        HttpEntity<Art> request = new HttpEntity<>(articulo);

        ResponseEntity<Art> exchange = restTemplate.exchange("http://localhost:8081/articulos/", HttpMethod.PUT,
                request, Art.class);

        return new ResponseEntity<>(exchange.getStatusCode());
    }

    @RequestMapping(value = "/api/articulos/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Art> borrar(@PathVariable Long id) {

        HttpEntity<Long> request = new HttpEntity<>(id);

        ResponseEntity<Long> exchange = restTemplate.exchange("http://localhost:8081/articulos/?id="+id, HttpMethod.DELETE,
                request, Long.class);

        return new ResponseEntity<>(exchange.getStatusCode());
    }

    @RequestMapping(value = "/api/articulos/comprar", method = RequestMethod.PUT, params = { "id", "cantidad" })
    public ResponseEntity<Art> comprarArticulos(@RequestParam("id") Long id,
                                                @RequestParam("cantidad") int cantidad) {

        String url = "http://localhost:8081/articulos/comprar";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("id", id)
                .queryParam("cantidad", cantidad);

        restTemplate.put(builder.toUriString(), null);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/articulos/paginacion", method = RequestMethod.GET, params = { "limit",
            "offset" }, produces = { "application/json" })
    public ResponseEntity<List<Art>> articulosPaginacion(@RequestParam("limit") int limit,
                                                         @RequestParam("offset") int offset) {

        String url = "http://localhost:8081/articulos/paginacion";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("limit", limit)
                .queryParam("offset", offset);

        ResponseEntity<List<Art>> lista = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Art>>() {
                });

        return new ResponseEntity<>(lista.getBody(), lista.getStatusCode());
    }

    @GetMapping("/api/articulos/{id}")
    public ResponseEntity<Art> buscarPorId(@PathVariable Long id) {

        HttpEntity<Long> request = new HttpEntity<>(id);

        ResponseEntity<Art> exchange = restTemplate.exchange("http://localhost:8081/articulos/" + id,
                HttpMethod.GET, request, Art.class);

        return new ResponseEntity<>(exchange.getBody(), HttpStatus.OK);
    }

    @GetMapping("/api/articulos/cantidad")
    public ResponseEntity<Long> contarArticulos() {

        ResponseEntity<Long> exchange = restTemplate.exchange("http://localhost:8081/articulos/cantidad",
                HttpMethod.GET, null, Long.class);

        return new ResponseEntity<>(exchange.getBody(), HttpStatus.OK);
    }

}

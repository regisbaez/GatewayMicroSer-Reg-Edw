package com.practicafinal.gateway.controladores;

import java.util.List;

import com.practicafinal.gateway.entidades.Client;
import com.practicafinal.gateway.entidades.Comp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * CompraController
 */
@RestController
public class CompController {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ClientController clienteController;

    @GetMapping(value="/api/compra")
    public ResponseEntity<List<Comp>> listarCompras() {
        
        ResponseEntity<List<Comp>> listResponseEntity = restTemplate.exchange("http://localhost:8085/compras", HttpMethod.GET, null, new ParameterizedTypeReference<List<Comp>>() {});

        return new ResponseEntity<>(listResponseEntity.getBody(), listResponseEntity.getStatusCode());
    }

    @PostMapping("/api/compra")
    public ResponseEntity<Comp> crearCompra(@RequestBody Comp compra){
        
        HttpEntity<Comp> request = new HttpEntity<>(compra);

        ResponseEntity<Comp> exchange = restTemplate.exchange("http://localhost:8085/compras", HttpMethod.POST, request, Comp.class);

        return new ResponseEntity<>(exchange.getStatusCode());
    }



    @GetMapping(value = "api/compras/paginacion",  params = {"limit", "offset"})
    public ResponseEntity<List<Comp>> comprasPorPaginacion(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        
        String url = "http://localhost:8085/compras/paginacion";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("limit", limit)
                .queryParam("offset", offset);

        ResponseEntity<List<Comp>> lista = restTemplate.exchange(builder.toUriString(),HttpMethod.GET, null, new ParameterizedTypeReference<List<Comp>>(){});


        return new ResponseEntity<>(lista.getBody(), lista.getStatusCode());
    }

    @GetMapping(value = "api/compras/paginacion/cliente/{cliente}",  params = {"limit", "offset"})
    public ResponseEntity<List<Comp>> comprasPorPaginacionCliente(@RequestParam("limit") int limit, @RequestParam("offset") int offset, @PathVariable Long cliente) {
        
        Client cliente2 = clienteController.buscarPorId(cliente).getBody();
        HttpEntity<Client> request = new HttpEntity<>(cliente2);

        String url = "http://localhost:8085/compras/paginacion";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("limit", limit)
                .queryParam("offset", offset);

        ResponseEntity<List<Comp>> lista = restTemplate.exchange(builder.toUriString(),HttpMethod.GET, request , new ParameterizedTypeReference<List<Comp>>(){});


        return new ResponseEntity<>(lista.getBody(), lista.getStatusCode());
    }

    @GetMapping("/api/compras/{id}")
    public ResponseEntity<Comp> compraPorId(@PathVariable Long id) {

        HttpEntity<Long> request = new HttpEntity<>(id);

        ResponseEntity<Comp> exchange = restTemplate.exchange("http://localhost:8085/compras/" + id, HttpMethod.GET, request, Comp.class);

        return new ResponseEntity<>(exchange.getBody(), exchange.getStatusCode());
        
    }

    
}
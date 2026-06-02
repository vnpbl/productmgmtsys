package com.gabriel.prod.serviceImpl;

import com.gabriel.prod.model.Uom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UomService {

    Logger logger = LoggerFactory.getLogger(UomService.class);
    static UomService service=null;
    private String endpointUrl = loadEndpoint("service.api.uomEndpoint", "http://localhost:8080/api/uom");
    RestTemplate restTemplate = null;

    private String loadEndpoint(String key, String defaultValue) {
        Properties properties = new Properties();
        try (InputStream stream = UomService.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (stream != null) {
                properties.load(stream);
                String value = properties.getProperty(key);
                if (value != null && !value.isBlank()) {
                    return value.trim();
                }
            }
        } catch (IOException ex) {
            logger.info("Failed to load app.properties: " + ex.getMessage());
        }
        return defaultValue;
    }

    public static UomService getService(){
        if(service == null){
            service=new UomService();
        }
        return service;
    }
    public RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
            messageConverters.add(converter);
            restTemplate.setMessageConverters(messageConverters);
        }
        return restTemplate;
    }

    public Uom getUom(Integer id) {
        String url = endpointUrl + "/" + Integer.toString(id);
        logger.info("getUom: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<Uom> response =
                getRestTemplate().exchange(url, HttpMethod.GET, request, Uom.class);
        return response.getBody();
    }

    public Uom[] getUoms() {
        String url = endpointUrl;
        logger.info("getUoms: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<Uom[]> response =
                getRestTemplate().exchange(url, HttpMethod.GET, request, Uom[].class);
        Uom[] uoms = response.getBody();
        return uoms;
    }

    //for future use - modify combobox items
    public Uom create(Uom uom) {
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Uom> request = new HttpEntity<>(uom, headers);
        final ResponseEntity<Uom> response =
                getRestTemplate().exchange(url, HttpMethod.PUT, request, Uom.class);
        return response.getBody();
    }

    public Uom update(Uom uom) {
        logger.info("update: " + uom.toString());
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Uom> request = new HttpEntity<>(uom, headers);
        final ResponseEntity<Uom> response =
                getRestTemplate().exchange(url, HttpMethod.POST, request, Uom.class);
        return response.getBody();
    }

    public void delete(Integer id) {
        logger.info("delete: " + Integer.toString(id));
        String url = endpointUrl + "/" + Integer.toString(id);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Uom> request = new HttpEntity<>(null, headers);
        final ResponseEntity<Uom> response =
                getRestTemplate().exchange(url, HttpMethod.DELETE, request, Uom.class);
    }
}

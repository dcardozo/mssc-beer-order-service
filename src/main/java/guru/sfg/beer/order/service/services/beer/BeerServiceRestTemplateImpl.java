package guru.sfg.beer.order.service.services.beer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.sfg.beer.order.service.services.beer.model.BeerDto;

@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Component
public class BeerServiceRestTemplateImpl implements BeerService {

    private final String UPC_SERVICE_PATH = "/api/v1/beerUpc/{upc}";
    private final String ID_SERVICE_PATH = "/api/v1/beer/{beerId}";
    private final RestTemplate restTemplate;

    private String beerServiceHost;

    public BeerServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setBeerServiceHost(String beerServiceHost) {
        this.beerServiceHost = beerServiceHost;
    }

    @Override
    public Optional<BeerDto> getBeerByUpc(String upc) {

        ResponseEntity<BeerDto> responseEntity = restTemplate
                .exchange(beerServiceHost + UPC_SERVICE_PATH, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {}, upc);

        Optional<BeerDto> optionalDto = Optional.of(responseEntity.getBody());
        return optionalDto;
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID beerId) {

        ResponseEntity<BeerDto> responseEntity = restTemplate
                .exchange(beerServiceHost + ID_SERVICE_PATH, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {}, beerId);

        Optional<BeerDto> optionalDto = Optional.of(responseEntity.getBody());
        return optionalDto;
    }
}

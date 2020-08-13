package guru.sfg.beer.order.service.web.mappers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.services.beer.BeerService;
import guru.sfg.beer.order.service.services.beer.model.BeerDto;
import guru.sfg.beer.order.service.web.model.BeerOrderLineDto;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper{
    private BeerService beerService;
    private BeerOrderLineMapper mapper;

    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Autowired
    public void setMapper(BeerOrderLineMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {
        BeerOrderLineDto orderLineDto = mapper.beerOrderLineToDto(line);

        Optional<BeerDto> optionalDto = beerService.getBeerByUpc(line.getUpc());
        optionalDto.ifPresent(beerDto -> {
            orderLineDto.setBeerId(beerDto.getId());
            orderLineDto.setBeerName(beerDto.getBeerName());
            orderLineDto.setBeerStyle(beerDto.getBeerStyle().name());
            orderLineDto.setPrice(beerDto.getPrice());
        });

        return orderLineDto;
    }

    @Override
    public BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto) {
        return mapper.dtoToBeerOrderLine(dto);
    }
}

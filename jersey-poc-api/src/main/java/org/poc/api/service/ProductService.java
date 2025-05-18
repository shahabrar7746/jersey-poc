package org.poc.api.service;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import jakarta.ws.rs.*;
import org.poc.api.dto.ProductDTO;
import org.poc.api.filter.ProductFilter;
import org.springframework.http.MediaType;

import java.util.List;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON_VALUE})
public interface ProductService {


    @GET
    List<ProductDTO> getProductsOrderByName(@Valid @BeanParam ProductFilter filter);

    @POST
    List<ProductDTO> getProductsOrderByPrice(@Valid @BeanParam ProductFilter filter);

}

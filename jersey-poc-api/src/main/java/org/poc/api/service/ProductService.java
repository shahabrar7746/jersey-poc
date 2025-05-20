package org.poc.api.service;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import jakarta.ws.rs.*;
import org.poc.api.dto.ProductDTO;
import org.poc.api.filter.ProductFilter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Path("/products")

@Consumes({MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
@Produces({MediaType.APPLICATION_JSON_VALUE})
public interface ProductService {



    @GET
    List<ProductDTO> findAllProductSortByName(@Valid @BeanParam ProductFilter filter);

    @POST
    List<ProductDTO> findAllProductSortByPrice(@Valid @BeanParam ProductFilter filter);


    @POST
    @Path("/add")
    ProductDTO addProduct(ProductDTO dto);

    @PUT
    @Path("/update/{id}")
    ProductDTO updateProduct(@PathParam("id") int id, @RequestBody ProductDTO dto);


    @DELETE
    @Path("/delete")
    ProductDTO deletedProduct(@QueryParam("id") Integer id);

}

package org.poc.api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProductDTO {


    private String name;


    private Boolean isAvailable;


    private Integer price;


    private LocalDate createdOn;


    private List<String> categories;
}

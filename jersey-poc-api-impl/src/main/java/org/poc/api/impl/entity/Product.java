package org.poc.api.impl.entity;


import jakarta.persistence.*;
import jakarta.persistence.metamodel.StaticMetamodel;
import lombok.Data;
import org.poc.api.impl.enums.EnumCategory;

import java.util.List;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "price")
    private Integer price;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private List<Category> categories;

}

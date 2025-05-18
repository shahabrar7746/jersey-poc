package org.poc.api.impl.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.poc.api.impl.enums.EnumCategory;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@Entity
@Data
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name")
    private EnumCategory category;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private List<Product> products;

}

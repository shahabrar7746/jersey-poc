package org.poc.api.impl.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Table(name = "product")
@Getter
@Setter
@Entity
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

    @CreationTimestamp
    @JsonFormat(pattern = "dd/MMM/yyyy", locale = "en")
    @Column(name = "created_on")
    private LocalDate createdOn;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;


}

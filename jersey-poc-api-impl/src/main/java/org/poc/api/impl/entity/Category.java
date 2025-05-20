package org.poc.api.impl.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "category")
public class Category  implements Serializable{



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    @Column(name = "category_name")
    private String category;

    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "categories", fetch = FetchType.EAGER)
    private List<Product> products;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", products=" + !products.isEmpty() +
                '}';
    }
}

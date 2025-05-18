package org.poc.api.impl.serviceimpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.poc.api.dto.ProductDTO;
import org.poc.api.filter.ProductFilter;
import org.poc.api.impl.entity.Product;
import org.poc.api.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final EntityManager manager;

    @Override
    public List<ProductDTO> getProductsOrderByName(ProductFilter filter) {
        return List.of();
    }

    @Override
    public List<ProductDTO> getProductsOrderByPrice(ProductFilter filter) {
        return List.of();
    }

    private Order orderByProductName(CriteriaBuilder builder,  Root<Product> root) {
        Order orderByProductName = builder.asc(root.get("name"));
      return orderByProductName;
    }
}

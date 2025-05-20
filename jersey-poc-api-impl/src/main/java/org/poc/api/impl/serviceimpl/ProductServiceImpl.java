package org.poc.api.impl.serviceimpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.poc.api.dto.ProductDTO;
import org.poc.api.filter.ProductFilter;
import org.poc.api.impl.RequestPredicates;
import org.poc.api.impl.entity.Category;
import org.poc.api.impl.entity.Category_;
import org.poc.api.impl.entity.Product;
import org.poc.api.impl.entity.Product_;
import org.poc.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private  final EntityManager manager;

    private final RequestPredicates predicateSource;


    @Override
    public List<ProductDTO> findAllProductSortByName(ProductFilter filter) {//look after
        final var builder = manager.getCriteriaBuilder();
        final var query = builder.createQuery(Product.class);
        final var root = query.from(Product.class);
        final var order = builder.asc(root.get(Product_.NAME));
        query.orderBy(order);
       getPredicates(filter, builder, query,root).forEach(predicate -> {
           query.where(builder.or(predicate));
       });
       return generateListOfProductDto(manager.createQuery(query).getResultList());
    }

    @Override
    public List<ProductDTO> findAllProductSortByPrice(ProductFilter filter) {
        final var builder = manager.getCriteriaBuilder();
        final var query = builder.createQuery(Product.class);
        final var root = query.from(Product.class);
        final var order = builder.asc(root.get(Product_.PRICE));
        query.orderBy(order);
        getPredicates(filter, builder, query,root).forEach(predicate -> {
            query.where(builder.or(predicate));
        });
        return generateListOfProductDto(manager.createQuery(query).getResultList());
    }

    @Override
    @Transactional
    public ProductDTO addProduct(ProductDTO dto) {
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setIsAvailable(dto.getIsAvailable());
        entity.setCategories(getCategory(dto.getCategories()));
        manager.persist(entity);
        return dto;
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(int id, ProductDTO dto) {

        Product product = manager.find(Product.class, id);
        if(product == null)
        {
            return null;
        }
        final var builder = manager.getCriteriaBuilder();
        final var criteriaUpdate = builder.createCriteriaUpdate(org.poc.api.impl.entity.Product.class);
        final var productRoot = criteriaUpdate.from(Product.class);
        criteriaUpdate.set(productRoot.get(Product_.NAME), dto.getName())
                .set(productRoot.get(Product_.PRICE), dto.getPrice())
               .set(productRoot.get(Product_.CATEGORIES), product.getCategories())
                .set(productRoot.get(Product_.IS_AVAILABLE), dto.getIsAvailable())
                .where(builder.equal(productRoot.get(Product_.ID), id));

        int i = 0;
        try {
             i = manager.createQuery(criteriaUpdate).executeUpdate();
        } catch (Exception e) {
            throw  e;
        }
        return i > 0 ? dto : null;
    }

    @Override
    @Transactional
    public ProductDTO deletedProduct(Integer id) {
        final var builder = manager.getCriteriaBuilder();
        final var criteriaDelete = builder.createCriteriaDelete(Product.class);
        final var productRoot = criteriaDelete.from(Product.class);
        criteriaDelete.where(builder.equal(productRoot.get(Product_.ID), id));
        final var product = manager.find(Product.class, id);
        manager.createQuery(criteriaDelete).executeUpdate();
        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setCreatedOn(product.getCreatedOn());
        dto.setPrice(product.getPrice());
        dto.setIsAvailable(product.getIsAvailable());
        return dto;
    }


    private List<Category> getCategory(List<String> categories)
   {
       final var builder = manager.getCriteriaBuilder();
       final var query = builder.createQuery(Category.class);
       final  var root = query.from(Category.class);
       final var predicate = root.get(Category_.CATEGORY).in(categories);
       query.where(predicate);
       return manager.createQuery(query).getResultList();
   }
   private List<Predicate> getPredicates(ProductFilter filter,
                                         CriteriaBuilder builder,
                                         CriteriaQuery<Product> query,
                                         Root<Product> root)
   {
       List<Predicate> predicates = new ArrayList<>();
       if(filter.hasCreatedBeforeDate() && filter.hasCreatedAfterDate())
       {
           predicates.add(predicateSource.getCreatedBetweenPredicate(builder, root, filter.getCreatedAfterDate(), filter.getCreatedBeforeDate()));
           return predicates;
       }
       if(filter.hasPriceNullOrUnAvailable())
       {
           predicates.add(predicateSource.getPriceIsNullOrUnAvailablePredicate(builder, root));
       }
       if(filter.hasCreatedOnYear())
       {
           predicates.add(predicateSource.getPredicateCreatedOnGivenYear(builder, root,filter.getCreatedOnYear()));
       }
       if (filter.hasPriceGreaterThan() && filter.hasPriceLessThan())
       {
           predicates.add(predicateSource.getPriceBetweenPredicate(builder, root, filter.getPriceLessThan(), filter.getPriceGreaterThan()));
       }
       if(filter.hasPriceLessThan() && filter.hasCreatedAfterDate())
       {
           predicates.add(predicateSource.getPriceLesserThanAndCreatedAfterPredicate(builder, root, filter.getPriceGreaterThan(), filter.getCreatedAfterDate()));
       }
       if(filter.hasCategory())
       {
           predicates.add(predicateSource.getProductsBelongingToParticularCategory(builder, root, filter.getCategory()));
       }

       return predicates;
   }
    private List<ProductDTO> generateListOfProductDto(List<Product> products) {
        return products
                .stream()
                .map(p -> {
                    ProductDTO dto = new ProductDTO();
                    dto.setCategories(
                            p.getCategories().stream().map(Category::getCategory
                            ).collect(Collectors.toList())
                    );
                    dto.setName(p.getName());
                    dto.setCreatedOn(p.getCreatedOn());
                    dto.setPrice(p.getPrice());
                    dto.setIsAvailable(p.getIsAvailable());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

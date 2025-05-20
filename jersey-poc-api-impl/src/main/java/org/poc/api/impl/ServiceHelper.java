//package org.poc.api.impl;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.criteria.*;
//import lombok.RequiredArgsConstructor;
//import org.poc.api.dto.ProductDTO;
//import org.poc.api.filter.ProductFilter;
//import org.poc.api.impl.entity.Product;
//import org.poc.api.impl.entity.Product_;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Component
//public class ServiceHelper {
//
//
//    private final EntityManager manager;
//
//    private final RequestPredicates predicates;
//    private boolean post;
//
//    public List<ProductDTO> generateResponse(ProductFilter filter, boolean post) {
//        this.post = post;
//        CriteriaBuilder builder = manager.getCriteriaBuilder();
//        CriteriaQuery<Product> query = builder.createQuery(Product.class);
//        Root<Product> root = query.from(Product.class);
//        if (filter.isCreatedOnYearSet()) {
//            Predicate predicate = predicates.getPredicateCreatedOnGivenYear(builder, root, filter.getCreatedOnYear());
//            return prepareResponse(builder, root, query, predicate);
//        } else if (filter.isCreatedBeforeDateSet() && !filter.isCreatedAfterDateSet() && !filter.isFindMax()) {
//            Predicate predicate = predicates.getCreatedBeforePredicate(builder, root, filter.getCreatedBeforeDate());
//            return prepareResponse(builder, root, query, predicate);
//        } else if (filter.isCreatedAfterDateSet()
//                && !filter.isCreatedBeforeDateSet()
//                && !filter.isPriceLessThanSet() && filter.isPriceGreaterThanSet()) {
//            Predicate predicate = predicates.getCreatedAfterPredicate(builder, root, filter.getCreatedAfterDate());
//            return prepareResponse(builder, root, query, predicate);
//        } else if (filter.isCreatedBeforeDateSet()
//                && filter.isCreatedAfterDateSet()
//                && !filter.isPriceGreaterThanSet()
//                && !filter.isPriceLessThanSet()
//                && filter.isFindMax()) {
//            Predicate predicate = predicates.getCreatedBetweenPredicate(builder, root, filter.getCreatedBeforeDate(), filter.getCreatedAfterDate());
//            return prepareResponse(builder, root, query, predicate);
//        } else if (filter.isPriceLessThanSet() && filter.isPriceGreaterThanSet()) {
//            Predicate predicate = predicates.getPriceBetweenPredicate(builder, root, filter.getPriceLessThan(), filter.getPriceGreaterThan());
//            return prepareResponse(builder, root, query, predicate);
//        } else if (filter.isPriceLessThanSet() && filter.isCreatedAfterDateSet()) {
//            Predicate predicate = predicates.getPriceLesserThanAndCreatedAfterPredicate(builder, root, filter.getPriceLessThan(), filter.getCreatedAfterDate());
//            return prepareResponse(builder, root, query, predicate);
//        } else if (filter.isCreatedAfterDateSet() && filter.isCreatedBeforeDateSet() && filter.isPriceGreaterThanSet()) {
//            Predicate predicate = predicates
//                    .getCreationDateInRangeAndPriceGreaterThan(builder,
//                            root,
//                            filter.getCreatedBeforeDate(),
//                            filter.getCreatedAfterDate(),
//                            filter.getPriceGreaterThan());
//            return prepareResponse(builder, root, query, predicate);
//        } else if (filter.isFindMax() && filter.isCreatedBeforeDateSet()) {
//            return getMaxPriceProduct(builder, query, root, filter.getCreatedBeforeDate());
//        } else if (filter.isPriceNullOrUnAvailable()) {
//            Predicate predicate = predicates.getPriceIsNullOrUnAvailablePredicate(builder, root);
//            return prepareResponse(builder, root, query, predicate);
//        } else if (filter.isCategorySet() && ! filter.getBelongsToCategory()) {
//            Predicate predicate = predicates.getNotBelongingToCategory(builder, root, filter.getCategory());
//            return prepareResponse(builder, root, query, predicate);
//        } else if (filter.isCategorySet()) {
//            Predicate particularCategory = predicates.getProductsBelongingToParticularCategory(builder, root, filter.getCategory());
//            Predicate datePredicate = builder.greaterThan(root.get(Product_.CREATED_ON), LocalDate.now().minusDays(12));
//            final var predicate = builder.and(particularCategory, datePredicate);
//            return prepareResponse(builder, root, query, predicate);
//        }
//     return null;
//    }
//
//    private List<ProductDTO> getMaxPriceProduct(CriteriaBuilder builder,
//                                                CriteriaQuery<Product> query,
//                                                Root<Product> root,
//                                                LocalDate beforeDate) {
//        Predicate predicate = predicates.getCreatedBeforePredicate(builder, root, beforeDate);
//        query.where(predicate)
//                .orderBy(builder.desc(root.get(Product_.PRICE)));
//        List<Product> products = manager.createQuery(query).getResultList();
//        final var product = products.get(products.size() - 1);
//        ProductDTO dto = new ProductDTO();
//        dto.setIsAvailable(product.getIsAvailable());
//        dto.setPrice(product.getPrice());
//        dto.setCategories(
//                product.getCategories().stream().map(c ->
//                        c.getCategory()
//                ).collect(Collectors.toList())
//        );
//        dto.setName(product.getName());
//        dto.setCreatedOn(product.getCreatedOn());
//        return List.of(dto);
//    }
//
//    private List<ProductDTO> prepareResponse(CriteriaBuilder builder, Root<Product> root, CriteriaQuery<Product> query, Predicate createdBeforePredicate) {
//        query.where(createdBeforePredicate);
//        query.orderBy(sortResponse(builder, root));
//        return generateListOfProductDto(manager.createQuery(query).getResultList());
//    }
//
//    private Order sortResponse(CriteriaBuilder builder,
//                               Root<Product> root) {
//        return post ? builder.asc(root.get(Product_.PRICE))
//                : builder.asc(root.get(Product_.NAME));
//    }
//
//    private List<ProductDTO> generateListOfProductDto(List<Product> products) {
//        return products
//                .stream()
//                .map(p -> {
//                    ProductDTO dto = new ProductDTO();
//                    dto.setCategories(
//                            p.getCategories().stream().map(c ->
//                                    c.getCategory()
//                            ).collect(Collectors.toList())
//                    );
//                    dto.setName(p.getName());
//                    dto.setCreatedOn(p.getCreatedOn());
//                    dto.setPrice(p.getPrice());
//                    dto.setIsAvailable(p.getIsAvailable());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//}

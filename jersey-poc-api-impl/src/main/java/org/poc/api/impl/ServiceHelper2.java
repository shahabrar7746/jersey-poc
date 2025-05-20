//package org.poc.api.impl;
//
//
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
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Component
//public class ServiceHelper2 {
//
//    private final EntityManager manager;
//    private final RequestPredicates predicates;
//
//    private boolean isPostRequest;
//
//    public List<ProductDTO> generateResponse(ProductFilter filter, boolean post) {
//        this.isPostRequest = post;
//
//        CriteriaBuilder cb = manager.getCriteriaBuilder();
//        CriteriaQuery<Product> query = cb.createQuery(Product.class);
//        Root<Product> root = query.from(Product.class);
//
//        // Handle special case separately (max price before date)
//        if (filter.isFindMax() && filter.isCreatedBeforeDateSet()) {
//            return getMaxPriceProduct(cb, query, root, filter.getCreatedBeforeDate());
//        }
//
//        // Compose all relevant predicates
//        List<Predicate> predicateList = buildPredicates(cb, root, filter);
//
//        if (predicateList.isEmpty()) {
//            // Optional: return empty list or all products
//            return List.of();
//        }
//
//        Predicate finalPredicate = cb.and(predicateList.toArray(new Predicate[0]));
//        query.where(finalPredicate);
//        query.orderBy(getSortOrder(cb, root));
//
//        List<Product> products = manager.createQuery(query).getResultList();
//        return convertToDTOs(products);
//    }
//
//    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Product> root, ProductFilter filter) {
//        List<Predicate> predicatesList = new ArrayList<>();
//
//        if (filter.isCreatedOnYearSet()) {
//            predicatesList.add(predicates.getPredicateCreatedOnGivenYear(cb, root, filter.getCreatedOnYear()));
//        }
//
//        if (filter.isCreatedBeforeDateSet() && !filter.isFindMax()) {
//            predicatesList.add(predicates.getCreatedBeforePredicate(cb, root, filter.getCreatedBeforeDate()));
//        }
//
//        if (filter.isCreatedAfterDateSet()) {
//            predicatesList.add(predicates.getCreatedAfterPredicate(cb, root, filter.getCreatedAfterDate()));
//        }
//
//        if (filter.isCreatedAfterDateSet() && filter.isCreatedBeforeDateSet() && filter.isPriceGreaterThanSet()) {
//            predicatesList.add(predicates.getCreationDateInRangeAndPriceGreaterThan(
//                    cb, root, filter.getCreatedBeforeDate(), filter.getCreatedAfterDate(), filter.getPriceGreaterThan()
//            ));
//        }
//
//        if (filter.isPriceLessThanSet() && filter.isPriceGreaterThanSet()) {
//            predicatesList.add(predicates.getPriceBetweenPredicate(cb, root, filter.getPriceLessThan(), filter.getPriceGreaterThan()));
//        }
//
//        if (filter.isPriceLessThanSet() && filter.isCreatedAfterDateSet()) {
//            predicatesList.add(predicates.getPriceLesserThanAndCreatedAfterPredicate(
//                    cb, root, filter.getPriceLessThan(), filter.getCreatedAfterDate()
//            ));
//        }
//
//        if (filter.isPriceNullOrUnAvailable()) {
//            predicatesList.add(predicates.getPriceIsNullOrUnAvailablePredicate(cb, root));
//        }
//
//        if (filter.isCategorySet()) {
//            if (!filter.getBelongsToCategory()) {
//                predicatesList.add(predicates.getNotBelongingToCategory(cb, root, filter.getCategory()));
//            } else {
//                Predicate categoryPredicate = predicates.getProductsBelongingToParticularCategory(cb, root, filter.getCategory());
//                Predicate recentPredicate = cb.greaterThan(root.get(Product_.CREATED_ON), LocalDate.now().minusDays(25));
//                predicatesList.add(cb.and(categoryPredicate, recentPredicate));
//            }
//        }
//
//        return predicatesList;
//    }
//
//    private List<ProductDTO> getMaxPriceProduct(CriteriaBuilder cb,
//                                                CriteriaQuery<Product> query,
//                                                Root<Product> root,
//                                                LocalDate beforeDate) {
//        Predicate predicate = predicates.getCreatedBeforePredicate(cb, root, beforeDate);
//        query.where(predicate);
//        query.orderBy(cb.desc(root.get(Product_.PRICE)));
//
//        List<Product> products = manager.createQuery(query).getResultList();
//
//        if (products.isEmpty()) return List.of();
//
//        Product topProduct = products.get(0); // max price is first due to desc order
//
//        return List.of(convertToDTO(topProduct));
//    }
//
//    private Order getSortOrder(CriteriaBuilder cb, Root<Product> root) {
//        return isPostRequest
//                ? cb.asc(root.get(Product_.PRICE))
//                : cb.asc(root.get(Product_.NAME));
//    }
//
//    private List<ProductDTO> convertToDTOs(List<Product> products) {
//        return products.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    private ProductDTO convertToDTO(Product product) {
//        ProductDTO dto = new ProductDTO();
//        dto.setName(product.getName());
//        dto.setCreatedOn(product.getCreatedOn());
//        dto.setPrice(product.getPrice());
//        dto.setIsAvailable(product.getIsAvailable());
//        dto.setCategories(product.getCategories().stream()
//                .map(c -> c.getCategory())
//                .collect(Collectors.toList()));
//        return dto;
//    }
//}

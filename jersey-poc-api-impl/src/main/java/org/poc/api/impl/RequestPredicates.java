package org.poc.api.impl;

import jakarta.persistence.criteria.*;
import org.poc.api.impl.entity.Category;
import org.poc.api.impl.entity.Category_;
import org.poc.api.impl.entity.Product;
import org.poc.api.impl.entity.Product_;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RequestPredicates {


    public Predicate getCreatedBeforePredicate(CriteriaBuilder builder,
                                               Root<Product> root,
                                               LocalDate createdBefore) {
        return builder.lessThan(root.get(Product_.CREATED_ON), createdBefore);
    }
    public Predicate getCreatedAfterPredicate(CriteriaBuilder builder,
                                              Root<Product> root,
                                              LocalDate createdAfter) {
        return builder.greaterThan(root.get(Product_.CREATED_ON), createdAfter);
    }
    public Predicate getCreatedBetweenPredicate(CriteriaBuilder builder,
                                                Root<Product> root,
                                                LocalDate createdAfter,
                                                LocalDate createdBefore) {
        return builder.between(root.get(Product_.CREATED_ON), createdAfter, createdBefore);
    }

    public Predicate getPriceBetweenPredicate(CriteriaBuilder builder,
                                              Root<Product> root,
                                              Integer priceLesserThan,
                                              Integer priceGreaterThan)
    {
        return builder.between(root.get(Product_.PRICE), priceGreaterThan, priceLesserThan);
    }
    public Predicate getPriceLesserThanAndCreatedAfterPredicate(CriteriaBuilder builder,
                                                                Root<Product> root,
                                                                Integer lesserThan,
                                                                LocalDate createdAfter)
    {
        final var createdAfterPredicate = getCreatedAfterPredicate(builder, root, createdAfter);
        final var priceLesserThanePredicate = builder.lessThan(root.get(Product_.PRICE), lesserThan);
        return builder.or(createdAfterPredicate, priceLesserThanePredicate);

    }
    public Predicate getCreationDateInRangeAndPriceGreaterThan(CriteriaBuilder builder,
                                                               Root<Product> root,
                                                               LocalDate createdBefore,
                                                               LocalDate createdAfter,
                                                               Integer priceGreaterThan)
    {
        final  var createdInRangePredicate = getCreatedBetweenPredicate(builder, root, createdAfter, createdBefore);
        final var priceGreaterThanPredicate = builder.greaterThan(root.get(Product_.PRICE), priceGreaterThan);
        return builder.and(createdInRangePredicate, priceGreaterThanPredicate);
    }

    public Predicate getPriceIsNullOrUnAvailablePredicate(CriteriaBuilder builder,
                                                          Root<Product> root)
    {
        final var priceIsNullPredicate = builder.isNull(root.get(Product_.PRICE));
        final var unAvailablePredicate = builder.equal(root.get(Product_.IS_AVAILABLE), Boolean.FALSE);
        return builder.or(priceIsNullPredicate, unAvailablePredicate);
    }

    public Predicate getNotBelongingToCategory(CriteriaBuilder builder,
                                               Root<Product> root,
                                               String category)
    {
        final var notBelongingToCategory = builder.notEqual(root.get(Product_.CATEGORIES), category);
        return notBelongingToCategory;
    }

  public Predicate getCreatedOnWeekendPredicate(CriteriaBuilder builder,
                                                Root<Product> root)
  {
      Expression<Integer> weekendExpression = builder.function("DAYOFWEEK", Integer.class, root.get(Product_.CREATED_ON));
      return weekendExpression.in(1, 7);
  }

  public Predicate getProductsBelongingToParticularCategory(CriteriaBuilder builder,
                                                            Root<Product> root,
                                                            String category)
  {
      Join<Product, Category> categoryJoin = root.join(Product_.categories);
     return builder.equal(categoryJoin.get(Category_.category), category);

  }

  public Predicate getPredicateCreatedOnGivenYear(CriteriaBuilder builder,
                                                  Root<Product> root,
                                                  Integer year)
  {
      String pattern = String.valueOf(year) + "%";
      Expression<String> yearExpr = builder.function("TO_CHAR", String.class, root.get(Product_.CREATED_ON), builder.literal("YYYY"));
      return builder.like((yearExpr), pattern);
  }


}

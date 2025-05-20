package org.poc.api.filter;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.QueryParam;

import java.time.LocalDate;

public class ProductFilter {

    @QueryParam("created-after")
    @PastOrPresent(message = "Date must be less or equal to today's date")
    private LocalDate createdAfterDate;

    @QueryParam("created-on-year")
    @Positive(message = "Year must be integer and positive")
    private Integer createdOnYear;

    @QueryParam("created-before")
    @Past(message = "Created date must be less than today")
    private LocalDate createdBeforeDate;

    @QueryParam("price-less-than")
    @Positive(message = "Price must be integer and positive")
    private Integer priceLessThan;

    @QueryParam("price-greater-than")
    @Positive(message = "Price must be integer and positive")
    private Integer priceGreaterThan;

    @QueryParam("category")
    private String category;

    @QueryParam("created-on-weekend")
    private Boolean createdOnWeekEnd;

    @QueryParam("created-on-month")
    private String createdOnSameMonthDifferentYear;

    @QueryParam("columns")
    private String columns;

    @QueryParam("price-null-or-unavailable")
    private Boolean priceNullOrUnAvailable;

    @QueryParam("belongs")
    private Boolean belongsToCategory;

    @QueryParam("find-max-before-date")
    private Boolean findMax;



    public LocalDate getCreatedAfterDate() {
        return createdAfterDate;
    }

    public boolean hasCreatedAfterDate() {
        return createdAfterDate != null;
    }

    public Integer getCreatedOnYear() {
        return createdOnYear;
    }

    public boolean hasCreatedOnYear() {
        return createdOnYear != null;
    }

    public LocalDate getCreatedBeforeDate() {
        return createdBeforeDate;
    }

    public boolean hasCreatedBeforeDate() {
        return createdBeforeDate != null;
    }

    public Integer getPriceLessThan() {
        return priceLessThan;
    }

    public boolean hasPriceLessThan() {
        return priceLessThan != null;
    }

    public Integer getPriceGreaterThan() {
        return priceGreaterThan;
    }

    public boolean hasPriceGreaterThan() {
        return priceGreaterThan != null;
    }

    public String getCategory() {
        return category;
    }

    public boolean hasCategory() {
        return category != null && !category.isBlank();
    }

    public Boolean getCreatedOnWeekEnd() {
        return createdOnWeekEnd;
    }

    public boolean hasCreatedOnWeekEnd() {
        return createdOnWeekEnd != null;
    }

    public String getCreatedOnSameMonthDifferentYear() {
        return createdOnSameMonthDifferentYear;
    }

    public boolean hasCreatedOnSameMonthDifferentYear() {
        return createdOnSameMonthDifferentYear != null && !createdOnSameMonthDifferentYear.isBlank();
    }

    public String getColumns() {
        return columns;
    }

    public boolean hasColumns() {
        return columns != null && !columns.isBlank();
    }

    public Boolean getPriceNullOrUnAvailable() {
        return priceNullOrUnAvailable;
    }

    public boolean hasPriceNullOrUnAvailable() {
        return priceNullOrUnAvailable != null && priceNullOrUnAvailable;
    }

    public Boolean getBelongsToCategory() {
        return belongsToCategory;
    }

    public boolean hasBelongsToCategory() {
        return belongsToCategory != null;
    }

    public Boolean getFindMax() {
        return findMax;
    }

    public boolean hasFindMax() {
        return findMax != null && findMax;
    }
}

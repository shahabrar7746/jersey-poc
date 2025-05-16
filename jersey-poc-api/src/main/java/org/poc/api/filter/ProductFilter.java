package org.poc.api.filter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class ProductFilter {


    @Valid
    private List<@PastOrPresent(message = "Date must be lessser or equals to today's date") LocalDate> createdAfterDate;
    private boolean createdAfterDateSet;

    @Valid
    private List<@Positive(message = "Year must be integer and positive") Integer> createdOnYear;
    private boolean createdOnYearSet;

    @Valid
    private List<@Past(message = "Created date must be lesser than day") LocalDate> createdBeforeDate;
    private boolean createdBeforeDateSet;

    @Valid
    private List<@Positive(message = "Price must be integer and positive") Integer> priceLessThan;
    private boolean priceLessThanSet;

    @Valid
    private List<@Positive(message = "Price must be integer and positive") Integer> priceGreaterThan;
    private boolean priceGreaterThanSet;

    private List<String> productsNotBelongsToParticularCategory;
    private boolean productsNotBelongsToParticularCategorySet;

    private boolean createdOnWeekEnd;
    private boolean createdOnWeekEndSet;

    private List<String> createdOnSameMonthDifferentYear;
    private boolean createdOnSameMonthDifferentYearSet;

    private List<String> columns;
    private boolean columnsSet;


    private boolean priceNullOrUnAvailable;

    public List<LocalDate> getCreatedAfterDate() {
        if (createdAfterDate == null || createdAfterDate.isEmpty()) {
            createdAfterDateSet = false;
        }
        return createdAfterDate;
    }

    public void setCreatedAfterDate(List<LocalDate> createdAfterDate) {
        this.createdAfterDate = createdAfterDate;
        this.createdAfterDateSet = true;
    }

    public boolean isCreatedAfterDateSet() {
        return createdAfterDateSet;
    }

    public List<Integer> getCreatedOnYear() {
        if (createdOnYear == null || createdOnYear.isEmpty()) {
            createdOnYearSet = false;
        }
        return createdOnYear;
    }

    public void setCreatedOnYear(List<Integer> createdOnYear) {
        this.createdOnYear = createdOnYear;
        this.createdOnYearSet = true;
    }

    public boolean isCreatedOnYearSet() {
        return createdOnYearSet;
    }

    public List<LocalDate> getCreatedBeforeDate() {
        if (createdBeforeDate == null || createdBeforeDate.isEmpty()) {
            createdBeforeDateSet = false;
        }
        return createdBeforeDate;
    }

    public void setCreatedBeforeDate(List<LocalDate> createdBeforeDate) {
        this.createdBeforeDate = createdBeforeDate;
        this.createdBeforeDateSet = true;
    }

    public boolean isCreatedBeforeDateSet() {
        return createdBeforeDateSet;
    }

    public List<Integer> getPriceLessThan() {
        if (priceLessThan == null || priceLessThan.isEmpty()) {
            priceLessThanSet = false;
        }
        return priceLessThan;
    }

    public void setPriceLessThan(List<Integer> priceLessThan) {
        this.priceLessThan = priceLessThan;
        this.priceLessThanSet = true;
    }

    public boolean isPriceLessThanSet() {
        return priceLessThanSet;
    }

    public List<Integer> getPriceGreaterThan() {
        if (priceGreaterThan == null || priceGreaterThan.isEmpty()) {
            priceGreaterThanSet = false;
        }
        return priceGreaterThan;
    }

    public void setPriceGreaterThan(List<Integer> priceGreaterThan) {
        this.priceGreaterThan = priceGreaterThan;
        this.priceGreaterThanSet = true;
    }

    public boolean isPriceGreaterThanSet() {
        return priceGreaterThanSet;
    }

    public List<String> getProductsNotBelongsToParticularCategory() {
        if (productsNotBelongsToParticularCategory == null || productsNotBelongsToParticularCategory.isEmpty()) {
            productsNotBelongsToParticularCategorySet = false;
        }
        return productsNotBelongsToParticularCategory;
    }

    public void setProductsNotBelongsToParticularCategory(List<String> productsNotBelongsToParticularCategory) {
        this.productsNotBelongsToParticularCategory = productsNotBelongsToParticularCategory;
        this.productsNotBelongsToParticularCategorySet = true;
    }

    public boolean isProductsNotBelongsToParticularCategorySet() {
        return productsNotBelongsToParticularCategorySet;
    }

    public boolean isCreatedOnWeekEnd() {
        return createdOnWeekEnd;
    }

    public void setCreatedOnWeekEnd(boolean createdOnWeekEnd) {
        this.createdOnWeekEnd = createdOnWeekEnd;
        this.createdOnWeekEndSet = true;
    }

    public boolean isCreatedOnWeekEndSet() {
        return createdOnWeekEndSet;
    }

    public List<String> getCreatedOnSameMonthDifferentYear() {
        if (createdOnSameMonthDifferentYear == null || createdOnSameMonthDifferentYear.isEmpty()) {
            createdOnSameMonthDifferentYearSet = false;
        }
        return createdOnSameMonthDifferentYear;
    }

    public void setCreatedOnSameMonthDifferentYear(List<String> createdOnSameMonthDifferentYear) {
        this.createdOnSameMonthDifferentYear = createdOnSameMonthDifferentYear;
        this.createdOnSameMonthDifferentYearSet = true;
    }

    public boolean isCreatedOnSameMonthDifferentYearSet() {
        return createdOnSameMonthDifferentYearSet;
    }

    public List<String> getColumns() {
        if (columns == null || columns.isEmpty()) {
            columnsSet = false;
        }
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
        this.columnsSet = true;
    }

    public boolean isColumnsSet() {
        return columnsSet;
    }
    public boolean isPriceNullOrUnAvailable() {
        return priceNullOrUnAvailable;
    }

    public void setPriceNullOrUnAvailable(boolean priceNullOrUnAvailable) {
        this.priceNullOrUnAvailable = priceNullOrUnAvailable;
    }

}

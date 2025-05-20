package org.poc.api.filter;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Provider
public class LocalDateParamConverterProvider implements ParamConverterProvider {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    @SuppressWarnings("unchecked")
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.equals(LocalDate.class)) {
            return new ParamConverter<>() {
                @Override
                public T fromString(String value) {
                    if (value == null || value.isBlank()) return null;
                    return (T) LocalDate.parse(value, formatter);
                }

                @Override
                public String toString(T value) {
                    return ((LocalDate) value).format(formatter);
                }
            };
        }
        return null;
    }
}

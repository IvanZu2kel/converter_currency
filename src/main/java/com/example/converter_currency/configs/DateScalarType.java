package com.example.converter_currency.configs;

import graphql.Scalars;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateScalarType extends GraphQLScalarType {
    public DateScalarType() {
        super("Date", "Date TIME", new Coercing() {
            @Override
            public Object serialize(Object o) throws CoercingSerializeException {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                return formatter.format((LocalDate)o);
            }

            @Override
            public Object parseValue(Object o) throws CoercingParseValueException {
                return o;
            }

            @Override
            public Object parseLiteral(Object o) throws CoercingParseLiteralException {
                if (o==null){
                    return null;
                }
                return o.toString();
            }
        });
    }

    @Bean
    public GraphQLScalarType longType() {
        return Scalars.GraphQLLong;
    }
}

package io.moderne.organizations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;
import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class CustomScalarsConfiguration {

    @DgsScalar(name = "DateTime")
    public static class OffsetDateTimeScalar implements Coercing<OffsetDateTime, String> {
        @Override
        public OffsetDateTime parseValue(Object input) throws CoercingParseValueException {
            return OffsetDateTime.parse(input.toString(), DateTimeFormatter.ISO_DATE_TIME);
        }

        @Override
        public OffsetDateTime parseLiteral(Object input) throws CoercingParseLiteralException {
            if (input instanceof StringValue) {
                return OffsetDateTime.parse(((StringValue) input).getValue(), DateTimeFormatter.ISO_DATE_TIME);
            }

            throw new CoercingParseLiteralException("Value is not a valid ISO date time");
        }

        @Override
        public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
            if (dataFetcherResult instanceof OffsetDateTime) {
                return ((OffsetDateTime) dataFetcherResult).format(DateTimeFormatter.ISO_DATE_TIME);
            } else {
                throw new CoercingSerializeException("Not a valid DateTime");
            }
        }
    }

    @DgsComponent
    public static class ObjectScalar {
        @DgsRuntimeWiring
        public RuntimeWiring.Builder addScalar(RuntimeWiring.Builder builder) {
            return builder.scalar(ExtendedScalars.Object);
        }
    }
}

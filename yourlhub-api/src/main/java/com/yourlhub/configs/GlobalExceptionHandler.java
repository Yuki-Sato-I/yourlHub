package com.yourlhub.configs;

import com.yourlhub.domain.enums.ErrorTypes;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GlobalExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof IllegalArgumentException illegalArgumentException) {
            return GraphqlErrorBuilder.newError(env)
                    .errorType(ErrorTypes.BAD_REQUEST)
                    .message(illegalArgumentException.getMessage())
                    .extensions(Map.of(
                            "errorCode", "errorCode.getShortCode()",
                            "errorMessage", "this is example message."
                    ))
                    .build();
        }

//        if (ex instanceof ConstraintViolationException constraintViolationException) {
//            return validationError(constraintViolationException, env);
//        }

        // other exceptions not yet caught
        return GraphqlErrorBuilder.newError(env)
                .message("Error occurred: Ensure request is valid ")
                .errorType(ErrorTypes.BAD_REQUEST)
                .build();
    }

//    private GraphQLError validationError(ConstraintViolationException exception, DataFetchingEnvironment env){
//        String invalidFields = exception.getConstraintViolations().stream()
//                .map(ConstraintViolation::getMessage)
//                .collect(Collectors.joining("\n"));
//
//        return GraphqlErrorBuilder.newError(env)
//                .errorType(ErrorTypes.BAD_REQUEST)
//                .message(invalidFields)
//                .build();
//    }
}

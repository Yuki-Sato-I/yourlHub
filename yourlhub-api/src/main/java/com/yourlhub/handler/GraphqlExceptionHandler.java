package com.yourlhub.handler;

import com.yourlhub.domain.enums.ErrorTypes;
import com.yourlhub.domain.exceptions.GraphqlException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GraphqlExceptionHandler implements DataFetcherExceptionResolver {

  private static final Logger logger = LoggerFactory.getLogger(GraphqlExceptionHandler.class);

  @Override
  public Mono<List<GraphQLError>> resolveException(
      Throwable exception, DataFetchingEnvironment environment) {
    final GraphqlException gex = toGraphqlException(exception);
    logger.error(gex.getMessage());
    return Mono.just(
        List.of(
            GraphqlErrorBuilder.newError()
                .message(gex.getMessage())
                .errorType(gex.getErrorTypes())
                .extensions(gex.getExtensions())
                .build()));
  }

  private GraphqlException toGraphqlException(Throwable throwable) {
    return GraphqlException.class.equals(throwable.getClass())
        ? (GraphqlException) throwable
        : new GraphqlException(
            ErrorTypes.INTERNAL_ERROR, throwable.getMessage(), Collections.emptyMap());
  }
}

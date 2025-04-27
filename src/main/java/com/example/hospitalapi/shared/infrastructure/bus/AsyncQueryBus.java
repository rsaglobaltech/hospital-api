package com.example.hospitalapi.shared.infrastructure.bus;

import com.example.hospitalapi.shared.domain.bus.Query;
import com.example.hospitalapi.shared.domain.bus.QueryBus;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import com.example.hospitalapi.shared.infrastructure.exception.HandlerClassResolutionException;
import com.example.hospitalapi.shared.infrastructure.exception.QueryHandlerNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Asynchronous implementation of the QueryBus
 */
@Service
public class AsyncQueryBus implements QueryBus {

    private final Executor executor;
    private final Map<Class<? extends Query<?>>, QueryHandler<? extends Query<?>, ?>> handlers;

    public AsyncQueryBus(ApplicationContext context, @Qualifier("queryExecutor") Executor executor) {
        this.executor = executor;
        this.handlers = new HashMap<>();

        // Register all query handlers
        context.getBeansOfType(QueryHandler.class).values().forEach(this::registerHandler);
    }

    @Override
    public <R, Q extends Query<R>> CompletableFuture<R> dispatch(Q query) {
        return CompletableFuture.supplyAsync(() -> {
            @SuppressWarnings("unchecked")
            QueryHandler<Q, R> handler = (QueryHandler<Q, R>) handlers.get(query.getClass());

            if (handler == null) {
                throw new QueryHandlerNotFoundException(query);
            }

            return handler.handle(query);
        }, executor);
    }

    private <Q extends Query<R>, R> void registerHandler(QueryHandler<Q, R> handler) {
        Class<Q> queryClass = getQueryClass(handler);
        handlers.put(queryClass, handler);
    }

    @SuppressWarnings("unchecked")
    private <Q extends Query<R>, R> Class<Q> getQueryClass(QueryHandler<Q, R> handler) {
        // Try to find the query class from the handler's class hierarchy
        Class<?> currentClass = handler.getClass();

        while (currentClass != null) {
            // Check interfaces directly implemented by this class
            for (Type genericInterface : currentClass.getGenericInterfaces()) {
                if (genericInterface instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericInterface;

                    // Check if this interface is QueryHandler or extends QueryHandler
                    if (QueryHandler.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
                        return (Class<Q>) parameterizedType.getActualTypeArguments()[0];
                    }
                }
            }

            // Move up to the superclass
            currentClass = currentClass.getSuperclass();
        }

        throw new HandlerClassResolutionException(handler.getClass());
    }
}

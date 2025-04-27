package com.example.hospitalapi.shared.infrastructure.bus;

import com.example.hospitalapi.shared.domain.bus.Command;
import com.example.hospitalapi.shared.domain.bus.CommandBus;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import com.example.hospitalapi.shared.infrastructure.exception.CommandHandlerNotFoundException;
import com.example.hospitalapi.shared.infrastructure.exception.HandlerClassResolutionException;
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
 * Asynchronous implementation of the CommandBus
 */
@Service
public class AsyncCommandBus implements CommandBus {

    private final Executor executor;
    private final Map<Class<? extends Command>, CommandHandler<? extends Command, ?>> handlers;

    public AsyncCommandBus(ApplicationContext context, @Qualifier("commandExecutor") Executor executor) {
        this.executor = executor;
        this.handlers = new HashMap<>();

        // Register all command handlers
        context.getBeansOfType(CommandHandler.class).values().forEach(this::registerHandler);
    }

    @Override
    public <R, C extends Command> CompletableFuture<R> dispatch(C command) {
        return CompletableFuture.supplyAsync(() -> {
            @SuppressWarnings("unchecked")
            CommandHandler<C, R> handler = (CommandHandler<C, R>) handlers.get(command.getClass());

            if (handler == null) {
                throw new CommandHandlerNotFoundException(command);
            }

            return handler.handle(command);
        }, executor);
    }

    private <C extends Command, R> void registerHandler(CommandHandler<C, R> handler) {
        Class<C> commandClass = getCommandClass(handler);
        handlers.put(commandClass, handler);
    }

    @SuppressWarnings("unchecked")
    private <C extends Command, R> Class<C> getCommandClass(CommandHandler<C, R> handler) {
        // Try to find the command class from the handler's class hierarchy
        Class<?> currentClass = handler.getClass();

        while (currentClass != null) {
            // Check interfaces directly implemented by this class
            for (Type genericInterface : currentClass.getGenericInterfaces()) {
                if (genericInterface instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericInterface;

                    // Check if this interface is CommandHandler or extends CommandHandler
                    if (CommandHandler.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
                        return (Class<C>) parameterizedType.getActualTypeArguments()[0];
                    }
                }
            }

            // Move up to the superclass
            currentClass = currentClass.getSuperclass();
        }

        throw new HandlerClassResolutionException(handler.getClass());
    }
}

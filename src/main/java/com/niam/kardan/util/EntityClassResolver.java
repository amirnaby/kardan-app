package com.niam.kardan.util;

import com.niam.kardan.model.basedata.BaseData;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EntityClassResolver {
    private final Map<String, Class<? extends BaseData>> cache = new ConcurrentHashMap<>();

    /**
     * Resolve by simple class name, e.g. "ExecutionStatus".
     * Throws IllegalArgumentException if not found or not a BaseData subclass.
     */
    @SuppressWarnings("unchecked")
    public Class<? extends BaseData> resolve(String simpleName) {
        return cache.computeIfAbsent(simpleName, name -> {
            try {
                String fqcn = "com.niam.kardan.model.basedata." + name;
                Class<?> clazz = ClassUtils.forName(fqcn, Thread.currentThread().getContextClassLoader());
                if (!BaseData.class.isAssignableFrom(clazz)) {
                    throw new IllegalArgumentException(name + " is not a BaseData subclass");
                }
                return (Class<? extends BaseData>) clazz;
            } catch (ClassNotFoundException | LinkageError ex) {
                throw new IllegalArgumentException("BaseData entity not found: " + name, ex);
            }
        });
    }
}
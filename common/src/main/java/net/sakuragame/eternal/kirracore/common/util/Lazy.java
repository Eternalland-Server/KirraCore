package net.sakuragame.eternal.kirracore.common.util;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

public final class Lazy<T> {

    private volatile T value;
    private volatile Supplier<T> factory;
    private final Object lock = new Object();

    public Lazy(Supplier<T> factory) {
        this.value = null;
        this.factory = factory;
    }

    public Lazy(T value) {
        this.value = value;
        this.factory = null;
    }

    @Nullable
    public T get() {
        if (factory != null) {
            synchronized (lock) {
                if (factory != null) {
                    value = factory.get();
                    factory = null;
                }
            }
        }

        return value;
    }

    @Override
    public String toString() {
        return Objects.toString(get());
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Lazy) && Objects.equals(get(), ((Lazy<?>) object).get());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(get());
    }
}
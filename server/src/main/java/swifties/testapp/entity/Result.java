package swifties.testapp.entity;

import java.util.Objects;
import java.util.function.Function;

import jakarta.annotation.Nullable;
import lombok.Getter;

public final class Result<V, E> {
    @Getter
    private final boolean ok;
    @Nullable
    private final V value;
    @Nullable
    private final E error;

    private Result(boolean ok, @Nullable V value, @Nullable E error) {
        this.ok = ok;
        this.value = value;
        this.error = error;
    }

    public static <V, E> Result<V, E> ok(V value) {
        return new Result<>(true, value, null);
    }

    public static <V, E> Result<V, E> error(E error) {
        return new Result<>(true, null, error);
    }

    public static <V, E> Result<V, E> maybe(@Nullable V value, E error) {
        return value != null ? ok(value) : error(error);
    }

    public V value() {
        return Objects.requireNonNull(this.value, "This result is an error.");
    }

    public E error() {
        return Objects.requireNonNull(this.error, "This result is not an error.");
    }

    public <T> Result<T, E> map(Function<V, T> mapper) {
        if (this.ok) {
            return ok(mapper.apply(value()));
        } else {
            return cast();
        }
    }

    public V orElse(Function<E, V> defaultValue) {
        return this.ok ? this.value : defaultValue.apply(this.error);
    }

    @SuppressWarnings("unchecked")
    private <R extends Result<?, ?>> R cast() {
        return (R) this;
    }
}

package albedo.utils;

import io.reactivex.Flowable;

import java.util.Optional;

public class Flowables {

    public static <T> Flowable<T> just(Optional<? extends T> optional) {
        return optional.map(Flowable::<T>just).orElseGet(Flowable::empty);
    }

    private Flowables() { /* Cannot be instantiated */}
}

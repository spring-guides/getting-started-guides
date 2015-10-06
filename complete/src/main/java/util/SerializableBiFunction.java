package util;

import java.io.Serializable;
import java.util.function.BiFunction;

public interface SerializableBiFunction<T, U, R> extends BiFunction<T, U, R>, Serializable {

}

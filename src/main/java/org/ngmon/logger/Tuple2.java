package org.ngmon.logger;

public class Tuple2<T0, T1> {

    public T0 f0;
    public T1 f1;

    public Tuple2(T0 value0, T1 value1) {
        this.f0 = value0;
        this.f1 = value1;
    }

    @Override
    public String toString() {
        return "Tuple2{" +
                "f0=" + f0 +
                ", f1=" + f1 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple2)) return false;

        Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;

        return f0.equals(tuple2.f0) && f1.equals(tuple2.f1);

    }

    @Override
    public int hashCode() {
        int result = f0.hashCode();
        result = 31 * result + f1.hashCode();
        return result;
    }
}
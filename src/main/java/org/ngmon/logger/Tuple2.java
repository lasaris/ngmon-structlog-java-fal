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
}
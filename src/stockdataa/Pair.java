package stockdataa;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class Pair<A,B> {
  public final A a;
  public final B b;

  // in the case of buydate maps. A is always the shares and b is always the comission

  public Pair(A a, B b) {
    this.a = a;
    this.b = b;
  }

  public Pair<Double,Double> add(Pair<Double,Double> other) {
    double myA = (Double) this.a + other.a;
    double myB = (Double) this.b + other.b;

    return new Pair<Double, Double>(myA, myB);
  }

  public Pair<Double,Double> addBMinusA(Pair<Double,Double> other) {
    double myA = (Double) this.a - other.a;
    double myB = (Double) this.b + other.b;

    return new Pair<Double, Double>(myA, myB);
  }

  /*
  public A getA() {
    return a;
  }

  public B getB() {
    return b;
  }

   */
}
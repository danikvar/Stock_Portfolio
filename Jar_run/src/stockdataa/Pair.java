package stockdataa;

/**
 * Class that holds a pair of values of any type.
 * @param <A> The first object to hold.
 * @param <B> The second object to hold.
 */
public class Pair<A,B> {
  public final A a;
  public final B b;

  // in the case of buydate maps. A is always the shares and b is always the comission

  public Pair(A a, B b) {
    this.a = a;
    this.b = b;
  }

  /**
   * Adds a pair of Doubles together with another pair of Doubles by summing the
   * respective objects together.
   * @param other a Double pair to add.
   * @return A new summed pair.
   */
  public Pair<Double,Double> add(Pair<Double,Double> other) {
    double myA = (Double) this.a + other.a;
    double myB = (Double) this.b + other.b;

    return new Pair<Double, Double>(myA, myB);
  }

  /**
   * Sums the second object and subtracts the first with another double pair.
   * @param other A double pair to perform the operation.
   * @return A new pair with the operation performed on A and B.
   */
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
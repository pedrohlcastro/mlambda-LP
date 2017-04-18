package Model;

public class ConstArrayValue extends ArrayValue {

  private Array array;

  public ConstArrayValue(Array array) {
    super(-1);
    this.array = array;
  }


  public Array value() {
    return array;
  }
}



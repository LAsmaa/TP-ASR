import java.io.Serializable;

public enum Pouvoirs implements Serializable{
  PLUS_DEUX("PLus deux"),
  INVERSEMENT("Inversemment"),
  PASSE_TON_TOUR("Passe ton tour"),
  JORKER("Joker"),
  PLUS_QUATRE("Plus quatre");

  private String value;

  Pouvoirs(final String value){
    this.value = value;
  }

  public String getValue(){
    return value;
  }

}

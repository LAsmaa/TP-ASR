import java.io.Serializable;

public class Carte implements Serializable {
  public String name;

  public Carte(String name){
    this.name = name;
  }

  public String getName(){
    return this.name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String toString(){
    return "Carte : "+ getName();
  }

}

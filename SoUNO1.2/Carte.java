import java.io.Serializable;

public class Carte implements Serializable {
  public String pouvoir;
  public String couleur;
  public int numero;

  public Carte(String pouvoir, String couleur){
    this.pouvoir = pouvoir;
    this.couleur = couleur;
  }

  public Carte(int numero, String couleur){
    this.numero = numero;
    this.couleur = couleur;
  }

  public String getPouvoir(){
    return this.pouvoir;
  }

  public void setPouvoir(String pouvoir){
    this.pouvoir = pouvoir;
  }

  public String getCouleur (){
    return this.couleur;
  }

  public void setCouleur(String couleur){
    this.couleur = couleur;
  }

  public int getNumero(){
    return this.numero;
  }

  public void setNumero(int numero){
    this.numero = numero;
  }

  public String toString(){
    if (pouvoir != null){
      return "Carte: "+ getPouvoir() + " de couleur " + getCouleur();
    }else return "Carte numero: "+ getNumero() + " de couleur " + getCouleur();

  }

}

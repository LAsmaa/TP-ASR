

public class Carte {

  //Check if la carte est spéciale, On lui donnera : Couleur + chiffre si oui Spéciale si non
  private Boolean  Special;

  //Les attributs des cartes
  private Couleurs C = null;
  private Pouvoirs P = null;
  //private Numero N = null;


  // Constructeur
  public Carte (Boolean Special, Couleurs C, Pouvoirs P){

    //Check si on lui donne une couleur de toute façons par exemple l'inverse à un pouvoir et une Couleur
    //La carte !!!!!!!!!!!=' et joker n'ont pas de couleur du coup ça reste à NULL
    if(C != null){
      this.C = C;
    }

    //Check si c'est une spéciale ou une normale et aviser
    if (Special){
      this.P = P;
    }else {
      //this.N = N;
    }
  }

  public Boolean getSpeciale (){
    return this.Special;
  }
  public String getPouvoir (){
    return this.P.getValue();
  }
}

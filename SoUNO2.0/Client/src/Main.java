//Interface imports
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;



public class Main extends Application {

    @Override
    public void start(Stage windows) throws Exception{

        Joueur joueur = new Joueur();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        //*** SCENE ACCEUIL ***//

        //Creating elements
        //For scene1
        Image UNO = new Image(new FileInputStream("/home/asmaa/Documents/TP ASR/Cartes/Uno-Game.png"));
        ImageView imageViewUNO = new ImageView(UNO);
        imageViewUNO.setX(200);
        Text Texte1 = new Text("Nouvelle partie");
        Text Texte2 = new Text("Votre nom: ");
        TextField textField1 = new TextField();
        Button button1 = new Button("Lancer");

        //Creationg my gride pane for scene 1
        GridPane gridePane_1 = new GridPane();

        //Setting the gride pane
        gridePane_1.setMinSize(250,350);
        gridePane_1.setPadding(new Insets(10,10,10,10));
        gridePane_1.setVgap(5);
        gridePane_1.setHgap(5);
        gridePane_1.setAlignment(Pos.CENTER);

        //Arranginf elements on the grid
        gridePane_1.add(imageViewUNO, 0,0);
        gridePane_1.add(Texte1,0,1);
        gridePane_1.add(Texte2,0,2);
        gridePane_1.add(textField1,0,3);
        gridePane_1.add(button1,0,4);


        //Créating des scenes
        Scene Acceuil = new Scene(gridePane_1);



        windows.setTitle("Nouvelle partie -SoUno-");
        windows.setScene(Acceuil);

        //** SCENE in_game ***//

        //Creating elements
        //ImageView imageViewUNO2 = new ImageView(UNO);
        Text Att_joueur = new Text("Joueur "+ joueur.getName() + " En mode " + joueur.joue);
        //Button Carte_table = new Button(joueur.getCarteSurTable().toString());
        VBox Cartes_main_joueur = new VBox();
        Button button2 = new Button("Actualiser");


        //Creationg my gride pane for scene 1
        GridPane gridePane_2 = new GridPane();

        //Setting the gride pane
        gridePane_2.setMinSize(250,350);
        gridePane_2.setPadding(new Insets(10,10,10,10));
        gridePane_2.setVgap(5);
        gridePane_2.setHgap(5);
        gridePane_2.setAlignment(Pos.CENTER);

        //Arranginf elements on the grid
        //gridePane_2.add(imageViewUNO2, 0,0);
        gridePane_2.add(Att_joueur,0,1);
        gridePane_2.add(button2,0,2);
        gridePane_2.add(Cartes_main_joueur,0,3);

        Scene in_game = new Scene(gridePane_2);





        //Les actions
        button1.setOnAction((ActionEvent e) -> {
            joueur.setName(textField1.getText());
            Client client = new Client(joueur);
            //Att_joueur.textProperty().bind(client.Jouer_En_Cours);
            client.start();
            windows.setScene(in_game);
            windows.setTitle("Partie en cours -SoUno-");

        });

        button2.setOnAction((ActionEvent e) -> {
            Att_joueur.setText("En mode "+ joueur.joue);
            
            windows.setScene(in_game);
        });



        windows.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private MenuItem calculateItem;
    private Button calculateButton;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {

        //Elements de l'interface
        final MenuItem exitItem = new MenuItem("Quitter");
        exitItem.setOnAction((ActionEvent t) -> Platform.exit());
        final Menu fileMenu = new Menu("Fichier");
        fileMenu.getItems().add(exitItem);
        calculateItem = new MenuItem("Calculer");
        calculateItem.setOnAction((ActionEvent t) -> doCalculate());
        final Menu actionMenu = new Menu("Action");
        actionMenu.getItems().add(calculateItem);
        final MenuBar menuBar = new MenuBar();
        menuBar.getMenus().setAll(fileMenu, actionMenu);
        calculateButton = new Button();
        calculateButton.setText("Lancer le calcul !");
        calculateButton.setOnAction((ActionEvent event) -> doCalculate());
        StackPane center = new StackPane();
        center.getChildren().add(calculateButton);
        final BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(center);
        scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void doCalculate() {
        final Cursor oldCursor = scene.getCursor();
        scene.setCursor(Cursor.WAIT);
        calculateItem.setDisable(true);
        calculateButton.setDisable(true);
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.geometry.*;
import javafx.fxml.FXMLLoader;
import java.util.ArrayList;
import javafx.application.Platform;

public class App extends Application{
  public static Stage primaryStage;
  public static Scene[] scenes = new Scene[3];

  public static void main(String[] args) {
    Application.launch(args);
  }
  //use if screen unknown for better application window size
  //stage.setX(primaryScreenBounds.getMinX());
  //stage.setY(primaryScreenBounds.getMinY());
  //stage.setWidth(primaryScreenBounds.getWidth());
  //stage.setHeight(primaryScreenBounds.getHeight());
  @Override
  public void start(Stage stage)throws Exception{
    WebView myWeb = new WebView();
    WebEngine engine = myWeb.getEngine();
    Button back = new Button("Go back");
    Button exit = new Button("Exit");

    final double screenWidth = 1024;
    final double screenHeight = 600;

    VBox box = new VBox();
    box.getChildren().addAll(myWeb,back);
    scenes[1] = new Scene(box,screenWidth,screenHeight);

    Button tid = new Button("Show Departure Times");
    tid.setPrefWidth(screenHeight);
    tid.setMinHeight(screenHeight/2);
    tid.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        engine.load(getClass().getResource("sanntid.html").toExternalForm());
        stage.setScene(scenes[1]);
      }
    });

    Button weather = new Button("Show Weather");
    weather.setPrefWidth(screenHeight);
    weather.setMinHeight(screenHeight/2);
    weather.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        try{
          Pane weatherRoot = (Pane)FXMLLoader.load(getClass().getResource("WeatherPane.fxml"));
          stage.setScene(new Scene(weatherRoot,screenWidth,screenHeight));
        } catch(Exception e){ e.printStackTrace();}
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        stage.setScene(scenes[0]);
      }
    });

    exit.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        Platform.exit();
      }
    });

    BorderPane border = new BorderPane();
    VBox timeBox = new VBox();
    VBox weatherBox = new VBox();
    HBox exitBox = new HBox();

    timeBox.getChildren().add(tid);
    weatherBox.getChildren().add(weather);
    exitBox.getChildren().add(exit);
    timeBox.setAlignment(Pos.CENTER);
    weatherBox.setAlignment(Pos.CENTER);
    border.setLeft(exitBox);
    border.setCenter(timeBox);
    border.setBottom(weatherBox);

    scenes[0] = new Scene(border,screenWidth,screenHeight);
    stage.setScene(scenes[0]);
    primaryStage = stage;
    stage.show();
  }

}

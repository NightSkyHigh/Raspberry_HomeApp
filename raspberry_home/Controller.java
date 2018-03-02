import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.fxml.*;
import java.net.URL;
import java.util.*;
import java.text.SimpleDateFormat;

public class Controller implements Initializable{
  @FXML ImageView backButton;
  @FXML ImageView backButton1;

  @FXML private List<Label> temperatureList;
  @FXML private List<GridPane> gridList;
  @FXML private List<Label> weekdayList;
  @FXML private List<Label> hourList;

  @FXML Label day1Temp;
  @FXML Label day2Temp;
  @FXML Label day3Temp;
  @FXML Label day4Temp;

  @FXML Label period0Temp;
  @FXML Label period0Temp1;
  @FXML Label period0Temp2;
  @FXML Label period0Temp3;

  @FXML GridPane day2Grid;
  @FXML GridPane day3Grid;
  @FXML GridPane day4Grid;

  @FXML private List<ImageView> symbols;
  @FXML ImageView img0;
  @FXML ImageView img1;
  @FXML ImageView img2;
  @FXML ImageView img3;
  //total of 83 symbols, whhere i only use a minimum amount but can add at a later point.
  String[] iconList = new String[83];


  @Override
  public void initialize(URL location, ResourceBundle resources){
    //long startTime = System.nanoTime();
    final String DEGREE  = "\u00b0";
    ExtractWeatherData data = new ExtractWeatherData();
    iconList[1] = "src/sola.png";
    iconList[2] = "src/sologskyer.png";
    iconList[3] = "src/overskyetlys.png";
    iconList[4] = "src/overskyet.png";
    iconList[44] = "src/snow.png";
    iconList[8] = "src/snow.png";
    iconList[45] = "src/snow.png";
    iconList[49] = "src/snow.png";
    iconList[13] = "src/snow.png";
    iconList[50] = "src/snow.png";
    iconList[46] = "src/regn.png";
    iconList[9] = "src/regn.png";
    iconList[10] = "src/regn.png";
    iconList[15] = "src/Taakeoutline.png";
    iconList[24] = "src/lyn.png";
    iconList[6] = "src/lyn.png";
    iconList[25] = "src/lyn.png";
    iconList[26] = "src/lyn.png";
    iconList[20] = "src/lyn.png";
    iconList[27] = "src/lyn.png";
    iconList[28] = "src/lyn.png";
    iconList[21] = "src/lyn.png";
    iconList[29] = "src/lyn.png";
    iconList[30] = "src/lyn.png";
    iconList[22] = "src/lyn.png";
    iconList[11] = "src/lyn.png";

    ArrayList<String> weatherValues = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<String> periodValues = new ArrayList<String>();
    ArrayList<String> icons = new ArrayList<String>();

    boolean checkPeriod = false;

    setDates();
    data.getWeatherValues(weatherValues,time,periodValues,icons);
    int periodOffset = Integer.parseInt(periodValues.get(0));
    int counter = 0;

    for (int i = 0;i < temperatureList.size();i++) {
      temperatureList.get(i).setText(weatherValues.get(counter) + DEGREE + "C");
      hourList.get(i).setText(time.get(counter));
      if(iconList[Integer.parseInt(icons.get(counter))] != null){
        symbols.get(i).setImage(new Image(iconList[Integer.parseInt(icons.get(counter))]));
      }else{
        symbols.get(i).setImage(new Image("src/NotAvailable.png"));
      }
      if (!checkPeriod && Integer.parseInt(periodValues.get(counter)) == 3) {
        checkPeriod = true;
        i += periodOffset;
      }
      counter++;
    }
    day1Temp.setText(period0Temp.getText());
    day2Temp.setText(period0Temp1.getText());
    day3Temp.setText(period0Temp2.getText());
    day4Temp.setText(period0Temp3.getText());

    img0.setImage(symbols.get(0).getImage());
    img1.setImage(symbols.get(6).getImage());
    img2.setImage(symbols.get(10).getImage());
    img3.setImage(symbols.get(14).getImage());
    //long endTime = System.nanoTime();
    //long duration = (endTime - startTime)/1000000;
    //System.out.println(duration);
  }
  //NB: img0 is the small version set to the same as "current"
  //<fx:reference source="img0" />

  private void setDates(){
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, 1);

    for(int i = 0; i < weekdayList.size();i+=2){
        weekdayList.get(i).setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar.getTime()));
        weekdayList.get(i+1).setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar.getTime()));
        calendar.add(Calendar.DATE, 1);
    }
  }

  @FXML
  private void changeBackIcon(){
    backButton.setImage(new Image("src/knapp2.png"));
    backButton1.setImage(new Image("src/knapp2.png"));
  }

  @FXML
  private void handleClick(){
    App.primaryStage.setScene(App.scenes[0]);
  }

//should be done to 1 function not 4
  @FXML
  private void switchDay1(){
    for (int i = 0;i < gridList.size();i++) {
      if (i == 1){
        gridList.get(i).setVisible(true);
        gridList.get(i).setDisable(false);
      }
      else if(i == 0){
        gridList.get(i).setVisible(false);
        gridList.get(i).setDisable(true);
      }
      else if(i % 2 == 0){
        gridList.get(i).setVisible(true);
        gridList.get(i).setDisable(false);
      } else{
        gridList.get(i).setVisible(false);
        gridList.get(i).setDisable(true);
      }
    }
    day2Grid.setLayoutX(424);
    day3Grid.setLayoutX(624);
  }

  @FXML
  private void switchDay2(){
    for (int i = 0;i < gridList.size();i++) {
      if(i == 3){
        gridList.get(i).setVisible(true);
        gridList.get(i).setDisable(false);
      } else if(i % 2 == 0){
        gridList.get(i).setVisible(true);
        gridList.get(i).setDisable(false);
      }
      else{
        gridList.get(i).setVisible(false);
        gridList.get(i).setDisable(true);
      }
    }
    day3Grid.setLayoutX(624);
  }

  @FXML
  private void switchDay3(){
    for (int i = 0;i < gridList.size();i++) {
      if(i == 5){
        gridList.get(i).setVisible(true);
        gridList.get(i).setDisable(false);
      } else if(i % 2 == 0){
        gridList.get(i).setVisible(true);
        gridList.get(i).setDisable(false);
      }
      else{
        gridList.get(i).setVisible(false);
        gridList.get(i).setDisable(true);
      }
    }
      day2Grid.setLayoutX(200);
  }

  @FXML
  private void switchDay4(){
    for (int i = 0;i < gridList.size();i++) {
      if(i == 7){
        gridList.get(i).setVisible(true);
        gridList.get(i).setDisable(false);
      } else if(i % 2 == 0){
        gridList.get(i).setVisible(true);
        gridList.get(i).setDisable(false);
      }
      else{
        gridList.get(i).setVisible(false);
        gridList.get(i).setDisable(true);
      }
    }
    day2Grid.setLayoutX(200);
    day3Grid.setLayoutX(400);
  }
}

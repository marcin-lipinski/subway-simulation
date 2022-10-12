package Projekt;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML private Button startButton, placeTrainsButton;
    @FXML private Slider speedOfYellowTrainSlider, speedOfRedTrainSlider, speedOfBlueTrainSlider;
    @FXML private Slider timeOfRedDroppingSlider, timeOfYellowDroppingSlider, timeOfBlueDroppingSlider;
    @FXML private Label speedOfYellowTrainLabel, speedOfRedTrainLabel, speedOfBlueTrainLabel;
    @FXML private Label timeOfBlueDroppingLabel, timeOfRedDroppingLabel, timeOfYellowDroppingLabel;
    @FXML private ImageView[][] carts;
    @FXML private Spinner<Integer> numberOfCartsSpinner;

    public int speedOfYellowTrain, speedOfRedTrain, speedOfBlueTrain;
    public int timeOfRedDropping, timeOfBlueDropping, timeOfYellowDropping;
    public int numberOfCarts;

    private String[][] carsImagesURLs;
    private Thread P1, P2, P3;
    private int isRunning = 1;
    private final int[][] route={{0,1,2,3,4,5,7,9,11,13,15,17,19,25,31,33,35,37,39,41,43,49,50,51,52,53,0},
                          {0,6,8,10,12,14,16,18,20,21,22,23,24,25,31,33,35,37,39,41,43,49,48,47,46,45,0},
                          {0,1,2,3,4,5,7,9,11,13,15,17,19,25,26,27,28,29,30,32,34,36,38,40,42,44,0}};

    private final int[][] railwaysXY={{0,0},{530,3}, {590,3},{650,3},{710,3},{770,3},{470,42}, {770,42},
                                {470,81}, {770,81},{470,120}, {770,120},{470,159}, {770,159},{470,198}, {770,198},
                                {470,237}, {770,237}, {470,276}, {770,276}, {470,315}, {530,315},{590,315},{650,315},
                                {710,315},{770,315}, {830,315},{890,315},{950,315},{1010,315},{1070,315},
                                {770,354},{1070,354},{770,393},{1070,393},{770,432},{1070,432}, {770,471},{1070,471},
                                {770,510},{1070,510},{770,549},{1070,549}, {770,588},{1070,588},{530,627},{590,627},
                                {650,627},{710,627},{770,627}, {830,627},{890,627},{950,627},{1010,627},{1070,627}};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        carsImagesURLs = new String[][]{{"src/resources/yellow_loco.png", "src/resources/yellow_car.png"},
                                        {"src/resources/red_loco.png", "src/resources/red_car.png"},
                                        {"src/resources/blue_loco.png", "src/resources/blue_car.png"}};
        startButton.setDisable(true);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,12);
        valueFactory.setValue(2);
        numberOfCartsSpinner.setValueFactory(valueFactory);
        numberOfCarts=numberOfCartsSpinner.getValue();
        numberOfCartsSpinner.valueProperty().addListener((observableValue, integer, t1) -> numberOfCarts=numberOfCartsSpinner.getValue());

        addingListeners();
        setInitialSpeedOfTrains();
        setInitialTimeOfDropping();
        setCarsImages();
    }

    private void setInitialSpeedOfTrains(){
        speedOfYellowTrain = (int)(speedOfYellowTrainSlider.getValue());
        speedOfRedTrain = (int)(speedOfRedTrainSlider.getValue());
        speedOfBlueTrain = (int)(speedOfBlueTrainSlider.getValue());
        setInitialSpeedOfTrainsLabels();
    }

    private void setInitialSpeedOfTrainsLabels(){
        speedOfYellowTrainLabel.setText(Integer.toString(speedOfYellowTrain));
        speedOfRedTrainLabel.setText(Integer.toString(speedOfRedTrain));
        speedOfBlueTrainLabel.setText(Integer.toString(speedOfBlueTrain));
    }

    private void setInitialTimeOfDropping(){
        timeOfRedDropping = (int)(timeOfRedDroppingSlider.getValue());
        timeOfBlueDropping = (int)(timeOfBlueDroppingSlider.getValue());
        timeOfYellowDropping = (int)(timeOfYellowDroppingSlider.getValue());
        setInitialTimeOfDroppingLabels();
    }

    private void setInitialTimeOfDroppingLabels(){
        timeOfRedDroppingLabel.setText(Integer.toString(timeOfRedDropping));
        timeOfBlueDroppingLabel.setText(Integer.toString(timeOfBlueDropping));
        timeOfYellowDroppingLabel.setText(Integer.toString(timeOfYellowDropping));
    }
    private void addingListeners(){
        speedOfBlueTrainSlider.valueProperty().addListener((observableValue, number, t1) -> {
            speedOfBlueTrain = (int)speedOfBlueTrainSlider.getValue();speedOfBlueTrainLabel.setText(Integer.toString((int) speedOfBlueTrainSlider.getValue()));});

        speedOfRedTrainSlider.valueProperty().addListener((observableValue, number, t1) ->{
            speedOfRedTrain = (int)speedOfRedTrainSlider.getValue();speedOfRedTrainLabel.setText(Integer.toString((int)speedOfRedTrainSlider.getValue()));});

        speedOfYellowTrainSlider.valueProperty().addListener((observableValue, number, t1) ->{
            speedOfYellowTrain = (int)speedOfYellowTrainSlider.getValue();speedOfYellowTrainLabel.setText(Integer.toString((int)speedOfYellowTrainSlider.getValue()));});

        timeOfRedDroppingSlider.valueProperty().addListener((observableValue,number, t1) ->{
            timeOfRedDropping = (int) timeOfRedDroppingSlider.getValue();timeOfRedDroppingLabel.setText(Integer.toString((int)timeOfRedDroppingSlider.getValue()));});

        timeOfYellowDroppingSlider.valueProperty().addListener((observableValue,number, t1) ->{
            timeOfYellowDropping = (int) timeOfYellowDroppingSlider.getValue();timeOfYellowDroppingLabel.setText(Integer.toString((int)timeOfYellowDroppingSlider.getValue()));});

        timeOfBlueDroppingSlider.valueProperty().addListener((observableValue,number, t1) ->{
            timeOfBlueDropping = (int) timeOfBlueDroppingSlider.getValue();timeOfBlueDroppingLabel.setText(Integer.toString((int)timeOfBlueDroppingSlider.getValue()));});

    }

    @FXML
    public void placeTrains(){
        changeButtonsState();
        for (int i = 0; i <numberOfCarts; i++)
        {
            setSingleCarCoor(0, i, route[0][numberOfCarts - i]);
            checkIndexAndRotate(0, i, route[0][numberOfCarts - i]);

            setSingleCarCoor(1, i, route[1][26 - numberOfCarts + i]);
            checkIndexAndRotate(1, i, route[1][26 - numberOfCarts + i]);

            setSingleCarCoor(2, i, route[2][26 - numberOfCarts + i]);
            checkIndexAndRotate(2, i, route[2][26 - numberOfCarts + i]);

            int finalI = i;
            Platform.runLater(() -> Main.root.getChildren().add(carts[0][finalI]));
            Platform.runLater(() -> Main.root.getChildren().add(carts[1][finalI]));
            Platform.runLater(() -> Main.root.getChildren().add(carts[2][finalI]));
        }
    }

    private void setSingleCarCoor(int loco, int car, int coorsIndex){
        carts[loco][car].setX(railwaysXY[coorsIndex][0]);
        carts[loco][car].setY(railwaysXY[coorsIndex][1]);
    }

    private void checkIndexAndRotate(int loco, int car, int coorsIndex){
        switch (loco){
            case 0:
                if((coorsIndex>5 && coorsIndex<20) || (coorsIndex>30 && coorsIndex<45)) carts[0][car].setRotate(90);
                break;
            case 1:
                if((coorsIndex>5 && coorsIndex<20) || (coorsIndex>30 && coorsIndex<45)) carts[1][car].setRotate(-90);
                break;
            case 2:
                if((coorsIndex>5 && coorsIndex<20) || (coorsIndex>30 && coorsIndex<45)) carts[2][car].setRotate(90);
                if(car == 0) carts[2][car].setRotate(-90);
                if(coorsIndex>25 && coorsIndex <31) carts[2][car].setRotate(180);
                break;
            default:
                break;
        }
    }

    private void changeButtonsState(){
        placeTrainsButton.setDisable(true);
        startButton.setDisable(false);
        numberOfCartsSpinner.setDisable(true);
    }
    private void setCarsImages(){
        carts = new ImageView[3][numberOfCarts + 1];
        for(int j=0;j<3;j++)
        {
            for (int i = 1; i < numberOfCarts; i++)
            {
                setSingleCar(j, i, carsImagesURLs[j][1]);
            }
            setSingleCar(j, 0, carsImagesURLs[j][0]);
            setSingleCar(j, numberOfCarts - 1, carsImagesURLs[j][0]);
            if(numberOfCarts != 1) carts[j][numberOfCarts-1].setRotate(180);
        }
    }

    private void setSingleCar(int loco, int car, String image){
        carts[loco][car] = new ImageView();
        carts[loco][car].setImage(new Image(image));
        carts[loco][car].setFitHeight(40);
        carts[loco][car].setFitWidth(60);
    }

    @FXML
    public void exitButtonOnClick() throws InterruptedException{
        if(isRunning==0)startButtonOnClick();
        System.exit(0);
    }

    @FXML
    public void startButtonOnClick() throws InterruptedException{
        isRunning = (isRunning + 1) % 2;
        if (isRunning == 0)
        {
            startButton.setText("STOP");
            Shared shared = new Shared(this);
            initThreads(shared);
            P1.start();
            P2.start();
            P3.start();
        }
        else
        {
            startButton.setText("START");
            startButton.setDisable(true);
            placeTrainsButton.setDisable(false);
            numberOfCartsSpinner.setDisable(false);
            stopThreads();
            removeTrainsFromBoard();

        }
    }

    private void removeTrainsFromBoard(){
        for (int i = 0; i < numberOfCarts; i++)
        {
            int finalI = i;
            Platform.runLater(() -> Main.root.getChildren().remove(carts[0][finalI]));
            Platform.runLater(() -> Main.root.getChildren().remove(carts[1][finalI]));
            Platform.runLater(() -> Main.root.getChildren().remove(carts[2][finalI]));
        }
    }

    private void stopThreads(){
        P1.interrupt();
        P2.interrupt();
        P3.interrupt();
    }
    private void initThreads(Shared shared){
        P1 = new YellowTrain(shared, numberOfCarts,this, route[0]);
        P2 = new RedTrain(shared, numberOfCarts,this, route[1]);
        P3 = new BlueTrain(shared, numberOfCarts,this, route[2]);
    }

    @FXML
    public void moveTrain(int head, int direction, int nr){
        for(int i=0;i<numberOfCarts;i++)
        {
            if(direction == 1)
            {
                if(!rotateLoco(head, nr, i)){
                    switch (nr) {
                        case 0 -> moveYellowTrainBack(head, i);
                        case 1 -> moveRedTrainBack(head, i);
                        case 2 -> moveBlueTrainBack(i);
                    }
                }
                carts[nr][i].setX(railwaysXY[route[nr][head+i]][0]);
                carts[nr][i].setY(railwaysXY[route[nr][head+i]][1]);
            }

            if(direction == 0)
            {
                if(!rotateLoco(head, nr, i)){
                    switch (nr) {
                        case 0 -> moveYellowTrainForward(head, i);
                        case 1 -> moveRedTrainForward(head, i);
                        case 2 -> moveBlueTrainForward(i);
                    }
                }
                carts[nr][i].setX(railwaysXY[route[nr][head-i]][0]);
                carts[nr][i].setY(railwaysXY[route[nr][head-i]][1]);
            }
        }
    }

    private boolean rotateLoco(int head, int nr, int car){
        if((route[nr][head - car]>5 && route[nr][head-car]<20) || (route[nr][head-car]>30 && route[nr][head-car]<45)){
            carts[nr][car].setRotate(-90);
            if(car == numberOfCarts-1) carts[nr][car].setRotate(90);
            return true;
        }
        return false;
    }

    private void moveRedTrainForward(int head, int i) {
        if(route[1][head-i]>=45)
        {
            carts[1][i].setRotate(180);
            if (i == numberOfCarts - 1) carts[1][i].setRotate(0);
        }
        else
        {
            carts[1][i].setRotate(0);
            if(i==numberOfCarts-1)carts[1][i].setRotate(180);
        }
    }

    private void moveRedTrainBack(int head, int i) {
        if(route[1][head+i]>=45){
            carts[1][i].setRotate(0);
            if(i==numberOfCarts-1)carts[1][i].setRotate(180);
        }
        else{
            carts[1][i].setRotate(180);
            if(i==numberOfCarts-1)carts[1][i].setRotate(0);
        }
    }

    private void moveBlueTrainForward(int i) {
        carts[2][i].setRotate(0);
        if(i==numberOfCarts-1)carts[2][i].setRotate(180);
    }

    private void moveBlueTrainBack(int i) {
        carts[2][i].setRotate(180);
        if(i==numberOfCarts-1)carts[2][i].setRotate(0);
    }
    private void moveYellowTrainForward(int head, int i){
        if(route[0][head-i]!=25)
        {
            carts[0][i].setRotate(0);
            if (i == numberOfCarts - 1) carts[0][i].setRotate(180);
        }
    }
    private void moveYellowTrainBack(int head, int i) {
        if(route[0][head+i]!=25){
            carts[0][i].setRotate(180);
            if(i==numberOfCarts-1)carts[0][i].setRotate(0);
        }
    }
}



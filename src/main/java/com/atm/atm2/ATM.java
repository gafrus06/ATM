package com.atm.atm2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ATM extends Application {

    private int pin_code;
    private File file;
    private final double WEIGHT = 200;
    private final double HEIGHT = 200;
    private  int count = 3;
    private final List<Button> buttons = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        Card card = new Card();
        buttons.add(new Button("Внести"));
        buttons.add(new Button("Вывести"));
        buttons.add(new Button("Счёт"));
        buttons.add(new Button("Выйти"));
        buttons.add(new Button("Вставьте карту"));
        buttons.add(new Button("Продолжить"));


        PasswordField passwordField = new PasswordField();
        passwordField.setMinHeight(HEIGHT/8);
        passwordField.setMinWidth(WEIGHT/2);


        buttons.get(4).setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();

            file = fileChooser.showOpenDialog(stage);
            card.readCard(new File(file.getPath()));
            pin_code = card.getPin_code();


            stage.setScene(createScene(buttons.get(5), passwordField));
        });
        buttons.get(5).setOnAction(actionEvent1 -> {
            if((passwordField.getText().equals(String.valueOf(pin_code))) && (count > 0)){
                stage.setScene(createMainScene(buttons));
            }else {
                if(count > 0) {
                    count--;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    if(count != 0) alert.setContentText("Не верный пин-код. Осталось " + count + " попыток");
                    if(count == 0) {
                        alert.setContentText("Карта заблокирована");
                        stage.close();
                    }
                    alert.showAndWait();
                    if (!alert.isShowing()) {
                        passwordField.clear();
                    }

                    System.out.println(count);
                    stage.setScene(createScene(buttons.get(4), passwordField));
                }

            }
        });
        buttons.get(0).setOnAction(actionEvent -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Внести");
            dialog.setHeaderText(null);
            dialog.setContentText("Внесите сумму:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(s -> card.updateBalanceCard(file, Integer.parseInt(s)));


        });
        buttons.get(2).setOnAction(actionEvent -> {
            card.readCard(file);
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Счет");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("Баланс: " + card.getBalance() + "\n" + "Номер карты: " + card.getNumber());
            infoAlert.showAndWait();
        });
        buttons.get(1).setOnAction(actionEvent -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Вывести");
            dialog.setHeaderText(null);
            dialog.setContentText("Введите сумму:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                if(Integer.parseInt(result.get()) < card.getBalance()){
                    card.updateBalanceCard(file, backwards(Integer.valueOf(result.get())));
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Пополните баланс");
                    alert.setTitle("Не досточно средств");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }

        });
        buttons.get(3).setOnAction(actionEvent -> stage.close());

        stage.setTitle("ATM");

        stage.setResizable(false);
        stage.setScene(createScene(buttons.get(4)));
        stage.show();

    }
    public int backwards(Integer inter){
        return -inter;
    }
    public Scene createScene(Button button){
         BorderPane root1 = new BorderPane();
         Group root = new Group();
         root1.setCenter(button);
         root.getChildren().add(root1);

        return new Scene(root, WEIGHT, HEIGHT);

    }
    public Scene createScene(Button button, PasswordField textField){
        BorderPane root1 = new BorderPane();
        root1.setBottom(button);
        root1.setCenter(textField);
        Group root = new Group();
        root.getChildren().add(root1);
        return new Scene(root, WEIGHT, HEIGHT);

    }
    public Scene createMainScene(List<Button> buttons){
        VBox vBox = new VBox();

        for(Button button : buttons){
            setWH(button);
            vBox.getChildren().add(button);
        }
        Group root = new Group();
        root.getChildren().add(vBox);

        return new Scene(root, WEIGHT, HEIGHT);

    }
    public void setWH(Button button){
        button.setMinHeight(HEIGHT/4);
        button.setMinWidth(WEIGHT);
    }

}
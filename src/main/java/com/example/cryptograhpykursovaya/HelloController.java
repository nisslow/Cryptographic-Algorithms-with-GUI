package com.example.cryptograhpykursovaya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

public class HelloController implements Initializable {
    @FXML
    private ComboBox<String> algorithmComboBox;

    private final ObservableList<String> algorithms = FXCollections.observableArrayList(
            "НОД Евклид", "Решето Эратосфена", "Диффи Хеллман", "Быстрое возведение в степень по модулю", "Шамир", "Эль-Гамаль", "RSA","Хэш-функция","Сеть Фейстеля","Ничего");


    private void showStage(String nameOfFXML) throws IOException {
        System.out.println("LOAD FXML");
        Parent parent = FXMLLoader.load(getClass().getResource(nameOfFXML));
        Scene scene = new Scene(parent);
        System.out.println("SCENE: " + nameOfFXML);
        Stage stage = new Stage();
        stage.setTitle(nameOfFXML);
        stage.setScene(scene);
        stage.show();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algorithmComboBox.getItems().addAll(algorithms);
    }
    @FXML
    public void handleAlgorithmSelection(ActionEvent event ) {
        String selected = algorithmComboBox.getValue();
        LoggerUtil.logFinest("Выбран алгоритм: " + selected);
        try {
            if (selected.equals(algorithms.get(0))) {
                LoggerUtil.logFiner("Запуск окна алгоритма Евклида");
                showStage("Evklid.fxml");
            } else if (algorithmComboBox.getValue().equals(algorithms.get(1))) {
                showStage("SieveOfEratosphenes.fxml");
            } else if (algorithmComboBox.getValue().equals(algorithms.get(2))) {
                showStage("DiffieHellman.fxml");
            }else if (algorithmComboBox.getValue().equals(algorithms.get(3))) {
                showStage("FastModPow.fxml");
            }else if (algorithmComboBox.getValue().equals(algorithms.get(4))) {
                showStage("Shamir.fxml");
            }else if (algorithmComboBox.getValue().equals(algorithms.get(5))) {
                showStage("ElGamal.fxml");
            }else if (algorithmComboBox.getValue().equals(algorithms.get(6))) {
                showStage("RSA.fxml");
            } else if (algorithmComboBox.getValue().equals(algorithms.get(7))) {
                showStage("Hash.fxml");
            }else if (algorithmComboBox.getValue().equals(algorithms.get(8))) {
                showStage("FeistelNetwork.fxml");
            }else {}
            // и т.д. для всех остальных алгоритмов
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
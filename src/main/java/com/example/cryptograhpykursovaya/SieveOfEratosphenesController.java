package com.example.cryptograhpykursovaya;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SieveOfEratosphenesController implements Initializable {

    static Scanner scanner = new Scanner(System.in);

    @FXML
    protected TextField textFieldStart;
    @FXML
    protected TextField textFieldEnd;
    @FXML
    protected TextArea textArea;
    @FXML
    protected Button buttonWriteInFile;
    @FXML
    protected Button buttonWriteInArea;

    @FXML
    protected Button buttonClearText;
    @FXML
    protected void clearText(ActionEvent event) {
        textFieldStart.clear();
        textFieldEnd.clear();
        textArea.clear();
    }

    @FXML
    protected void writeInFile(ActionEvent event) {
        if (textFieldStart.getText().isBlank() || textFieldEnd.getText().isBlank() ||
                !textFieldStart.getText().matches("-?\\d+") || textFieldStart.getText().matches("-\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Впишите не отрицательные числа");
            alert.showAndWait();
        } else if (Integer.parseInt(textFieldStart.getText()) > Integer.parseInt(textFieldEnd.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Начало не может быть больше конца");
            alert.showAndWait();
        } else {
            ArrayList<Integer> primes = eratosphenesPrimes(Integer.parseInt(textFieldStart.getText()), Integer.parseInt(textFieldEnd.getText()));
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showSaveDialog(null);
            if (selectedFile != null) {
                try {
                    FileWriter writer = new FileWriter(selectedFile);
                    writer.write(primes.toString());
                    writer.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех");
                    alert.setContentText("Данные записаны в файл " + selectedFile.getAbsolutePath());
                    alert.showAndWait();
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setContentText("Ошибка при записи данных в файл" + selectedFile.getAbsolutePath() + ": " + ex.getMessage());
                    alert.showAndWait();
                }
            }
        }

    }

    @FXML
    protected void writeInTextArea(ActionEvent event) {
        if (textFieldStart.getText().isBlank() || textFieldEnd.getText().isBlank() ||
                !textFieldStart.getText().matches("-?\\d+") || textFieldStart.getText().matches("-\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Впишите не отрицательные числа");
            alert.showAndWait();
        } else if (Integer.parseInt(textFieldStart.getText()) > Integer.parseInt(textFieldEnd.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Начало не может быть больше конца");
            alert.showAndWait();
        } else {
            ArrayList<Integer> a = eratosphenesPrimes(Integer.parseInt(textFieldStart.getText()), Integer.parseInt(textFieldEnd.getText()));
            textArea.setWrapText(true);
            textArea.setText(a.toString());
        }
    }

    protected static ArrayList<Integer> eratosphenesPrimes(int start, int max) {
        BitSet isPrime = new BitSet(max);
        isPrime.set(2, max, true);

        for (int i = 2; i * i < max; i++) {
            if (isPrime.get(i)) {
                for (int j = i * i; j < max; j += i) {
                    isPrime.clear(j);
                }
            }
        }
        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = start; i < max; i++) {
            if (isPrime.get(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

package com.example.cryptograhpykursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;

public class FastModPowController implements Initializable {

    @FXML
    protected TextField textFieldBaseNumber;
    @FXML
    protected TextField textFieldPower;
    @FXML
    protected TextField textFieldMod;
    @FXML
    protected TextArea TextAreaResult;
    @FXML
    protected  Button buttonFastModPow;

    @FXML
    protected Button buttonClearText;

    @FXML
    protected void clearText(ActionEvent event) {
        textFieldBaseNumber.clear();
        textFieldPower.clear();
        textFieldMod.clear();
        TextAreaResult.clear();
    }

    @FXML
    protected void fastModPow(ActionEvent event) {


        if (!textFieldBaseNumber.getText().matches("\\d+") || !textFieldPower.getText().matches("\\d+") || !textFieldMod.getText().matches("\\d+") ||
                textFieldBaseNumber.getText().matches("-\\d+") || textFieldPower.getText().matches("-\\d+") || textFieldMod.getText().matches("-\\d+") ||
                textFieldBaseNumber.getText().isBlank() || textFieldPower.getText().isBlank() || textFieldMod.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Впишите не отрицательные числа");
            alert.showAndWait();
        } else {
            BigInteger baseNumber = new BigInteger(textFieldBaseNumber.getText());
            BigInteger power = new BigInteger(textFieldPower.getText());
            BigInteger mod = new BigInteger(textFieldMod.getText());
            String res = baseNumber + "^" + power + " mod " + mod + " = " + fastModPow(baseNumber, power, mod);
            TextAreaResult.setWrapText(true);
            TextAreaResult.setText(res);
        }
    }

    public static int calculateT(BigInteger logX) {
        BigInteger powerRes = BigInteger.valueOf(1);
        int count = 0;
        while (powerRes.compareTo(logX) < 0) {
            powerRes = powerRes.multiply(BigInteger.valueOf(2));
            count++;
        }
        return count;
    }

    public static BigInteger fastModPow(BigInteger number, BigInteger logX, BigInteger mod) {
        BigInteger[] arrayT = new BigInteger[calculateT(logX)];
        arrayT[0] = number.mod(mod);
        for (int i = 1; i < arrayT.length; i++) {
            arrayT[i] = arrayT[i-1].multiply(arrayT[i-1]).mod(mod);
        }
        String binaryLogX = logX.toString(2);
        BigInteger fastModPowRes = BigInteger.valueOf(1);
        for (int i = 0; i < binaryLogX.length(); i++) {
            if (binaryLogX.charAt(binaryLogX.length() - 1 - i) == '1') {
                fastModPowRes = fastModPowRes.multiply(arrayT[i]).mod(mod);
            }
        }
        return fastModPowRes;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

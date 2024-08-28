
package com.example.cryptograhpykursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class EvklidController implements Initializable {
    @FXML
    protected CheckBox checkGeneralEvklid;
    @FXML
    protected CheckBox checkEvklid;
    @FXML
    protected TextField textField1;
    @FXML
    protected TextField textField2;
    @FXML
    protected TextField textFieldResult;
    @FXML
    protected TextField textFieldX;
    @FXML
    protected TextField textFieldY;
    @FXML
    protected  Button buttonResult;

    @FXML
    protected Button buttonClearText;
    protected static boolean isGeneralEvklid =false;
    protected static boolean isEvklid =false;


    @FXML
    protected void clearText(ActionEvent event) {
        textField1.clear();
        textField2.clear();
        textFieldResult.clear();
        textFieldX.clear();
        textFieldY.clear();
        LoggerUtil.logInfo("Data has been cleared");
    }
    @FXML
    protected void chooseCheckGeneralEvklid(ActionEvent event) {
        if(!isGeneralEvklid)
        {
            LoggerUtil.logInfo("Выбран алгоритм Обобщенного Евклида");
            isGeneralEvklid=true;
        } else {
            LoggerUtil.logInfo("Убран алгоритм Обобщенного Евклида");
            isGeneralEvklid=false;
        }
    }
    @FXML
    protected void chooseCheckEvklid(ActionEvent event) {
        if(!isEvklid)
        {
            LoggerUtil.logInfo("Выбран алгоритм Евклида");
            isEvklid=true;
        } else {
            LoggerUtil.logInfo("Убран алгоритм Евклида");
            isEvklid=false;
        }
    }

    @FXML
    protected void doOperation(ActionEvent event) {
        if (textField1.getText().isBlank() || textField2.getText().isBlank() ||
                !textField1.getText().matches("-?\\d+") || textField1.getText().matches("-\\d+")||
                !textField2.getText().matches("-?\\d+") || textField2.getText().matches("-\\d+")) {
            LoggerUtil.logError("Вписаны отрицательные числа или не числа" + "\na: "+textField1.getText() + "\nb: "+ textField2.getText());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Впишите не отрицательные числа");
            alert.showAndWait();
        } else if (checkEvklid.isSelected() && checkGeneralEvklid.isSelected()) {
            LoggerUtil.logError("Выбрано два алгоритма");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Выбрано несколько алгоритмов");
            alert.showAndWait();
        } else if (!checkEvklid.isSelected() && !checkGeneralEvklid.isSelected()) {
            LoggerUtil.logError("Не выбран алгоритм");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Выберите алгоритм");
            alert.showAndWait();
        } else if (checkEvklid.isSelected()) {
            LoggerUtil.logInfo("Выполняется алгоритм Евклида");
            long[] z = Evklid();
            LoggerUtil.logFinest("Конец алгоритма евклида");
            textFieldResult.setText(String.valueOf(z[0]));
            LoggerUtil.logInfo("Результат: " + z[0]);

        } else if (checkGeneralEvklid.isSelected()) {
            LoggerUtil.logInfo("Выполняется алгоритм Евклида");
            long[] x = EvklidGeneral();
            LoggerUtil.logFinest("Конец алгоритма евклида");
            textFieldResult.setText(String.valueOf(x[0]));
            textFieldX.setText(String.valueOf(x[1]));
            textFieldY.setText(String.valueOf(x[2]));
            LoggerUtil.logInfo("Результат: \nx[0]: " + x[0] + "\nx[1]: " + x[1] + "\nx[2]: " + x[2]  );

        }
    }


    protected long[] EvklidGeneral() {
        LoggerUtil.logFinest("Начало обобщенного алгоритма Еклида");

        long a = Long.parseLong(textField1.getText());
        long b = Long.parseLong(textField2.getText());
        long[] u = {a, 1, 0}, v = {b, 0, 1};
        LoggerUtil.logWarning("a: " + a + " / b: " + b);


        if (a < 1 || b < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            LoggerUtil.logError("Числа меньше нуля" + "\na: "+a + "\nb: "+ b);
            alert.setTitle("Ошибка");
            alert.setContentText("Числа должны быть больше нуля");
            alert.showAndWait();
        } else {
            if (a < b) {
                long temp = a;
                a = b;
                b = temp;
            }
            while (v[0] != 0) {
                long q = u[0] / v[0];
                long[] t = {u[0] % v[0], u[1] - q * v[1], u[2] - q * v[2]};
                u = v.clone();
                v = t.clone();
            }
        }
        return u;
    }

    protected long[] Evklid() {
        LoggerUtil.logFinest("Начало алгоритма Еклида");
        long a = Long.parseLong(textField1.getText());
        long b = Long.parseLong(textField2.getText());
        long[] f = {a, 8, 4}, g = {b, 4, 0};
        LoggerUtil.logWarning("a: " + a + " / b: " + b);

        if (a < 1 || b < 1) {
            LoggerUtil.logError("Числа меньше нуля" + "\na: "+a + "\nb: "+ b);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Числа должны быть больше нуля");
            alert.showAndWait();
        } else {
            if (a < b) {
                long temp = a;
                a = b;
                b = temp;
            }
            while (g[0] != 0) {
                long r = f[0] % g[0];
                f = g.clone();
                g[0] = r;
                LoggerUtil.logFine("r: "+r +" / f[0]: " + f[0]+" / g[0]: " + g[0]);
            }
        }
        return f;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


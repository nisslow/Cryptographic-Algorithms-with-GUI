package com.example.cryptograhpykursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.ResourceBundle;

public class ShamirController implements Initializable {

    @FXML
    protected TextField textFieldSecretString;
    @FXML
    protected TextField textFieldShowP;
    @FXML
    protected  Button buttonTransferString;
    @FXML
    protected  Button buttonGenerateP;

    @FXML
    protected Button buttonClearText;

    BigInteger p;

    @FXML
    protected void clearText(ActionEvent event) {
        textFieldSecretString.clear();
    }

    @FXML
    protected void generateP(ActionEvent event){
        ArrayList<Integer> arrayList = eratosphenesPrimes(10000000, 100000000);
        p = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
        textFieldShowP.setText(String.valueOf(p));
    }

    @FXML
    protected void transferString(ActionEvent event){
        ArrayList<Integer> arrayList = eratosphenesPrimes(10000000, 100000000);



        if (!textFieldSecretString.getText().matches("\\d+") || textFieldSecretString.getText().isBlank() || new BigInteger(textFieldSecretString.getText()).compareTo(BigInteger.ONE) < 0
              || textFieldSecretString.getText().matches("-\\d+")  || new BigInteger(textFieldSecretString.getText()).compareTo(p.subtract(BigInteger.TWO)) >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Сообщение должно быть больше 1 и меньше p-2");
            alert.showAndWait();
        }else {
            BigInteger Ca, Da, Cb, Db, m;
            System.out.println("p = " + p);
            Da = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
            System.out.println("Абонент А:");
            BigInteger[] u = {p.subtract(BigInteger.ONE), BigInteger.ONE, BigInteger.ZERO}, v = {Da, BigInteger.ZERO, BigInteger.ONE};
            BigInteger[] x = GeneralEvklid(u, v);
            if (x[2].compareTo(BigInteger.ZERO) < 0) {
                Ca = u[0].add(x[2]);
                System.out.println("Ca= " + Ca);
            } else {
                Ca = x[2];
                System.out.println("Ca= " + Ca);
            }
            System.out.println("Da= " + Da);

            System.out.print("Абонент А передает сообщение абоненту B: ");
            m = new BigInteger(textFieldSecretString.getText());
            System.out.println("m= " + m);


            Db = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
            System.out.println("Абонент B:");
            BigInteger[] u2 = {p.subtract(BigInteger.ONE), BigInteger.ONE, BigInteger.ZERO}, v2 = {Db, BigInteger.ZERO, BigInteger.ONE};
            x = GeneralEvklid(u2, v2);
            if (x[2].compareTo(BigInteger.ZERO) < 0) {
                Cb = u[0].add(x[2]);
                System.out.println("Cb= " + Cb);
            } else {
                Cb = x[2];
                System.out.println("Cb= " + Cb);
            }
            System.out.println("Db= " + Db);
            BigInteger x1 = fastModPow(m, Ca, p);
            BigInteger x2 = fastModPow(x1, Cb, p);
            BigInteger x3 = fastModPow(x2, Da, p);
            BigInteger x4 = fastModPow(x3, Db, p);
            System.out.println("x1= " + x1);
            System.out.println("x2= " + x2);
            System.out.println("x3= " + x3);
            System.out.println("x4= " + x4);
            if (m.equals(x4)) {
                System.out.println("m = " + m + " = x4 = " + x4);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setContentText("Сообщение было передано");
                alert.showAndWait();
            }
            else{
                System.out.println("m != x4");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Сообщение не было передано");
                alert.showAndWait();
            }

        }
    }

    public static BigInteger[] GeneralEvklid(BigInteger[] u, BigInteger[] v) {
        while (v[0].compareTo(BigInteger.ZERO) != 0) {
            BigInteger q = u[0].divide(v[0]);
            BigInteger[] t = {u[0].mod(v[0]), u[1].subtract(q.multiply(v[1])), u[2].subtract(q.multiply(v[2]))};
            u = v.clone();
            v = t.clone();
        }
        return u;
    }

    public static ArrayList<Integer> eratosphenesPrimes(int start, int max) {
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

   /* public static int CalculateT(BigInteger logX) {
        BigInteger powerRes = new BigInteger("2");
        int count = 1;
        for (int i = 1; i < logX.intValue(); i++) {
            if (powerRes.pow(i).compareTo(logX) < 1) {
                powerRes.pow(i);
                count = i;
            } else if (powerRes.pow(i).compareTo(logX) == 1) break;
        }
        return count;
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

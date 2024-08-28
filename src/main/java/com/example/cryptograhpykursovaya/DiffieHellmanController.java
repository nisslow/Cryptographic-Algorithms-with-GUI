package com.example.cryptograhpykursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.ResourceBundle;

public class DiffieHellmanController implements Initializable {

    @FXML
    protected PasswordField textFieldKey1;
    @FXML
    protected PasswordField textFieldKey2;
    @FXML
    protected Button buttonGenerateSecretKey;

    @FXML
    protected Button buttonClearText;

    @FXML
    protected void clearText(ActionEvent event) {
        textFieldKey1.clear();
        textFieldKey2.clear();
    }

    @FXML
    protected void generateSecretKey(ActionEvent event) {
        if ( !textFieldKey1.getText().matches("\\d+") || !textFieldKey2.getText().matches("\\d+") ||
                textFieldKey1.getText().matches("-\\d+") || textFieldKey2.getText().matches("-\\d+") ||
                textFieldKey1.getText().isBlank() || textFieldKey2.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Впишите не отрицательные секретные числа");
            alert.showAndWait();
        } else {
            ArrayList<Integer> arrayList = eratosphenesPrimes(500000, 100000000);
            BigInteger Xc, Ya, Yb, Yc, Zab, Zba, Zac, Zca, g, p, q;
            double currentTime = System.currentTimeMillis();// определяется настоящим временем в микросекундах
            long seed = (long) (currentTime % 100);
            p = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
                  System.out.println("p: " + p);
            q = p.subtract(BigInteger.ONE).divide(BigInteger.TWO);
                 System.out.println("q: " + q);
                 System.out.println("Генерируется число g в диапазоне от 2 до " + (p.subtract(BigInteger.TWO)));
            g = BigInteger.valueOf(2).add(myRandom(arrayList, seed).mod(p.subtract(BigInteger.TWO)));
            //  System.out.println("g = "+g);
            int gcondition = (g.modPow(q, p)).intValue();
            while ((g.compareTo(BigInteger.TWO) < 0) || (g.compareTo(p.subtract(BigInteger.ONE)) >= 0) || gcondition == 1) {
                //     System.out.println("Сгенерировалсоь неверное число g");
                //      System.out.println("Генерируется g в диапазоне от 2 до " + (p.subtract(BigInteger.TWO)));
                g = BigInteger.valueOf(2).add(myRandom(arrayList, seed).mod(p.subtract(BigInteger.TWO)));
                //      System.out.println("g = " + g);
                gcondition = (g.modPow(q, p)).intValue();
            }
                  System.out.println("g = "+g);
            BigInteger Xa = new BigInteger(textFieldKey1.getText());
            BigInteger Xb = new BigInteger(textFieldKey2.getText());
                    //      Xc = BigInteger.valueOf(100000).add(myRandom(seed).mod(BigInteger.valueOf(5000000 - 100000)));
                           System.out.println("Абонент A вводит секретное число: " + Xa);
                           System.out.println("Абонент B вводит секретное число: " + Xb);
                    //      System.out.println("Абонент C вводит секретное число: " + Xc);
                    Ya = g.modPow(Xa, p);
                   System.out.println("Открытый ключ Ya: " + Ya);
            Yb = g.modPow(Xb, p);
                  System.out.println("Открытый ключ Yb: " + Yb);
            //      Yc = g.modPow(Xc, p);
            //      System.out.println("Открытый ключ Yc: " + Yc);
            Zab = Yb.modPow(Xa, p);
                  System.out.println("Величина Zab: " + Zab);
            Zba = Ya.modPow(Xb, p);
                    System.out.println("Величина Zba: " + Zba);
            if (Zab.equals(Zba)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setContentText("Общий секретный ключ был сгенерирован");
                alert.showAndWait();
                System.out.println("Соединение успешно выполнено");
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Общий секретный ключ не был сгенерирован");
                alert.showAndWait();
                System.out.println("Ошибка!");
            }

        /*Zac = Yc.modPow(Xa, p);
        System.out.println("Величина Zac: " + Zac);
        Zca = Ya.modPow(Xc, p);
        System.out.println("Величина Zca: " + Zca);
        if (Zac.equals(Zca)) System.out.println("Соединение успешно выполнено");
        else System.out.println("Соединение не выполнено");
         */
        }
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

    public static BigInteger myRandom(ArrayList<Integer> arrayList, long seed) {
        BigInteger m = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
        BigInteger a = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
        BigInteger c = a.subtract(BigInteger.ONE); // простое большое число взаимное с m
        seed *= seed;
        return (a.multiply(BigInteger.valueOf(seed)).add(c)).mod(m);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}

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

public class ElGamalController implements Initializable {

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

    @FXML
    protected void clearText(ActionEvent event) {
        textFieldSecretString.clear();
    }

     BigInteger p;
    @FXML
    protected void generateP(ActionEvent event){
        ArrayList<Integer> arrayList = eratosphenesPrimes(10000000, 100000000);
        p = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
        textFieldShowP.setText(String.valueOf(p));
    }

    @FXML
    protected void transferString(ActionEvent event) {
        ArrayList<Integer> arrayList = eratosphenesPrimes(10000000, 100000000);

        if ( !textFieldSecretString.getText().matches("\\d+") || textFieldSecretString.getText().isBlank() || new BigInteger(textFieldSecretString.getText()).compareTo(BigInteger.ONE) < 0
               || textFieldSecretString.getText().matches("-\\d+") || new BigInteger(textFieldSecretString.getText()).compareTo(p.subtract(BigInteger.ONE)) >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Сообщение должно быть больше 1 и меньше p");
            alert.showAndWait();
        }else {
            BigInteger g, q, cA, dA, cB, dB, k, r, e, m, m2;
            double currentTime = System.currentTimeMillis();// определяется настоящим временем в микросекундах
            long seed = (long) (currentTime % 100);
     //       p = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
            System.out.println("p: " + p);
            q = p.subtract(BigInteger.ONE).divide(BigInteger.TWO);
            System.out.println("q: " + q);
            System.out.println("Генерируется число g в диапазоне от 2 до " + (p.subtract(BigInteger.TWO)));
            g = BigInteger.valueOf(2).add(myRandom(arrayList,seed).mod(p.subtract(BigInteger.TWO)));
 //           System.out.println("g = " + g);
            int gcondition = (g.modPow(q, p)).intValue();
            while ((g.compareTo(BigInteger.TWO) < 0) || (g.compareTo(p.subtract(BigInteger.ONE)) >= 0) || gcondition == 1) {
    //            System.out.println("Сгенерировалсоь неверное число g");
    //            System.out.println("Генерируется число g в диапазоне от 2 до " + (p.subtract(BigInteger.TWO)));
                g = BigInteger.valueOf(2).add(myRandom(arrayList,seed).mod(p.subtract(BigInteger.TWO)));
     //           System.out.println("g = " + g);
                gcondition = (g.modPow(q, p)).intValue();
            }
            System.out.println("g = " + g);
            System.out.println("Генерируем секретное число cA в диапазоне от 2 до " + (p.subtract(BigInteger.TWO)));
            cA = BigInteger.valueOf(2).add(myRandom(arrayList,seed).mod(p.subtract(BigInteger.TWO)));
            // cA = checkInput();
            secretKey(arrayList,cA, p, seed);
            dA = g.modPow(cA, p);
            System.out.println("cA= " + cA);
            System.out.println("dA= " + dA);
            System.out.println("Генерируем секретное число cB в диапазоне от 2 до " + (p.subtract(BigInteger.TWO)));
            // cB = checkInput();
            cB = myRandom(arrayList,seed);
            secretKey(arrayList ,cB, p, seed);
            dB = g.modPow(cB, p);
            System.out.println("cB= " + cB);
            System.out.println("dB= " + dB);
            System.out.println("Генерируем случайное число k в диапазоне от 1 до " + (p.subtract(BigInteger.TWO)));
            k = BigInteger.valueOf(2).add(myRandom(arrayList,seed).mod(p.subtract(BigInteger.TWO)));
            System.out.println("k= " + k);
            System.out.print("Абонент А передает сообщение абоненту B. m < " + p + ". m = ");
            m = new BigInteger(textFieldSecretString.getText());
            if (m.compareTo(p) < 0) System.out.println("m= " + m);
            r = fastModPow(g, k, p);
            System.out.println("r= " + r);
            e = (m.multiply(fastModPow(dB, k, p))).mod(p);
            System.out.println("e= " + e);
            System.out.println("Абонент B получает данные от абонента A");
            m2 = (e.multiply(fastModPow(r, p.subtract(BigInteger.ONE).subtract(cB), p))).mod(p);
            System.out.println("m'= " + m2);
            if (m.equals(m2)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setContentText("Сообщение было передано");
                alert.showAndWait();
                System.out.println("m = " + m + "= m' = " + m2);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Сообщение не было передано");
                alert.showAndWait();
                System.out.println("m != m'");
            }
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

   /* public static int calculateT(BigInteger logX) {  // вычисляется величина t = logX , пока 2^powerRes <= logX
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


    public static BigInteger myRandom(ArrayList<Integer> arrayList, long seed) {
        BigInteger m = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
        BigInteger a = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
        BigInteger c = a.subtract(BigInteger.ONE); // простое большое число взаимное с m
        seed *= seed;
        return (a.multiply(BigInteger.valueOf(seed)).add(c)).mod(m);
    }

    public static void secretKey(ArrayList<Integer> arrayList, BigInteger c, BigInteger p, long seed) {
        while (c.compareTo(p.subtract(BigInteger.ONE)) >= 0 || c.compareTo(BigInteger.TWO) < 0) {
            if (c.compareTo(BigInteger.TWO) < 0) {
                System.out.println("c < 1. Введите сообщение заново:");
                c=   BigInteger.valueOf(2).add(myRandom(arrayList,seed).mod(p.subtract(BigInteger.TWO)));
            } else if (c.compareTo(p.subtract(BigInteger.ONE)) >= 0) {
                System.out.println("c > p-1. Введите сообщение заново:");
                c= BigInteger.valueOf(2).add(myRandom(arrayList,seed).mod(p.subtract(BigInteger.TWO)));
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

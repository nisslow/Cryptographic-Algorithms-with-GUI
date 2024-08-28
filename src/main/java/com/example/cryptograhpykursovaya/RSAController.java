package com.example.cryptograhpykursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.ResourceBundle;

public class RSAController implements Initializable {

    @FXML
    protected PasswordField passwordFieldSecretString;
    @FXML
    protected Button buttonTransferString;

    @FXML
    protected Button buttonClearText;

    @FXML
    protected void clearText(ActionEvent event) {
        passwordFieldSecretString.clear();
    }

    @FXML
    protected void transferString(ActionEvent event) {
        ArrayList<Integer> arrayList = eratosphenesPrimes(10000000, 100000000);
        BigInteger pB = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));                // большое простое
        BigInteger qB = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));                // большое простое
        BigInteger nB = pB.multiply(qB);                               // открытая информация

        if (!passwordFieldSecretString.getText().matches("\\d+") || passwordFieldSecretString.getText().isBlank() || new BigInteger(passwordFieldSecretString.getText()).compareTo(BigInteger.ONE) < 0
                || passwordFieldSecretString.getText().matches("-\\d+") || new BigInteger(passwordFieldSecretString.getText()).compareTo(nB.subtract(BigInteger.TWO)) >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Сообщение должно быть больше 1 и меньше Nb-2");
            alert.showAndWait();
        } else {
            BigInteger pA, qA, nA, phiA, phiB, cA, dA, cB, dB, e, m, m2;
            double currentTime = System.currentTimeMillis();// определяется настоящим временем в микросекундах
            long seed = (long) (currentTime % 100);
            pA = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));              // большое простое
            System.out.println("Абонент А генерирует p: " + pA);
            qA = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));        // большое простое
            System.out.println("Абонент А генерирует q: " + qA);
            nA = (pA.subtract(BigInteger.ONE)).multiply(qA);                               // открытая информация
            System.out.println("Абонент А вычисляет N: " + nA);
            phiA = (pA.subtract(BigInteger.ONE)).multiply(qA.subtract(BigInteger.ONE));  // (p-1)(q-1)
            System.out.println("Абонент А вычисляет phi: " + phiA);
            dA = myRandom(arrayList, seed);                // открытая информация
            //  System.out.println("Абонент А генерирует d: " + dA);
            while (dA.compareTo(phiA) > 0 || (EvklidSimple(dA, phiA).compareTo(BigInteger.ONE)) != 0) {/*System.out.println("Сгенерировалось неверное число (d > phi)" + dA);*/
                dA = myRandom(arrayList, seed);
            }
            System.out.println("Абонент А генерирует d: " + dA);
            BigInteger[] u = {phiA, BigInteger.ONE, BigInteger.ZERO}, v = {dA, BigInteger.ZERO, BigInteger.ONE};
            BigInteger[] x = GeneralEvklid(u, v);
            if (x[2].compareTo(BigInteger.ZERO) < 0) {
                cA = u[0].add(x[2]);
                System.out.println("Абонент А генерирует c: " + cA);    // секретная информация
            } else {
                cA = x[2];
                System.out.println("Абонент А генерирует c: " + cA);
            }
            //  System.out.println("ПРОВЕРКА cd mod phi: " + (cA.multiply(dA)).mod(phiA));  //ПРОВЕРКА
            pB = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));                // большое простое
            System.out.println("\nАбонент B генерирует p: " + pB);
            qB = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));                // большое простое
            System.out.println("Абонент B генерирует q: " + qB);
            nB = pB.multiply(qB);                               // открытая информация
            System.out.println("Абонент B вычисляет N: " + nB);
            phiB = (pB.subtract(BigInteger.ONE)).multiply(qB.subtract(BigInteger.ONE));
            System.out.println("Абонент B вычисляет phi: " + phiB);
            dB = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));                // открытая информация
            System.out.println("Абонент B генерирует d: " + dB);
            while (dB.compareTo(phiB) > 0) {
                System.out.println("Сгенерировалось невенрное число (d > phi)" + dB);
                dB = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
            }
            BigInteger[] u2 = {phiB, BigInteger.ONE, BigInteger.ZERO}, v2 = {dB, BigInteger.ZERO, BigInteger.ONE};
            BigInteger[] x2 = GeneralEvklid(u2, v2);
            if (x2[2].compareTo(BigInteger.ZERO) < 0) {
                cB = u2[0].add(x2[2]);
                System.out.println("Абонент B генерирует c: " + cB);    // секретная информация
            } else {
                cB = x2[2];
                System.out.println("Абонент B генерирует c: " + cB);
            }
            //  System.out.println("ПРОВЕРКА cd mod phi: " + (cB.multiply(dB)).mod(phiB));
            System.out.print("\nАбонент А передает сообщение абоненту B: ");
            m = new BigInteger(passwordFieldSecretString.getText());
            e = fastModPow(m, dB, nB);
            System.out.println("Абонент А шифрует сообщение, e: " + e);
            m2 = fastModPow(e, cB, nB);
            System.out.println("Абонент Б расшифровывает сообщение, m': " + m2);
            if (m.equals(m2)) {
                System.out.println("m = " + m + " = m' = " + m2);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setContentText("Сообщение было передано");
                alert.showAndWait();
            } else {
                System.out.println("m != m'");
                System.out.println("m != x4");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Сообщение не было передано");
                alert.showAndWait();
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
            arrayT[i] = arrayT[i - 1].multiply(arrayT[i - 1]).mod(mod);
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

    public static BigInteger myRandom(ArrayList<Integer> arrayList, long seed) {
        BigInteger m = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
        BigInteger a = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
        BigInteger c = a.subtract(BigInteger.ONE); // простое большое число взаимное с m
        seed *= seed;
        return (a.multiply(BigInteger.valueOf(seed)).add(c)).mod(m);
    }

    public static BigInteger EvklidSimple(BigInteger f, BigInteger g) {
        while (g.compareTo(BigInteger.ZERO) != 0) {
            BigInteger r = f.mod(g);
            f = g;
            g = r;
        }
        return f;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

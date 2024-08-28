package com.example.cryptograhpykursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class FeistelNetworkController implements Initializable {

    @FXML
    Button buttonPlainText;
    @FXML
    Button buttonEncryptedText;
    @FXML
    Button buttonDecryptedText;
    @FXML
    Button buttonFeistel;
    @FXML
    TextField textFieldPassword;

     @FXML
     protected void chooseFileWithPlainText(ActionEvent event){
     }
     @FXML
     protected void chooseFileForEncryptData(ActionEvent event){
     }
     @FXML
     protected void chooseFileForDecryptData(ActionEvent event){
     }
    @FXML
    protected void doFeistel(ActionEvent event) {

        StringBuilder keyStrBuilder = new StringBuilder(textFieldPassword.getText());
        String alphabetLow = "abcdefghijklmnopqrstuvwxyz ,.!?()—:";
        String alphabetBig = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ,.!?()—:";
        System.out.println(alphabetBig.length());
        StringBuilder cesarText = new StringBuilder();
        int shift = 1;
        System.out.print("Введите текст : ");

        StringBuilder textFromfile = new StringBuilder();



        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setContentText("Выберите файл, откуда считать данные");
        alert.showAndWait();

        FileChooser fileChooser = new FileChooser();
        File selectedFileRead = fileChooser.showOpenDialog(null);
        if (selectedFileRead != null) {
            try {
                textFromfile = new StringBuilder(Files.readString(Paths.get(selectedFileRead.getAbsolutePath())));

                System.out.println("\nС файла прочетно: \n" + textFromfile);           // МОЖНО ВЫВЕСТИ В КОНСОЛЬ
                System.out.println(textFromfile.length());
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setContentText("Данные считанны с файла " + selectedFileRead.getAbsolutePath());
                alert.showAndWait();

            } catch (IOException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Ошибка при чтении данных с файла " + selectedFileRead.getAbsolutePath());
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        }




        //  String fileData = "C:\\Users\\Стас\\IdeaProjects\\Feistel Network_9lab\\Feistel Network_9lab\\Data.txt";
        //  String fileEncryptedData = "C:\\Users\\Стас\\IdeaProjects\\Feistel Network_9lab\\Feistel Network_9lab\\Encrypted Data.txt";
        //  String fileDecryptedData = "C:\\Users\\Стас\\IdeaProjects\\Feistel Network_9lab\\Feistel Network_9lab\\Decrypted Data.txt";

        StringBuilder ecnryptedText = new StringBuilder();
        StringBuilder decryptedText = new StringBuilder();
        int keyLength = keyStrBuilder.length();

        int initTextLength = textFromfile.length();
        double blocksCount = Math.ceil((double) textFromfile.length() / (keyLength * 2));
        String[] key = new String[(int) (blocksCount * 2)];
        key[0] = String.valueOf(keyStrBuilder);


        System.out.println(blocksCount);
        String[] funcString = new String[(int) (blocksCount * 2)];
        String[] encryptedString = new String[(int) (blocksCount * 2)];
        String[] decryptedString = new String[(int) (blocksCount * 2)];

        String[] left = new String[(int) blocksCount];
        String[] right = new String[(int) blocksCount];
        String[] blocks = new String[(int) blocksCount];
        String[] subBlocks = new String[(int) (blocksCount * 2)];

        // ЗАШИФРОВАНИЕ И ЗАПИСЬ В ФАЙЛ

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setContentText("Выберите файл, куда записать зашифрованные данные");
        alert.showAndWait();

        fileChooser = new FileChooser();
        File selectedFileWriteEnc = fileChooser.showSaveDialog(null);

        divideIntoBlocks(keyLength, textFromfile, left, right, blocksCount, blocks, subBlocks);
        encryptFiestelNetwork(cesarText, alphabetLow, alphabetBig, key, keyStrBuilder, textFromfile, ecnryptedText, left, right, blocksCount, funcString, encryptedString);
        System.out.println(ecnryptedText + "\n" + " length: " + ecnryptedText.length());
        writeToFile(ecnryptedText, selectedFileWriteEnc.getAbsolutePath());

        // РАСШИФРОВАНИЕ И ЗАПИСЬ В ФАЙЛ

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setContentText("Выберите файл, куда записать расшифрованные данные");
        alert.showAndWait();

        fileChooser = new FileChooser();
        File selectedFileWriteDec = fileChooser.showSaveDialog(null);

        textFromfile = readFromFile(textFromfile, selectedFileWriteEnc.getAbsolutePath());
        divideIntoBlocks(keyLength, textFromfile, left, right, blocksCount, blocks, subBlocks);
        decryptFiestelNetwork(cesarText, alphabetLow, alphabetBig, key, keyStrBuilder, textFromfile, decryptedText, left, right, blocksCount, funcString, decryptedString);
        for (int i = initTextLength, c = 1, j = 0; i < decryptedText.length() - 1; j++) {
            decryptedText.deleteCharAt((decryptedText.length() - c));
        }
        decryptedText.deleteCharAt((decryptedText.length() - 1));
        System.out.println(decryptedText.length() - 1);
        writeToFile(decryptedText, selectedFileWriteDec.getAbsolutePath());

        // ЧТЕНИЕ С ФАЙЛА
        textFromfile = readFromFile(textFromfile, selectedFileWriteDec.getAbsolutePath());
        System.out.println("Расшифрованное сообщение : " + textFromfile + "\n");

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setContentText("Операция прошла успешно");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void divideIntoBlocks(int keyLength, StringBuilder text, String[] left, String[] right, double blocksCount, String[] blocks, String[] subBlocks) {
        int f = 0;
        int f1 = 0;
        int f2 = 0;
        if ((keyLength * 2) * blocksCount > text.length()) {
            for (int i = text.length(); i < (keyLength * 2) * blocksCount; i++) {
                text.insert(i, " ");
            }
        }
        System.out.println(text + "\n");
        for (int c = 0, h = keyLength * 2, l = 1, k = 0, i = 0; i < blocksCount; i++, k += 2, l++) {
            blocks[i] = text.substring(keyLength * k, h * l);

            System.out.println("\n" + i + " блок : " + blocks[i]);
            for (int z = 0, g = 1, j = 0; j < 2; j++) {                            // ПРОБЛЕМА! СУББЛОК = 2 = 3 почему то!!!!!!!ПРОБЛЕМА! СУББЛОК = 2 = 3 почему то!!!!!!!!!!!!!!!!!!!!!!!!
                subBlocks[f] = blocks[i].substring(z, keyLength * g);
                if ((f + 1) % 2 == 0) { //right subblock
                    right[f2] = subBlocks[f];
                    f2++;
                } else {    //left subblock
                    left[f1] = subBlocks[f];
                    f1++;
                }
                z = keyLength * g;
                g++;
                System.out.println(f + " суб блок : " + subBlocks[f]);
                f++;
            }
        }
    }

    public static StringBuilder encryptFiestelNetwork(StringBuilder cesarText, String alphabetLow, String alphabetBig, String[] key, StringBuilder keyStringBuilder, StringBuilder text, StringBuilder encryptedText, String[] left, String[] right, double blocksCount, String[] funcString, String[] encryptedString) {
        int z = 0;
        int g = 0;
        int funcCount = 0;
        int keyCount = 0;
        int encStringCount = 0;
        String tempStr;
        int tempStrInt;
        StringBuilder tempStringBuilder = new StringBuilder();
        //    StringBuilder tempR = new StringBuilder();
        for (int i = 0; i < blocksCount; i++) {
            System.out.println("\n" + i + " блок : ");
            if (i > 0) {
                cesar(keyStringBuilder, alphabetLow, alphabetBig, cesarText, 1);
                key[keyCount] = String.valueOf(cesarText);
                cesarText.delete(0, cesarText.length());
                keyStringBuilder = new StringBuilder(key[keyCount]);
            }
            for (int j = 0; j < key[0].length(); j++) { //RIGHT
                tempStrInt = (right[i].charAt(j) + key[keyCount].charAt(j)) % 255;
                tempStr = String.valueOf((char) tempStrInt);
                tempStringBuilder.append(tempStr);
            }
            funcString[funcCount] = tempStringBuilder.toString();
            tempStringBuilder.delete(0, tempStringBuilder.length());
            //    System.out.println("Функция F(i,Li-1,Ri) = " + funcString[funcCount]);
            for (int j = 0; j < left[i].length(); j++) { //LEFT
                tempStrInt = (left[i].charAt(j) + funcString[funcCount].charAt(j)) % 255;
                tempStr = String.valueOf((char) tempStrInt);
                tempStringBuilder.append(tempStr);
            }
            keyCount++;
            cesar(keyStringBuilder, alphabetLow, alphabetBig, cesarText, 1);
            key[keyCount] = String.valueOf(cesarText);                                           ////////////////////key+1 МОЖЕТ БЫТЬ ОШИБКА
            keyStringBuilder = new StringBuilder(key[keyCount]);
            encryptedString[encStringCount] = tempStringBuilder.toString();
            tempStringBuilder.delete(0, tempStringBuilder.length());

            for (int j = 0; j < key[0].length(); j++) { //RIGHT
                tempStrInt = (encryptedString[encStringCount].charAt(j) + key[keyCount].charAt(j)) % 255;
                tempStr = String.valueOf((char) tempStrInt);
                tempStringBuilder.append(tempStr);
            }
            funcCount++;
            funcString[funcCount] = String.valueOf(tempStringBuilder);
            tempStringBuilder.delete(0, tempStringBuilder.length());
            //    tempR.delete(0, tempR.length());

            for (int j = 0; j < key[0].length(); j++) { //LEFT
                tempStrInt = (right[i].charAt(j) + funcString[funcCount].charAt(j)) % 255;
                tempStr = String.valueOf((char) tempStrInt);   /////////////////////////////////ГДЕ I + 1 МОЖЕТ ЫБАТЬ ОШИБКА
                tempStringBuilder.append(tempStr);
            }
            encStringCount++;
            encryptedString[encStringCount] = tempStringBuilder.toString();
            System.out.println("Зашифрованный " + i + " блок : ");
            System.out.println(encryptedString[encStringCount - 1] + encryptedString[encStringCount]);

            cesarText.delete(0, cesarText.length());
            //    tempR.delete(0, tempR.length());
            tempStringBuilder.delete(0, tempStringBuilder.length());
            keyCount++;
            funcCount++;
            encStringCount++;

        }
        for (int i = 0; i < encryptedString.length; i++) {
            encryptedText.append(encryptedString[i]);
        }
        return encryptedText;
    }

    public static StringBuilder decryptFiestelNetwork(StringBuilder cesarText, String alphabetLow, String alphabetBig, String[] key, StringBuilder keyStringBuilder, StringBuilder encryptedText, StringBuilder decryptedText, String[] left, String[] right, double blocksCount, String[] funcString, String[] decryptedString) {
        int z = 0;
        int g = 0;
        int funcCount = 0;
        int keyCount = 0;
        int encStringCount = 0;
        String tempStr;
        int tempStrInt;
        StringBuilder tempStringBuilder = new StringBuilder();
        //    StringBuilder tempR = new StringBuilder();
        for (int i = 0; i < blocksCount; i++) {
            System.out.println("\n" + i + " блок : ");
            if (i > 0) {
                //      cesar(keyStringBuilder, alphabetLow, alphabetBig, cesarText, 1);
                //   key[keyCount] = String.valueOf(cesarText);
                //   cesarText.delete(0, cesarText.length());
                keyStringBuilder = new StringBuilder(key[keyCount]);
            }
            for (int j = 0; j < key[0].length(); j++) { //RIGHT
                tempStrInt = (left[i].charAt(j) + key[keyCount + 1].charAt(j)) % 255;
                tempStr = String.valueOf((char) tempStrInt);
                tempStringBuilder.append(tempStr);
            }
            funcString[funcCount] = tempStringBuilder.toString();
            tempStringBuilder.delete(0, tempStringBuilder.length());
            //  System.out.println("Функция F(i,Li-1,Ri) = " + funcString[funcCount]);

            for (int j = 0; j < left[i].length(); j++) { //LEFT

                if ((right[i].charAt(j) - funcString[funcCount].charAt(j)) < 0) {
                    tempStrInt = (((right[i].charAt(j) - funcString[funcCount].charAt(j)) + 255) % 255);
                    tempStr = String.valueOf((char) tempStrInt);
                    tempStringBuilder.append(tempStr);
                } else {
                    tempStrInt = ((right[i].charAt(j) - funcString[funcCount].charAt(j)) % 255);
                    tempStr = String.valueOf((char) tempStrInt);
                    tempStringBuilder.append(tempStr);
                }
            }
            keyCount++;
            //  cesar(keyStringBuilder, alphabetLow, alphabetBig, cesarText, 1);
            //        key[keyCount] = String.valueOf(cesarText);                                           ////////////////////key+1 МОЖЕТ БЫТЬ ОШИБКА
            keyStringBuilder = new StringBuilder(key[keyCount]);
            decryptedString[encStringCount] = tempStringBuilder.toString();
            tempStringBuilder.delete(0, tempStringBuilder.length());

            for (int j = 0; j < key[0].length(); j++) { //RIGHT
                tempStrInt = (decryptedString[encStringCount].charAt(j) + key[keyCount - 1].charAt(j)) % 255;
                tempStr = String.valueOf((char) tempStrInt);
                tempStringBuilder.append(tempStr);
            }
            funcCount++;
            funcString[funcCount] = String.valueOf(tempStringBuilder);
            tempStringBuilder.delete(0, tempStringBuilder.length());
            //    tempR.delete(0, tempR.length());

            for (int j = 0; j < left[i].length(); j++) { //LEFT

                if ((left[i].charAt(j) - funcString[funcCount].charAt(j)) < 0) {
                    tempStrInt = (((left[i].charAt(j) - funcString[funcCount].charAt(j)) + 255) % 255);
                    tempStr = String.valueOf((char) tempStrInt);
                    tempStringBuilder.append(tempStr);
                } else {
                    tempStrInt = ((left[i].charAt(j) - funcString[funcCount].charAt(j)) % 255);
                    tempStr = String.valueOf((char) tempStrInt);
                    tempStringBuilder.append(tempStr);
                }
            }

            encStringCount++;
            decryptedString[encStringCount] = tempStringBuilder.toString();
            System.out.println("Расшифрованный " + i + " блок : ");
            System.out.println(decryptedString[encStringCount] + decryptedString[encStringCount - 1]);

            cesarText.delete(0, cesarText.length());
            //    tempR.delete(0, tempR.length());
            tempStringBuilder.delete(0, tempStringBuilder.length());
            keyCount++;
            funcCount++;
            encStringCount++;

        }
        for (int i = 0; i < blocksCount * 2; ) {
            decryptedText.append(decryptedString[i + 1]).append(decryptedString[i]);
            i += 2;
        }
        return decryptedText;
    }

    public static StringBuilder readFromFile(StringBuilder text, String filePath) {
        try {
            text = new StringBuilder(Files.readString(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\nС файла прочетно: \n" + text + "\n" + text.length());
        return text;
    }

    public static void writeToFile(StringBuilder ecnryptedText, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath); //, true если нужно добавлять, а не перезаписывать
            for (int i = 0; i < ecnryptedText.length(); i++) {
                fileWriter.write(ecnryptedText.charAt(i));
            }
            fileWriter.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setContentText("Данные записаны в файл " + filePath);
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Ошибка при записи данных в файла " + filePath);
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    private static void cesar(StringBuilder sb, String arrRlc, String arr, StringBuilder cesarText, int c) {
        for (int i = 0; i != sb.length(); i++) {
            for (int j = 0; j != arr.length(); j++) {
                if (sb.charAt(i) == '\n') {
                    cesarText.append('\n');
                    break;
                } else if (sb.charAt(i) == arr.charAt(j)) {
                    if (j + c > arr.length() - 1) {
                        int countzero = 0;
                        while (countzero != c) {
                            if (j + countzero == arr.length() - 1) {
                                j = 0;
                                countzero = c - countzero - 1;
                                cesarText.append(arr.charAt(j + countzero));
                                break;
                            } else countzero++;
                        }
                    } else cesarText.append(arr.charAt(j + c));
                    break;
                } else if (sb.charAt(i) == arrRlc.charAt(j)) {
                    if (j + c > arr.length() - 1) {
                        int countzero = 0;
                        while (countzero != c) {
                            if (j + countzero == arr.length() - 1) {
                                j = 0;
                                countzero = c - countzero - 1;
                                cesarText.append(arrRlc.charAt(j + countzero));
                                break;
                            } else countzero++;
                        }
                    } else cesarText.append(arrRlc.charAt(j + c));
                    break;
                }
            }
        }
        System.out.println("Ключ: ");
        for (int i = 0; i != cesarText.length(); i++) {
            System.out.print(cesarText.charAt(i));
        }
        System.out.println("\n");
    }

}

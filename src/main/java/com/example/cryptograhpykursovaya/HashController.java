package com.example.cryptograhpykursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.ResourceBundle;

public class HashController implements Initializable {

    @FXML
    protected CheckBox checkBoxReadFile;
    @FXML
    protected CheckBox checkBoxWriteIterationsFile;
    @FXML
    protected CheckBox checkBoxWriteHashResultFile;
    @FXML
    protected CheckBox checkBoxReadTextField;
    @FXML
    protected CheckBox checkBoxWriteInTextField;
    @FXML
    protected CheckBox checkBoxWriteHashResultTextField;
    @FXML
    protected Button buttonDoOperations;
    @FXML
    protected Button buttonClearTextArea;
    @FXML
    protected TextArea textArea;

    int isCheckBoxReadFile;
    int isCheckBoxWriteIterationsFile;
    int isCheckBoxWriteHashResultFile;
    int isCheckBoxReadTextField;
    int isCheckBoxWriteInTextField;
    int isCheckBoxWriteHashResultTextField;

    @FXML
    protected void readFileCB(ActionEvent event) {
        if (checkBoxReadFile.isSelected()) isCheckBoxReadFile = 1;
        else isCheckBoxReadFile = 0;
    }

    @FXML
    protected void writeIterationsFileCB(ActionEvent event) {
        if (checkBoxWriteIterationsFile.isSelected()) isCheckBoxWriteIterationsFile = 1;
        else isCheckBoxWriteIterationsFile = 0;
    }

    @FXML
    protected void writeHashResultFileCB(ActionEvent event) {
        if (checkBoxWriteHashResultFile.isSelected()) isCheckBoxWriteHashResultFile = 1;
        else isCheckBoxWriteHashResultFile = 0;
    }

    @FXML
    protected void readTextFieldCB(ActionEvent event) {
        if (checkBoxReadTextField.isSelected()) {
            isCheckBoxReadTextField = 1;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setContentText("Запишите текст в консоль ниже");
            alert.showAndWait();
        } else isCheckBoxReadTextField = 0;
    }

    @FXML
    protected void writeInTextFieldCB(ActionEvent event) {
        if (checkBoxWriteInTextField.isSelected()) isCheckBoxWriteInTextField = 1;
        else isCheckBoxWriteInTextField = 0;
    }

    @FXML
    protected void writeHashResultTextFieldCB(ActionEvent event) {
        if (checkBoxWriteHashResultTextField.isSelected()) isCheckBoxWriteHashResultTextField = 1;
        else isCheckBoxWriteHashResultTextField = 0;
    }


    protected BigInteger p;
    int one, two, three;

    @FXML
    protected void clearTextArea(ActionEvent event) {
        textArea.clear();
    }

    @FXML
    protected void doOperations(ActionEvent event) {
        ArrayList<Integer> arrayList = eratosphenesPrimes(10000000, 100000000);
        p = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
        if (!checkBoxReadFile.isSelected() && !checkBoxReadTextField.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Выберите как считать текст");
            alert.showAndWait();
        } else if (!checkBoxWriteIterationsFile.isSelected() && !checkBoxWriteInTextField.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Выберите как записать значения итераций");
            alert.showAndWait();
        } else if (!checkBoxWriteHashResultFile.isSelected() && !checkBoxWriteHashResultTextField.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Выберите как записать результат Хэй-функции");
            alert.showAndWait();
        } else if (checkBoxReadFile.isSelected() && checkBoxReadTextField.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Выберите либо чтение с файла либо с консоли");
            alert.showAndWait();
        } else {
            BigInteger n, q, oldRes;
            String stringText = "";
            q = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
            n = p.multiply(q);
            BigInteger m0 = BigInteger.valueOf(arrayList.get(((int) (Math.random() * (arrayList.size())))));
            //String path = "C:\\Users\\Стас\\IdeaProjects\\crypto7_HashFunc\\";
            // String fileForReading = path +"Data.txt";
            //    String fileForReading = "C:\\Users\\Стас\\IdeaProjects\\crypto7_HashFunc\\Data.txt";
            //    String fileForIterations = "C:\\Users\\Стас\\IdeaProjects\\crypto7_HashFunc\\Iterations data.txt";
            //   String fileForHashResult = "C:\\Users\\Стас\\IdeaProjects\\crypto7_HashFunc\\Result hash.txt";
            textArea.setWrapText(true);
            System.out.println("p = " + p + " / q = " + q + " / n = " + n);

            //   boolean flag = true;
            //  while (flag) {
            System.out.println(
                    "Считать текст:\n" +
                            "1) С консоли\n" +
                            "2) С файла"
            );
            if (isCheckBoxReadTextField == 1) {
                one = 1;
            } else if (isCheckBoxReadFile == 1) {
                one = 2;
            }
            System.out.println("-----------------------------------------------------");
            System.out.println(
                    "Значения итераций:\n" +
                            "1) Вывести в консоль\n" +
                            "2) Сохранить в файл"
            );
            if (isCheckBoxWriteInTextField == 1) {
                two = 1;
            } else if (isCheckBoxWriteIterationsFile == 1) {
                two = 2;
            }
            System.out.println("-----------------------------------------------------");
            System.out.println(
                    "Значение Хэш-Функции:\n" +
                            "1) Вывести в консоль\n" +
                            "2) Сохранить в файл"
            );
            if (isCheckBoxWriteHashResultTextField == 1) {
                three = 1;
            } else if (isCheckBoxWriteHashResultFile == 1) {
                three = 2;
            }
            switch (one) {
                case 1:
                    System.out.print("\nВведите строку: ");
                    stringText = textArea.getText();
                    System.out.println(stringText.length());
                  /*  char[] charsKeybord = new char[stringText.length()];

                    for (int i = 0; i < charsKeybord.length; i++) {
                        charsKeybord[i] = stringText.charAt(i);
                        System.out.println(charsKeybord[i] +" ");
                    }*/
                    //   scanner.close();
                    break;
                case 2:

                    FileChooser fileChooser = new FileChooser();
                    File selectedFile = fileChooser.showOpenDialog(null);
                    if (selectedFile != null) {
                        try {
                            stringText = Files.readString(Paths.get(selectedFile.getAbsolutePath()));
                            System.out.println("\nС файла прочетно: \n" + stringText);           // МОЖНО ВЫВЕСТИ В КОНСОЛЬ
                            System.out.println(stringText.length());

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Успех");
                            alert.setContentText("Данные считанны с файла " + selectedFile.getAbsolutePath());
                            alert.showAndWait();
                        } catch (IOException ex) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка");
                            alert.setContentText("Ошибка при чтении данных с файла " + selectedFile.getAbsolutePath() + ": " + ex.getMessage());
                            alert.showAndWait();
                        }
                    }
                    //      stringText = Files.readString(Paths.get(fileForReading));
                    //      System.out.println("\nС фалйа прочетно: \n" + stringText);           // МОЖНО ВЫВЕСТИ В КОНСОЛЬ
                    //      System.out.println(stringText.length());

                    /* int fileSize = (int) new File(file).length();
                    char[] chars = new char[fileSize];
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        br.read(chars, 0, fileSize);
                        System.out.println("Текст был считан с файла");
                        for (char c : chars) {
                            System.out.print(c);
                        }
                        */

                    System.out.println("\n");
                    break;
                default:
                    System.out.println("Введите 1 или 2");
                    break;
            }

            System.out.print("Текст переведен в числовой тип: ");
            long[] stringToASCII = new long[stringText.length()];
            for (int i = 0; i != stringText.length(); i++) {
                stringToASCII[i] = stringText.charAt(i);
                //   System.out.print(stringToASCII[i] + " ");
            }

            long[] resStringArray = new long[stringText.length()];
            String[] resString = new String[stringText.length()];

            //первая итерация----------------------------
            System.out.println("\n");
            resStringArray[0] = m0.xor(BigInteger.valueOf(stringToASCII[0])).modPow(BigInteger.valueOf(2), n).longValue();
            resString[0] = Long.toBinaryString(resStringArray[0]);
            //--------------------------------------------
            for (int i = 1; i < stringText.length(); i++) {
                resStringArray[i] = (BigInteger.valueOf(resStringArray[i - 1]).xor(BigInteger.valueOf(stringToASCII[i]))).modPow(BigInteger.valueOf(2), n).longValue();
                resString[i] = Long.toBinaryString(resStringArray[i]);

            }


            switch (two) {
                case 1: //Вывод в консоль
                    textArea.appendText("\n1) " + stringText.charAt(0) + " ([" + m0 + " Xor " + stringToASCII[0] + "]^2)" + " mod " + n + " = " + resStringArray[0] + " = " + resString[0]);
                    System.out.println("\n1) " + stringText.charAt(0) + " ([" + m0 + " Xor " + stringToASCII[0] + "]^2)" + " mod " + n + " = " + resStringArray[0] + " = " + resString[0]);
                    for (int i = 1; i < resStringArray.length; i++) {
                        textArea.appendText("\n\n" + i + 1 + ") " + stringText.charAt(i) + " ([" + resStringArray[i - 1] + " Xor " + stringToASCII[i] + "]^2)" + " mod " + n + " = " + resStringArray[i] + " = " + resString[i]);
                        System.out.println(i + 1 + ") " + stringText.charAt(i) + " ([" + resStringArray[i - 1] + " Xor " + stringToASCII[i] + "]^2)" + " mod " + n + " = " + resStringArray[i] + " = " + resString[i]);
                    }
                    break;
                case 2:

                    String[] lines = new String[resString.length];
                    lines[0] = "1) " + stringText.charAt(0) + " ([" + m0 + " Xor " + stringToASCII[0] + "]^2)" + " mod " + n + " = " + resStringArray[0] + " = " + resString[0];
                    for (int i = 1; i < resStringArray.length; i++) {
                        lines[i] = i + 1 + ") " + stringText.charAt(i) + " ([" + resStringArray[i - 1] + " Xor " + stringToASCII[i] + "]^2)" + " mod " + n + " = " + resStringArray[i] + " = " + resString[i];
                    }

                    FileChooser fileChooser = new FileChooser();
                    File selectedFile = fileChooser.showSaveDialog(null);
                    if (selectedFile != null) {
                        try {
                            FileWriter fileWriter = new FileWriter(selectedFile); //, true если нужно добавлять, а не перезаписывать
                            fileWriter.write(lines[0] + "\n");
                            for (int i = 1; i < lines.length; i++) {
                                //   System.out.println(i + 1 + ") ([" + resStringArray[i-1] + " Xor " + stringToASCII[i] + "]^2)" + " mod " + n + " = " + resStringArray[i] + " - " + resString[i]+"\n");
                                //   fileWriter.write(i + 1 + ") ([" + resStringArray[i-1] + " Xor " + stringToASCII[i] + "]^2)" + " mod " + n + " = " + resStringArray[i] + " - " + resString[i]+"\n");
                                fileWriter.write(lines[i] + "\n\n");
                            }
                            fileWriter.close();

                            System.out.println("\nВ файл записано: \n" + stringText);           // МОЖНО ВЫВЕСТИ В КОНСОЛЬ
                            System.out.println(stringText.length());

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Успех");
                            alert.setContentText("Данные записаны в файл " + selectedFile.getAbsolutePath());
                            alert.showAndWait();
                        } catch (IOException ex) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка");
                            alert.setContentText("Ошибка при записи данных в файла " + selectedFile.getAbsolutePath() + ": " + ex.getMessage());
                            alert.showAndWait();
                        }
                    }

                    break;
                default:
                    System.out.println("Введите 1 или 2");
                    break;
            }

            switch (three) { //результат хэш функции
                case 1:
                    textArea.appendText("\n\nРезультат Хэш-Функции (десятичное - двочиное): " + resStringArray[resStringArray.length - 1] + " = " + resString[resString.length - 1]);
                    System.out.println("Результат Хэш-Функции (десятичное - двочиное): " + resStringArray[resStringArray.length - 1] + " = " + resString[resString.length - 1]);
                    break;
                case 2:

                    FileChooser fileChooser = new FileChooser();
                    File selectedFile = fileChooser.showSaveDialog(null);
                    if (selectedFile != null) {
                        try {
                            FileWriter fileWriter = new FileWriter(selectedFile); //, true если нужно добавлять, а не перезаписывать
                            fileWriter.write(resStringArray[resStringArray.length - 1] + " - " + resString[resString.length - 1]);
                            fileWriter.close();

                            System.out.println("\nВ файл записано: \n" + stringText);           // МОЖНО ВЫВЕСТИ В КОНСОЛЬ
                            System.out.println(stringText.length());

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Успех");
                            alert.setContentText("Данные записаны в файл " + selectedFile.getAbsolutePath());
                            alert.showAndWait();
                        } catch (IOException ex) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка");
                            alert.setContentText("Ошибка при записи данных в файла " + selectedFile.getAbsolutePath() + ": " + ex.getMessage());
                            alert.showAndWait();
                        }
                    }

                    break;
                default:
                    System.out.println("Введите 1 или 2");
                    break;
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

package com.example.cryptograhpykursovaya;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


//--module-path "C:\Users\Стас\Downloads\openjfx-19_windows-x64_bin-sdk\javafx-sdk-19\lib"
//--add-modules javafx.controls,javafx.fxml


// ДЛЯ ОБОЩЕННОГО Х и У
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        LoggerUtil.logInfo("Starting FXML");
        Parent parent =  FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        LoggerUtil.logInfo("Starting Scene");
        Scene scene = new Scene(parent);
        stage.setTitle("DB MS SQL!");
        stage.setScene(scene);
        stage.show();
        LoggerUtil.logInfo("Application has successfully started");
    }
    public static void main(String[] args) {
        launch();
    }
}
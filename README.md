# Cryptographic-Algorithms-with-GUI

This project is a Java application that allows users to experiment with various cryptographic algorithms through a graphical user interface (GUI).
Project Structure
The project is organized in the com.example.cryptographykursovaya package and contains the following classes:

- DiffieHellmanController
- ElGamalController
- EvklidController
- FastModPowController
- FeistelNetworkController
- HashController
- HelloApplication
- HelloController
- LoggerUtil
- RSAController
- ShamirController
- SieveOfEratosthenesController

## Description
This application provides a user-friendly interface for testing and understanding different cryptographic algorithms. Users can interact with the GUI to see how various cryptographic methods work in practice.
Technologies Used

- Java 19
- JavaFX 19 (for the graphical user interface)
- Java Util

## Installation and Setup

Ensure you have Java 19 installed on your system.
The project uses Maven for dependency management. JavaFX 19 will be automatically downloaded and included in the project.
When setting up the project in your IDE, add the following VM options:
Copy--module-path "path\openjfx-19_windows-x64_bin-sdk\javafx-sdk-19\lib" --add-modules javafx.controls,javafx.fxml
Replace path with the actual path to your JavaFX SDK on your system.

## Running the Application
To run the application:

- Open the project in your preferred Java IDE.
- Ensure all dependencies are properly loaded.
- Run the HelloApplication class, which serves as the entry point for the JavaFX application.

## Features
The application demonstrates the following cryptographic algorithms and concepts:

- Diffie-Hellman key exchange
- ElGamal encryption
- Euclidean algorithm + General Euclidean algorithm
- Fast modular exponentiation
- Feistel network
- Hash functions
- RSA encryption
- Shamir's secret sharing
- Sieve of Eratosthenes

Each algorithm has its own controller class, allowing users to input data and see the results of the cryptographic operations.

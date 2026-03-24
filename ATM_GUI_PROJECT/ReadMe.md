# ByteBank ATM System

### Overview
This project is a Java-based ATM simulation that connects a graphical user interface with a backend banking logic. It allows users to create a personal account, choose from a list of different ATM locations, and perform standard banking transactions like depositing or withdrawing money. A "Saboteur" logic is included that randomly disables ATMs to simulate real-world hardware failures.

---

### Features
* **User Initialization**: On startup, the system prompts for a name, initial balance, and a 4-digit security PIN to create a `BankAccount`.
* **ATM Selection Grid**: A visual interface displays 10 different ATM locations based on real-world spots like "Mürzzuschlag" or "Leoben".
* **Real-time Console**: A text area in the UI logs every system action, including successful logins and error reports.
* **Transaction Management**: Supports withdrawing money, depositing funds, and checking the current balance.
* **Saboteur System**: Every access triggers a chance for a random ATM in the network to be sabotaged and set to "offline" status.

---

### Technical Structure
The project uses a modular approach with custom Exception classes for error handling:

* **Core Logic**: 
    * `BankAccount.java`: Handles customer data, PIN storage, and balance.
    * `ATM.java`: Manages the link between the user account and the terminal.
    * `Saboteur.java`: Contains the logic for random ATM status changes.
* **Custom Exceptions**: 
    * `InvalidPinTypeException`: Thrown if the PIN is not exactly 4 digits.
    * `InvalidDrawAmount`: Triggered when withdrawal exceeds the current balance.
    * `InputTypeMissmatch`: Handles invalid data types during user input.
* **Interface**: 
    * `GUI.java`: The main entry point using Java Swing for the graphical interface.

---

### Project Background
This is a school project created to practice the fundamentals of Object-Oriented Programming (OOP) in Java. The main focus was on learning how to structure classes, handle custom exceptions, and specifically understanding the use of `static` members (like global transaction and account counters).

**Note on Development**: While the core logic and exception handling were written manually, the Graphical User Interface (GUI) was generated with the help of AI to achieve a more modern look and feel.

---

### Setup and Execution

1. **Requirements**: 
   * Java Runtime Environment (JRE) 17 or higher.
2. **Installation**:
   * Import all `.java` files into your IDE.
   * Ensure all files are in the package `atm_wetzlhuetter`.
   * Delete `module-info.java` if you see "package javax.swing is not accessible" errors.
3. **Running**:
   * Start the application by running the `main` method in `GUI.java`.

---

### How to Download and Run (.exe)
If you just want to test the application without looking at the code, follow these steps:

1. **Go to Releases**: Navigate to the "Releases" section on the right side of this GitHub repository.
2. **Download**: Find the latest version and download the `ByteBank_ATM.exe` file from the "Assets" section.
3. **Java Requirement**: Make sure you have at least Java 17 installed on your Windows machine, as the executable requires a local Java Runtime Environment to start.
4. **Launch**: Double-click the `.exe` file. If Windows shows a "SmartScreen" warning (because the file is not digitally signed), click on "More info" and then "Run anyway".
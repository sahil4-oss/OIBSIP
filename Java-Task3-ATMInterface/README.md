ATM BANKING SYSTEM - JAVA SWING
OSIS INTERNSHIP PROJECT

PROJECT OVERVIEW
This project is a desktop ATM Banking System developed using Java. The application
combines Java OOP concepts with a Java Swing graphical user interface.

FRONTEND
- Java Swing
- JFrame, JPanel, JLabel, JButton, JTextField, JPasswordField
- CardLayout for Login and Dashboard screens
- JOptionPane for transaction forms and alerts
- Custom layout, colors, typography and validation

BACKEND / CORE LOGIC
- Java OOP
- Bank class for account management
- Account class for balance and authentication
- Transaction class for transaction records
- HashMap for account lookup
- ArrayList for transaction history

FEATURES
1. Secure User ID + PIN login
2. Maximum 3 login attempts
3. Professional ATM dashboard
4. Balance inquiry / refresh
5. Cash withdrawal
6. Cash deposit
7. Fund transfer between accounts
8. Transaction history
9. Input validation and error handling
10. Logout confirmation

DEMO ACCOUNTS
User ID: user1 | PIN: 1234 | Balance: 10000
User ID: user2 | PIN: 5678 | Balance: 5000

HOW TO RUN
1. Open a terminal in this folder.
2. Compile: javac *.java
3. Run: java Main

PROJECT STRUCTURE
Main.java       -> Application entry point
ATMFrame.java   -> Java Swing frontend
ATM.java        -> Console ATM implementation
Bank.java       -> Account storage and lookup
Account.java    -> Account, PIN and balance operations
Transaction.java-> Transaction record

TECHNOLOGIES
Java | Java Swing | OOP | Collections Framework

NOTE
This is an educational internship project. Demo account credentials are stored
locally for demonstration purposes and are not suitable for production banking.

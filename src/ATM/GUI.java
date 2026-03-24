package ATM;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class GUI {
    private BankAccount userAccount;
    private ATM[] atms = new ATM[10];
    private Saboteur saboteur = new Saboteur();
    private ATM selectedAtm;

    private JFrame frame;
    private JTextArea displayArea;
    private JPanel atmGridPanel;
    
    // Design Colors
    private final Color BG_COLOR = new Color(25, 25, 30);
    private final Color PANEL_COLOR = new Color(40, 40, 45);
    private final Color ACCENT_COLOR = new Color(0, 180, 255);
    private final Color TEXT_COLOR = new Color(220, 220, 220);

    public GUI() {
        if (createUser()) { // Zuerst User erstellen
            setupAtms();    // Dann ATMs laden
            initializeUI(); // Dann Haupt-GUI zeigen
        }
    }

    // --- NEU: User selbst erstellen ---
    private boolean createUser() {
        JTextField nameField = new JTextField("John Doe");
        JTextField saldoField = new JTextField("1000.0");
        JPasswordField pinField = new JPasswordField();

        Object[] message = {
            "Customer Name:", nameField,
            "Initial Deposit (€):", saldoField,
            "Set 4-Digit PIN:", pinField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Create New Bank Account", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                double saldo = Double.parseDouble(saldoField.getText());
                int pin = Integer.parseInt(new String(pinField.getPassword()));

                userAccount = new BankAccount(name, saldo);
                userAccount.createPin(pin);
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid Input! Please check PIN (4 digits) and Saldo.");
                return createUser(); // Neustart bei Fehler
            }
        }
        return false;
    }

    private void setupAtms() {
        String[] locations = {
            "Main Square", "Central Station", "Shopping Mall", "Airport Gate A",
            "University Campus", "City Park", "Industrial Area", "Old Town",
            "Business District", "Subway Entrance"
        };
        for (int i = 0; i < 10; i++) {
            atms[i] = new ATM("ATM-" + (i + 1), locations[i], "SN-100" + i, saboteur);
        }
    }

    private void initializeUI() {
        frame = new JFrame("ByteBank Terminal - User: " + userAccount.getCustomerName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getContentPane().setBackground(BG_COLOR);
        frame.setLayout(new BorderLayout(15, 15));

        // --- Header ---
        JLabel header = new JLabel("SELECT AN AVAILABLE ATM TERMINAL", SwingConstants.CENTER);
        header.setForeground(ACCENT_COLOR);
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.setBorder(new EmptyBorder(20, 0, 10, 0));
        frame.add(header, BorderLayout.NORTH);

        // --- Center: ATM Selection Grid ---
        atmGridPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        atmGridPanel.setBackground(BG_COLOR);
        atmGridPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        for (int i = 0; i < atms.length; i++) {
            ATM current = atms[i];
            JButton atmBtn = new JButton("<html><center><b>" + current.getLocation() + "</b><br><font size='3'>" + current.getSerialNumber() + "</font></center></html>");
            atmBtn.setBackground(PANEL_COLOR);
            atmBtn.setForeground(TEXT_COLOR);
            atmBtn.setFocusPainted(false);
            atmBtn.setBorder(new LineBorder(ACCENT_COLOR, 1));
            
            int index = i;
            atmBtn.addActionListener(e -> selectAtm(index));
            atmGridPanel.add(atmBtn);
        }
        frame.add(atmGridPanel, BorderLayout.CENTER);

        // --- South: Display Console ---
        displayArea = new JTextArea(5, 40);
        displayArea.setEditable(false);
        displayArea.setBackground(Color.BLACK);
        displayArea.setForeground(Color.CYAN);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        displayArea.setText(" SYSTEM READY.\n LOGGED IN AS: " + userAccount.getCustomerName() + "\n CURRENT GLOBAL ACCOUNTS: " + BankAccount.getAccountCount());
        
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBorder(new TitledBorder(new LineBorder(ACCENT_COLOR), "Console Output", TitledBorder.LEFT, TitledBorder.TOP, null, ACCENT_COLOR));
        frame.add(scroll, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void selectAtm(int index) {
        saboteur.destroyRandAtm(); // Saboteur zerschießt irgendwo einen ATM
        selectedAtm = atms[index];

        if (!selectedAtm.getStatus()) {
            displayArea.append("\n [!] ERROR: ATM AT " + selectedAtm.getLocation() + " IS OFFLINE (SABOTAGED).");
            JOptionPane.showMessageDialog(frame, "This ATM is out of order!", "System Failure", JOptionPane.ERROR_MESSAGE);
            return;
        }

        displayArea.append("\n [+] ACCESSING ATM AT " + selectedAtm.getLocation() + "...");
        
        String pinStr = JOptionPane.showInputDialog(frame, "Enter PIN for " + userAccount.getCustomerName() + ":");
        if (pinStr == null) return;

        try {
            int pin = Integer.parseInt(pinStr);
            if (selectedAtm.checkPin(userAccount, pin)) {
                openAtmMenu();
            } else {
                displayArea.append("\n [!] INVALID PIN.");
                JOptionPane.showMessageDialog(frame, "Access Denied: Wrong PIN.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid Input.");
        }
    }

    private void openAtmMenu() {
        String[] options = {"Withdraw", "Deposit", "Balance", "Logout"};
        int choice = JOptionPane.showOptionDialog(frame, 
            "Connected to: " + selectedAtm.getLocation() + "\nWhat do you want to do?", 
            "ATM Menu", 0, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        try {
            if (choice == 0) { // Withdraw
                String amtStr = JOptionPane.showInputDialog("Enter amount:");
                if (amtStr == null) return;
                double amt = Double.parseDouble(amtStr);
                
                if (selectedAtm.drawMoney(userAccount, amt)) {
                    userAccount.withdraw(amt);
                    displayArea.append("\n [✓] WITHDRAWAL: " + amt + " €. NEW SALDO: " + userAccount.getSaldo() + " €");
                }
            } else if (choice == 1) { // Deposit
                String amtStr = JOptionPane.showInputDialog("Enter amount:");
                if (amtStr == null) return;
                double amt = Double.parseDouble(amtStr);
                
                selectedAtm.depositMoney(userAccount, amt);
                displayArea.append("\n [✓] DEPOSIT: " + amt + " €. NEW SALDO: " + userAccount.getSaldo() + " €");
            } else if (choice == 2) { // Balance
                displayArea.append("\n [ℹ] BALANCE CHECK: " + userAccount.getSaldo() + " €");
                JOptionPane.showMessageDialog(frame, "Current Saldo: " + userAccount.getSaldo() + " €");
            }
        } catch (Exception e) {
            displayArea.append("\n [!] TRANSACTION ERROR: " + e.getMessage());
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
package ui;

import account.Account;
import account.ChequingAccount;
import atm.ExternalFiles;
import atm.FileHandler;
import atm.User;

import javax.swing.*;
import java.awt.*;

public class OpenAccountMenu extends SubMenu {
    private JComboBox<Class<Account>> accountSelection;
    private JButton request;
    private JCheckBox setPrimary;

    private FileHandler fileHandler = new FileHandler();

    @SuppressWarnings("unchecked")
    OpenAccountMenu(User user) {
        super("Open Account");
        request = new JButton("Request");
        setPrimary = new JCheckBox("Set Primary");
        setPrimary.setEnabled(false);

        accountSelection = new JComboBox<>(Account.OWNABLE_ACCOUNT_TYPES);

        accountSelection.addActionListener(e -> {
            Class selectedAccount = (Class) accountSelection.getSelectedItem();

            if (selectedAccount == ChequingAccount.class) {
                setPrimary.setEnabled(true);
            } else {
                setPrimary.setEnabled(false);
            }
        });

        request.addActionListener(e -> {
            boolean primary = false;
            Class<Account> accountType = (Class<Account>) accountSelection.getSelectedItem();
            if (setPrimary.isSelected()) {
                primary = true;
            }

            if (accountType != null) {
                if (JOptionPane.showConfirmDialog(this,
                        "Are you sure to request to create a " + accountType.getSimpleName() + "?",
                        "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) ==
                        JOptionPane.YES_OPTION) {
                            fileHandler.saveTo(ExternalFiles.ACCOUNT_CREATION_REQUEST_FILE,
                            String.format("%s %s %s", user.getUserName(), accountType, primary));

                    JOptionPane.showMessageDialog(
                            this, "Account creation successfully requested!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        initializeLayout();
        setVisible(true);
    }

    private void initializeLayout() {
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        flowLayout.setVgap(10);
        flowLayout.setHgap(10);

        JPanel accountRequestPanel = new JPanel(flowLayout);
        accountRequestPanel.add(new JLabel("Select Account: "));
        accountRequestPanel.add(accountSelection);
        accountRequestPanel.add(setPrimary);
        accountRequestPanel.add(request);

        container.add(accountRequestPanel);
    }
}

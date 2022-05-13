package com.alejandrov.frontend;

import javax.swing.*;

public class ValidacionesException extends Exception {

    public ValidacionesException(String message, JFrame parent) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}

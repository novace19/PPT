package cz.vspj.t18;

import cz.vspj.t18.data.CalculationHistory;
import cz.vspj.t18.logic.FactorialService;
import cz.vspj.t18.presentation.CalculatorPanel;
import javax.swing.*;

/** Vstupní bod a sestavení jednotlivých vrstev aplikace. */
public final class Main {
    private Main() { }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Kalkulačka faktoriálu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new CalculatorPanel(new FactorialService(new CalculationHistory())));
            frame.pack(); frame.setMinimumSize(frame.getSize());
            frame.setLocationRelativeTo(null); frame.setVisible(true);
        });
    }
}

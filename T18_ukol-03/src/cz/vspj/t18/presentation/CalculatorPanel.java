package cz.vspj.t18.presentation;

import cz.vspj.t18.logic.FactorialService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Prezentační vrstva: komponenty a reakce na uživatelské události. */
public final class CalculatorPanel extends JPanel {
    private final JTextField input = new JTextField(18);
    private final JTextArea output = new JTextArea(12, 52);
    private final JCheckBox steps = new JCheckBox("Zobrazit postup výpočtu");
    private final JButton calculate = new JButton("Výpočet faktoriál");
    private final JButton clear = new JButton("Smazat text");

    public CalculatorPanel(FactorialService service) {
        super(new BorderLayout(12, 16));
        setBorder(new EmptyBorder(22, 24, 22, 24));
        JLabel title = new JLabel("Kalkulačka faktoriálu");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        JLabel hint = new JLabel("Zadejte celé číslo od 0 do 12. Každý výpočet se přidá do historie.");
        JPanel heading = new JPanel(new GridLayout(0, 1, 0, 8));
        heading.add(title); heading.add(hint);
        JLabel label = new JLabel("Celé číslo:");
        label.setLabelFor(input);
        input.setName("input"); output.setName("output");
        steps.setName("steps"); calculate.setName("calculate"); clear.setName("clear");
        input.getAccessibleContext().setAccessibleName("Celé číslo");
        output.getAccessibleContext().setAccessibleName("Výsledky výpočtů");
        JPanel entry = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        entry.add(label); entry.add(input); entry.add(calculate);
        JPanel controls = new JPanel(new GridLayout(0, 1, 0, 12));
        controls.add(heading); controls.add(entry); controls.add(steps);
        add(controls, BorderLayout.NORTH);
        output.setEditable(false);
        output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        output.setLineWrap(true); output.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createTitledBorder("Výsledky výpočtů"));
        add(scroll, BorderLayout.CENTER);
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        footer.add(clear); add(footer, BorderLayout.SOUTH);
        calculate.addActionListener(event -> {
            output.append(service.calculate(input.getText(), steps.isSelected()).output() + "\n");
            output.setCaretPosition(output.getDocument().getLength());
        });
        input.addActionListener(event -> calculate.doClick());
        clear.addActionListener(event -> { service.clearHistory(); output.setText(""); });
    }
}

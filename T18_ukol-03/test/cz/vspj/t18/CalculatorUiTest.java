package cz.vspj.t18;
import cz.vspj.t18.data.*;
import cz.vspj.t18.logic.*;
import cz.vspj.t18.presentation.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.nio.file.*;
import javax.imageio.ImageIO;
import static org.junit.jupiter.api.Assertions.*;

/** Integrační GUI test: skutečné okno a klikání myší / klávesnice přes Robot. */
class CalculatorUiTest {
    private JFrame frame;
    private CalculatorPanel panel;
    private Robot robot;
    private final CalculationHistory history = new CalculationHistory();
    @BeforeEach void open() throws Exception {
        assertFalse(GraphicsEnvironment.isHeadless(), "GUI testy vyžadují displej; na Linuxu použijte xvfb-run -a mvn test.");
        robot = new Robot(); robot.setAutoDelay(80);
        SwingUtilities.invokeAndWait(() -> {
            frame = new JFrame("Kalkulačka faktoriálu");
            panel = new CalculatorPanel(new FactorialService(history));
            frame.setContentPane(panel); frame.pack(); frame.setLocation(60,60); frame.setVisible(true);
        });
        robot.waitForIdle();
    }
    @AfterEach void close() throws Exception {
        if (frame != null) SwingUtilities.invokeAndWait(() -> frame.dispose());
    }
    private Component find(Container root, String name) {
        for (Component c : root.getComponents()) {
            if (name.equals(c.getName())) return c;
            if (c instanceof Container container) {
                Component nested = find(container,name); if (nested != null) return nested;
            }
        }
        return null;
    }
    private void click(String name) throws Exception {
        Point[] point = new Point[1];
        SwingUtilities.invokeAndWait(() -> {
            Component c = find(panel,name); assertNotNull(c);
            point[0] = c.getLocationOnScreen(); point[0].translate(c.getWidth()/2,c.getHeight()/2);
        });
        robot.mouseMove(point[0].x,point[0].y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.waitForIdle();
    }
    private void input(String text) throws Exception {
        SwingUtilities.invokeAndWait(() -> ((JTextField)find(panel,"input")).setText(text));
    }
    private String output() throws Exception {
        String[] text = new String[1];
        SwingUtilities.invokeAndWait(() -> text[0] = ((JTextArea)find(panel,"output")).getText());
        return text[0];
    }
    private void screenshot(String name) throws Exception {
        if (!Boolean.getBoolean("screenshots")) return;
        Rectangle[] bounds = new Rectangle[1];
        SwingUtilities.invokeAndWait(() -> bounds[0] = frame.getBounds());
        robot.waitForIdle(); Toolkit.getDefaultToolkit().sync();
        Path dir = Path.of("docs/screenshots"); Files.createDirectories(dir);
        ImageIO.write(robot.createScreenCapture(bounds[0]),"png",dir.resolve(name+".png").toFile());
    }
    @Test void completeUserWorkflow() throws Exception {
        assertEquals("", output());
        screenshot("01_start");
        // První hodnotu skutečně zadáme klávesnicí.
        click("input"); robot.keyPress(KeyEvent.VK_5); robot.keyRelease(KeyEvent.VK_5);
        click("calculate"); assertEquals("5! = 120\n",output());
        click("steps"); click("calculate");
        input("0"); click("calculate");
        input("20"); click("calculate");
        assertEquals("5! = 120\n5! = 5 * 4 * 3 * 2 * 1 = 120\n0! = 1\n20! = 20 * 19 * 18 * 17 * 16 * 15 * 14 * 13 * 12 * 11 * 10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1 = 2432902008176640000\n",output());
        screenshot("02_vypocty");
        click("clear"); assertEquals("",output());
        input("-1"); click("calculate"); input("pět"); click("calculate");
        input("21"); click("calculate"); input("99999999999999999999"); click("calculate");
        input(""); click("calculate");
        assertEquals("-1! => Chyba:Nelze spočítat faktoriál záporného čísla.\n" + "=> Chyba:Vstupní údaj není správný.\n".repeat(4),output());
        screenshot("03_chyby");
        click("clear"); assertEquals("",output());
        SwingUtilities.invokeAndWait(() -> assertTrue(history.records().isEmpty()));
        screenshot("04_smazani");
        click("steps"); input("3"); click("input");
        robot.keyPress(KeyEvent.VK_ENTER); robot.keyRelease(KeyEvent.VK_ENTER); robot.waitForIdle();
        assertEquals("3! = 6\n",output());
        SwingUtilities.invokeAndWait(() -> {
            assertFalse(((JTextArea)find(panel,"output")).isEditable());
            assertEquals(1,history.records().size());
        });
    }
}

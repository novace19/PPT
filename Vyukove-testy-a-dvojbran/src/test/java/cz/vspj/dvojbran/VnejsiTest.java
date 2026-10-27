package cz.vspj.dvojbran;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/** Testuje veřejné rozhraní programu v samostatném procesu. */
class VnejsiTest {
    private String spust(int ocekavanyKod, String... vstupy) throws Exception {
        List<String> prikaz = new ArrayList<>(Arrays.asList(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java", "-cp", System.getProperty("java.class.path"), "cz.vspj.dvojbran.Main"));
        Process proces = new ProcessBuilder(prikaz).redirectErrorStream(true).start();
        try (Writer vstup = new OutputStreamWriter(proces.getOutputStream(), Charset.defaultCharset())) {
            for (String hodnota : vstupy) vstup.write(hodnota + "\n");
        }
        ByteArrayOutputStream vystup = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; int n;
        while ((n = proces.getInputStream().read(buffer)) != -1) vystup.write(buffer, 0, n);
        assertEquals(ocekavanyKod, proces.waitFor());
        return new String(vystup.toByteArray(), Charset.defaultCharset());
    }
    @Test void vypisujeMezivysledkyAVysledkyNaDveDesetinnaMista() throws Exception {
        String vystup = spust(0, "10", "1", "2");
        assertTrue(vystup.contains("3.000000"));
        assertTrue(vystup.contains("3.333333"));
        assertTrue(vystup.contains("6.666667"));
        assertTrue(vystup.contains("6.67 V"));
        assertTrue(vystup.contains("3.33 A"));
    }
    @Test void odmitneChybejiciANeplatneHodnoty() throws Exception {
        spust(1);
        spust(1, "abc", "1", "2");
        spust(1, "10", "1", "0");
    }
}

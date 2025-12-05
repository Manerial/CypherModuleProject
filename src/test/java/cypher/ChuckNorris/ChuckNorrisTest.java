package cypher.ChuckNorris;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ChuckNorrisTest {

    private final ChuckNorris chuckNorris = new ChuckNorris();

    @Test
    void testEncrypt() {
        assertEquals("0 0 00 0 0 0000 00 00", chuckNorris.encrypt("C"));
        assertEquals("0 0 00 0 0 0000 00 00 0 0 00 0 0 0000 00 00", chuckNorris.encrypt("CC"));
        assertEquals("", chuckNorris.encrypt(""));
    }

    @Test
    void testDecipher() {
        assertEquals("C", chuckNorris.decipher("0 0 00 0 0 0000 00 00"));
        assertEquals("CC", chuckNorris.decipher("0 0 00 0 0 0000 00 00 0 0 00 0 0 0000 00 00"));
        assertEquals("", chuckNorris.decipher(""));
    }

    @Test
    void testEncryptAndDecipher() {
        String text = "Hello World!";
        String encrypted = chuckNorris.encrypt(text);
        String decrypted = chuckNorris.decipher(encrypted);
        assertEquals(text, decrypted);
    }
}

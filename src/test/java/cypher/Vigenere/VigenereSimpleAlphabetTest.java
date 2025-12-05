package cypher.Vigenere;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class VigenereSimpleAlphabetTest {

    @Test
    void testEncrypt() {
        VigenereSimpleAlphabet vigenere = new VigenereSimpleAlphabet("KEY");
        assertEquals("RIJVS", vigenere.encrypt("HELLO"));
    }

    @Test
    void testEncryptWithDifferentKey() {
        VigenereSimpleAlphabet vigenere = new VigenereSimpleAlphabet("SECRET");
        assertEquals("KIEIIM", vigenere.encrypt("SECRET"));
    }

    @Test
    void testDecipher() {
        VigenereSimpleAlphabet vigenere = new VigenereSimpleAlphabet("KEY");
        assertEquals("HELLO", vigenere.decipher("RIJVS"));
    }

    @Test
    void testDecipherWithDifferentKey() {
        VigenereSimpleAlphabet vigenere = new VigenereSimpleAlphabet("SECRET");
        assertEquals("SECRET", vigenere.decipher("KIEIIM"));
    }

    @Test
    void testEncryptAndDecipher() {
        VigenereSimpleAlphabet vigenere = new VigenereSimpleAlphabet("LONGKEY");
        String plainText = "THISISALONGTEST";
        String encrypted = vigenere.encrypt(plainText);
        String decrypted = vigenere.decipher(encrypted);
        assertEquals(plainText, decrypted);
    }

    @Test
    void testWithSpaces() {
        VigenereSimpleAlphabet vigenere = new VigenereSimpleAlphabet("KEY");
        assertEquals("RIJVS GSPVH", vigenere.encrypt("HELLO WORLD"));
        assertEquals("HELLO WORLD", vigenere.decipher("RIJVS GSPVH"));
    }
}

package cypher.Enigma;

import org.apache.commons.lang3.tuple.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EnigmaTest {

    private Enigma enigma;

    @BeforeEach
    void setUp() {
        enigma = new Enigma();
    }

    @Test
    void testEncrypt() {
        String encrypted = enigma.encrypt("HELLO");
        assertNotEquals("HELLO", encrypted);
    }

    @Test
    void testDecipher() {
        String encrypted = enigma.encrypt("HELLO");
        String decrypted = enigma.decipher(encrypted);
        assertEquals("HELLO", decrypted);
    }

    @Test
    void testEncryptWithSpaces() {
        String encrypted = enigma.encrypt("HELLO WORLD");
        assertNotEquals("HELLO WORLD", encrypted);
        assertEquals(' ', encrypted.charAt(5));
    }

    @Test
    void testDecipherWithSpaces() {
        String encrypted = enigma.encrypt("HELLO WORLD");
        String decrypted = enigma.decipher(encrypted);
        assertEquals("HELLO WORLD", decrypted);
    }

    @Test
    void testSymmetricEncryption() {
        String text = "THEQUICKBROWNFOXJUMPSOVERTHELAZYDOG";
        String encrypted = enigma.encrypt(text);
        String decrypted = enigma.decipher(encrypted);
        assertEquals(text, decrypted);
    }

    @Test
    void testEmptyString() {
        assertEquals("", enigma.encrypt(""));
        assertEquals("", enigma.decipher(""));
    }

    @Test
    void testCustomConfiguration() {
        Pair<Rotor, Integer> rotor1 = Pair.of(Accessories.rotor6, 12);
        Pair<Rotor, Integer> rotor2 = Pair.of(Accessories.rotor4, 6);
        Pair<Rotor, Integer> rotor3 = Pair.of(Accessories.rotor5, 9);
        Pair<Rotor, Integer> mirror = Pair.of(Accessories.mirror2, 0);

        List<Pair<Character, Character>> plugs = new ArrayList<>();
        plugs.add(Pair.of('A', 'B'));
        plugs.add(Pair.of('C', 'D'));

        Enigma customEnigma = new Enigma(List.of(rotor1, rotor2, rotor3), mirror, plugs);

        String text = "CUSTOMTEST";
        String encrypted = customEnigma.encrypt(text);
        String decrypted = customEnigma.decipher(encrypted);

        assertEquals(text, decrypted);
    }
}
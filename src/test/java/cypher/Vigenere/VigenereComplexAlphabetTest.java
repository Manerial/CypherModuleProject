package cypher.Vigenere;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class VigenereComplexAlphabetTest {

    private VigenereComplexAlphabet vigenere;

    @BeforeEach
    void setUp() {
        vigenere = new VigenereComplexAlphabet();
    }

    @Test
    void testEncryptAndDecipher() {
        String key = "KEY";
        vigenere.setKey(key);
        String clearText = "HELLO WORLD";
        String cypherText = vigenere.encrypt(clearText);
        String decodedText = vigenere.decipher(cypherText);
        assertEquals(clearText, decodedText);
    }

    @Test
    void testConstructorWithKey() {
        String key = "TESTKEY";
        VigenereComplexAlphabet vigenereWithKey = new VigenereComplexAlphabet(key);
        vigenereWithKey.setKey(key); // Ensure the key is set for operations
        String clearText = "TEST MESSAGE";
        String cypherText = vigenereWithKey.encrypt(clearText);
        String decodedText = vigenereWithKey.decipher(cypherText);
        assertEquals(clearText, decodedText);
    }

    @Test
    void testEncryptWithDifferentKey() {
        String key1 = "KEYONE";
        String key2 = "KEYTWO";
        vigenere.setKey(key1);
        String clearText = "SECRET";
        String cypherText1 = vigenere.encrypt(clearText);

        vigenere.setKey(key2);
        String cypherText2 = vigenere.encrypt(clearText);

        assertNotEquals(cypherText1, cypherText2);
    }

    @Test
    void testDecipherWithIncorrectKey() {
        String correctKey = "SECRETKEY";
        String incorrectKey = "WRONGKEY";
        vigenere.setKey(correctKey);
        String clearText = "ATTACKATDAWN";
        String cypherText = vigenere.encrypt(clearText);

        vigenere.setKey(incorrectKey);
        String decodedText = vigenere.decipher(cypherText);

        assertNotEquals(clearText, decodedText);
    }

    @Test
    void testEmptyClearText() {
        String key = "KEY";
        vigenere.setKey(key);
        String clearText = "";
        String cypherText = vigenere.encrypt(clearText);
        assertEquals("", cypherText);
        String decodedText = vigenere.decipher(cypherText);
        assertEquals("", decodedText);
    }

    @Test
    void testEmptyKey() {
        String key = "";
        vigenere.setKey(key);
        String clearText = "HELLO";
        String encodedText = vigenere.encrypt(clearText);
        assertEquals(clearText, encodedText);
    }

    @Test
    void testSpecialCharacters() {
        String key = "SPECIAL";
        vigenere.setKey(key);
        String clearText = "Hello, World! 123 @#$ %^&*()";
        String cypherText = vigenere.encrypt(clearText);
        String decodedText = vigenere.decipher(cypherText);
        assertEquals(clearText, decodedText);
    }

    @Test
    void testLongTextAndKey() {
        String key = "LONGERKEYTHANUSUAL";
        vigenere.setKey(key);
        StringBuilder longTextBuilder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longTextBuilder.append("This is a long text part. ");
        }
        String clearText = longTextBuilder.toString();
        String cypherText = vigenere.encrypt(clearText);
        String decodedText = vigenere.decipher(cypherText);
        assertEquals(clearText, decodedText);
    }
}

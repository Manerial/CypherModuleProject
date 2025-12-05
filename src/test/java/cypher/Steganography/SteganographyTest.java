package cypher.Steganography;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SteganographyTest {

    @Test
    void testSteganography() {
        Steganography steganography = new Steganography("normalImage.png", "cryptedImage.png");
        String message = "This is a secret message.";
        steganography.encrypt(message);
        String decryptedMessage = steganography.decipher("");
        assertEquals(message, decryptedMessage);
    }
}

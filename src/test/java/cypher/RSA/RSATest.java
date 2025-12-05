package cypher.RSA;

import org.junit.jupiter.api.*;

import java.math.*;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {
    BigInteger prime1 = new BigInteger("464716427113787");
    BigInteger prime2 = new BigInteger("688724289189847");

    @Test
    void testRSA() {
        RSA rsa = new RSA(prime1, prime2, RSA.probablePrime());
        String message = "Hello, World!";
        String encryptedMessage = rsa.encrypt(message);
        String decryptedMessage = rsa.decipher(encryptedMessage);
        assertEquals(message, decryptedMessage);
    }

    @Test
    void testRSAWithLargeMessage() {
        RSA rsa = new RSA(prime1, prime2, RSA.probablePrime());
        String message = "This is a test with a larger message size, like lorem ipsum dolor sit amet w  a&é' ùq lmfkaùml dfe 231035' 0y(y¨32(KDSF  .";
        String encryptedMessage = rsa.encrypt(message);
        String decryptedMessage = rsa.decipher(encryptedMessage);
        assertEquals(message, decryptedMessage);
    }

    @Test
    void testEncryptionDecryptionWithDifferentMessages() {
        RSA rsa = new RSA(prime1, prime2, RSA.probablePrime());
        String message1 = "First message";
        String message2 = "Second message";

        String encrypted1 = rsa.encrypt(message1);
        String encrypted2 = rsa.encrypt(message2);

        assertNotEquals(encrypted1, encrypted2);

        String decrypted1 = rsa.decipher(encrypted1);
        String decrypted2 = rsa.decipher(encrypted2);

        assertEquals(message1, decrypted1);
        assertEquals(message2, decrypted2);
    }
}

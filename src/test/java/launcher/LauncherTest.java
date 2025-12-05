package launcher;

import cypher.ChuckNorris.ChuckNorris;
import cypher.Enigma.Enigma;
import cypher.RSA.RSA;
import cypher.Steganography.Steganography;
import cypher.Vigenere.VigenereComplexAlphabet;
import cypher.Vigenere.VigenereSimpleAlphabet;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

class LauncherTest {

    @Test
    void testRSA() {
        System.out.println("===RSA===");
        RSA rsa = new RSA(new BigInteger("794138950211"), new BigInteger("846784129943"), RSA.probablePrime());
        rsa.runCypher("Mon test est sur");
        System.out.println();
    }

    @Test
    void testVigenereComplexAlphabet() {
        System.out.println("===VIGNERE (COMPLEX)===");
        VigenereComplexAlphabet vig = new VigenereComplexAlphabet("Ma clé est géniale");
        vig.runCypher("Mon test est sur");
        System.out.println();
    }

    @Test
    void testVigenereSimpleAlphabet() {
        System.out.println("===VIGNERE (SIMPLE)===");
        VigenereSimpleAlphabet vig = new VigenereSimpleAlphabet("MACLEGENIALE");
        vig.runCypher("Mon test est sur");
        System.out.println();
    }

    @Test
    void testEnigma() {
        System.out.println("===ENIGMA===");
        Enigma enigma = new Enigma();
        enigma.runCypher("Mon test est sur");
        System.out.println();
    }

    @Test
    void testSteganography() {
        System.out.println("===STEGANOGRAPHY===");
        Steganography chuck = new Steganography("normalImage.png", "cryptedImage.png");
        chuck.runCypher("Mon test est sur");
        System.out.println();
    }

    @Test
    void testChuck() {
        System.out.println("===CHUCK NORRIS===");
        ChuckNorris chuck = new ChuckNorris();
        chuck.runCypher("Mon test est sur");
        System.out.println();
    }
}

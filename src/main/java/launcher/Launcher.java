package launcher;

import cypher.ChuckNorris.*;
import cypher.Enigma.*;
import cypher.RSA.*;
import cypher.Steganography.*;
import cypher.Vigenere.*;

import java.math.*;

public class Launcher {

    public static void main(String[] args) {
        String clearText = "Mon test est sur";
        
        System.out.println("===RSA===");
        RSA rsa = new RSA(new BigInteger("794138950211"), new BigInteger("846784129943"), RSA.probablePrime());
        rsa.runCypher(clearText);
        System.out.println();

        System.out.println("===VIGNERE (COMPLEX)===");
        VigenereComplexAlphabet vigComplex = new VigenereComplexAlphabet("Ma clé est géniale");
        vigComplex.runCypher(clearText);
        System.out.println();

        System.out.println("===VIGNERE (SIMPLE)===");
        VigenereSimpleAlphabet vigSimple = new VigenereSimpleAlphabet("MACLEGENIALE");
        vigSimple.runCypher(clearText);
        System.out.println();

        System.out.println("===ENIGMA===");
        Enigma enigma = new Enigma();
        enigma.runCypher(clearText);
        System.out.println();

        System.out.println("===STEGANOGRAPHY===");
        Steganography steganography = new Steganography("normalImage.png", "cryptedImage.png");
        steganography.runCypher(clearText);
        System.out.println();

        System.out.println("===CHUCK NORRIS===");
        ChuckNorris chuck = new ChuckNorris();
        chuck.runCypher(clearText);
        System.out.println();
    }
}

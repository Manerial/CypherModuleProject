package cypher.RSA;

import cypher.*;

import java.math.*;
import java.security.*;

/**
 * Uses the RSA cypher method to encrypt/decrypt a message.
 * It uses big prime numbers and modulo to always get a different result, with big calculations.
 * This method is not symmetric.
 *
 * @author JHER
 *
 */
public class RSA extends CypherAbstract {
    private final Encoder encoder;
    private final Decoder decoder;

    /**
     *
     * @param prime1         : The first big prime number that will be use to create the public and the private key
     * @param prime2         : The second big prime number that will be use to create the public and the private key
     * @param cypherExposant : A prime number lesser than (prime1 - 1) * (prime2 - 1)
     */
    public RSA(BigInteger prime1, BigInteger prime2, BigInteger cypherExposant) {
        // cypherExposant is a prime number < phiN

        // phiN = (prime1 - 1) * (prime2 - 1)
        BigInteger phiN = prime1.subtract(BigInteger.ONE).multiply(prime2.subtract(BigInteger.ONE));

        // cypherModule = prime1 * prime2
        BigInteger cypherModule = prime1.multiply(prime2);
        encoder = new Encoder(cypherExposant, cypherModule);

        // uncypherExposant => exposantChiffrement * uncypherExposant % phiN = 1
        BigInteger uncypherExposant = cypherExposant.modInverse(phiN);
        decoder = new Decoder(uncypherExposant, cypherModule);
    }

    @Override
    public String cryptText(String clearText) {
        return encoder.encodeText(clearText);
    }

    @Override
    public String uncryptText(String cypherText) {
        return decoder.decodeMessage(cypherText);
    }

    /**
     * @return a probable prime number
     */
    public static BigInteger probablePrime() {
        return BigInteger.probablePrime(80, new SecureRandom());
    }
}


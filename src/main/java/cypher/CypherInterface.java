package cypher;

interface CypherInterface {

    /**
     * Encrypts a given text with a specific method
     *
     * @param clearText : the text to encrypt
     * @return the text encrypted
     */
    String encrypt(String clearText);

    /**
     * Decrypts a given text with a specific method
     *
     * @param cypherText : the text to decipher
     * @return the text decrypted
     */
    String decipher(String cypherText);
}

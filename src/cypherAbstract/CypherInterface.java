package cypherAbstract;

interface CypherInterface {
	
	/**
	 * Encrypts a given text with a specific method
	 * 
	 * @param clearText : the text to encrypts
	 * @return the text encrypted
	 */
	abstract String cryptText(String clearText);
	
	/**
	 * Decrypts a given text with a specific method
	 * 
	 * @param cypherText : the text to decrypts
	 * @return the text decrypted
	 */
	abstract String uncryptText(String cypherText);
}

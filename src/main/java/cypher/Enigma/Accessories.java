package cypher.Enigma;

public class Accessories {
    public static final Rotor rotor1 = new Rotor("DUFHQOWPANEMSRZXLBTYGIJKVC".toCharArray());
    public static final Rotor rotor2 = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ".toCharArray());
    public static final Rotor rotor3 = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE".toCharArray());
    public static final Rotor rotor4 = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO".toCharArray());
    public static final Rotor rotor5 = new Rotor("ESOVPZJAYQUIRHXLNFTGKDCMWB".toCharArray());
    public static final Rotor rotor6 = new Rotor("VZBRGITYUPSDNHLXAWMJQOFECK".toCharArray());


    // The mirrors are special rotors with a special construction :
    // At the position A (1) is the char H (8) and vice versa
    // Compare with the alphabet :
    // ABCDEFGHIJKLMNOPQRSTUVWXYZ
    // |      |
    // HBPQVJSAZFXLYWTCDUGORENKMI
    public static final Rotor mirror1 = new Rotor("HBPQVJSAZFXLYWTCDUGORENKMI".toCharArray());
    public static final Rotor mirror2 = new Rotor("KRFXOCTIHQAZWPENJBVGYSMDUL".toCharArray());
    public static final Rotor mirror3 = new Rotor("TJLWNZKUYBGCPESMRQOAHXDVIF".toCharArray());
}

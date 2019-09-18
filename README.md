# CypherModuleProject
A little Java module to understand different cypher (cryptography) method

In this project, you will find many different methods to cypher a message.

The code is written a way you can understand it quite easily, but it's not magic.

You'll need to think about it and make some efforts to understand it !

## How to use it ?
First, get an IDE (Eclipse)

1. Download Eclipse (The last version)
2. Install Eclipse
3. Launch Eclipse

Next, clone / download this project

Go to Eclipse and follow the procedure :

File -> Open project from File system -> Directory -> [go to the project] -> Select the project with the checkbox -> Finish

Then, you should have access to the project.

To launch it, you can :
1. click the run button
2. ctrl + f11
3. use the menu : Run -> Run

To change it : open the project from the Eclipse window -> open the src folder -> open the launcher package (folder) -> double click on Launcher.java

## RESOURCES
This project uses one resources file, the image used for Steganography.

This file is stored in the resources folder of the CypherModuleProject project.

You can update it as much as you want (be careful to keep the same name !).

## Architecture

### ChuckNorris
Transform a text in binary and then transform the binary in a sequence of 0.

This sequence consists of several consecutive packets of 0.

In each consecutive packet, the first one is 0 or 00.

If 0, then the second packet will contain 1, else it will contain 0.

Example : 01001110000 -> 0+1+00+111+0000 -> 00 0, 0 0, 00 00, 0 000, 00 0000.

This encoding method is symmetric.

### ENIGMA
Encrypt a message using the ENIGMA method.

A character will undergo many permutations

First, it will travel the plugs that will switch it with an other character (but only if the character is on the permute table)

Then it will travel 3 rotors and be permute with other characters again

After that it will travel a mirror (like a rotor but where the entry and the destination give the same result)

Then it will travel again 3 rotors but in the other way

And then it will be the end.

This encoding method is symmetric (thanks to the mirror)

### RSA
Uses the RSA cypher method to encrypt/decrypt a message.

It uses big prime numbers and modulo to always get a different result, with big calculations.

This method is not symmetric.

### STEGANOGRAPHY
Hide a message in the least significant bits of an image.

A byte contains 8 bits and as the color is mostly contain in the high significant bits.

With this method, we can use the last bits of each byte to hide a message in the image.

CAUTION : Steganography is not cryptography !

### VIGENERE
Encode a text with a key using their position in the alphabet or in a list of characters.

This encoding method is symmetric.
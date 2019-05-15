package cypher_method;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import cypher_abstract.CypherAbstract;

/**
 * Hide a message in the least significant bits of an image.
 * A byte contains 8 bits and as the color is mostly contain in the high significant bits.
 * With this method, we can use the last bits of each byte to hide a message in the image.
 * CAUTION : Steganography is not cryptography !
 * 
 * @author JHER
 *
 */
public class Steganography extends CypherAbstract{
	private static final String RESOURCES_PATH = System.getProperty("user.dir") + "\\resources\\steganography\\";
	private String normalImageName;
	private String cryptedTextImageName;

	public Steganography(String normalImageName, String cryptedTextImageName) {
		this.normalImageName = normalImageName;
		this.cryptedTextImageName = cryptedTextImageName;
	}

	/**
	 * Encrypt an image with message
	 *
	 * @param clearText : The message to hide in the image
	 */
	@Override
	public String cryptText(String clearText) {
		String imageSourceName = RESOURCES_PATH + normalImageName;
		BufferedImage bufferedImageSource = readImage(imageSourceName);

		BufferedImage imageToCrypt = copyImage(bufferedImageSource);
		imageToCrypt = encodeImage(imageToCrypt, clearText);
		try {
			saveImage(imageToCrypt, new File(RESOURCES_PATH + cryptedTextImageName), "png");
		} catch(Exception e) {
			return "Fail saving image";
		}
		return RESOURCES_PATH + cryptedTextImageName;
	}

	/**
	 * Extracts the hidden message from an image
	 *
	 * @param notUsed : Unused
	 * @return the message hidden in the image
	 */
	@Override
	@Deprecated
	public String uncryptText(String notUsed) {
		return uncryptText();
	}
	
	/**
	 * Extracts the hidden message from an image
	 *
	 * @return the message hidden in the image
	 */
	public String uncryptText() {
		BufferedImage image = copyImage(readImage(RESOURCES_PATH + cryptedTextImageName));
		byte[] decode = decodeImage(getBytesImage(image));
		return(new String(decode));
	}

	/**
	 * Read method to return an image file
	 * 
	 * @param imagePath : The complete path name of the image.
	 * @return a BufferedImage of the supplied file path
	 */
	private BufferedImage readImage(String imagePath) {
		BufferedImage image = null;
		File file = new File(imagePath);
		try {
			image = ImageIO.read(file);
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Image could not be read!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}

	/**
	 * Creates a new image for editing and saving bytes
	 * 
	 * @param imageSource : The image to copy
	 * @return a copy of the supplied image
	 */
	private BufferedImage copyImage(BufferedImage imageSource) {
		BufferedImage imageCopy = new BufferedImage(imageSource.getWidth(), imageSource.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D	graphics = imageCopy.createGraphics();
		graphics.drawRenderedImage(imageSource, null);
		graphics.dispose(); //release all allocated memory for this image
		return imageCopy;
	}

	/**
	 * Add text to an image
	 *
	 * @param image : The image to add hidden text to
	 * @param text : The text to hide in the image
	 * @return the image with the text hidden in it
	 */
	private BufferedImage encodeImage(BufferedImage image, String text) {
		//convert all items to byte arrays: image, message, message length
		byte imageBytes[] = getBytesImage(image);
		byte textBytes[] = text.getBytes();
		byte textLength[] = integerToByteArray(textBytes.length);
		
		// Register the message length
		// 0 = first position
		encodeData(imageBytes, textLength,  0);
		
		// Register the message itself
		// 4 bytes of space for the length: 4bytes*8bit = 32 bits
		encodeData(imageBytes, textBytes, 32);

		return image;
	}

	/**
	 * Retrieves hidden text from an image
	 * 
	 * @param image : Array of bytes representing an image
	 * @return an array of data which contains the hidden text
	 */
	private byte[] decodeImage(byte[] image) {
		int length = 0;
		int offset  = 32;
		//loop through 32 bytes of data to determine the text length
		for(int i = 0; i < 32; ++i) { //i=24 will also work, as only the 4th byte contains real data
			length = (length << 1) | (image[i] & 1);
		}

		byte[] result = new byte[length];

		//loop through each byte of text
		for(int b=0; b<result.length; ++b ) {
			//loop through each bit within a byte of text
			for(int i=0; i<8; ++i, ++offset) {
				//assign bit: [(new byte value) << 1] OR [(text byte) AND 1]
				result[b] = (byte) ((result[b] << 1) | (image[offset] & 1));
			}
		}
		return result;
	}

	/**
	 * Gets the byte array of an image
	 *
	 * @param image : The image to get bytes from
	 * @return the byte array of the image supplied
	 */
	private byte[] getBytesImage(BufferedImage image) {
		WritableRaster raster = image.getRaster();
		DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
		return buffer.getData();
	}

	/**
	 * Generates proper byte format of an integer
	 * 
	 * @param i : The integer to convert
	 * @return a byte[4] array converting the supplied integer into bytes
	 */
	private byte[] integerToByteArray(int i) {
		byte byte3 = (byte) ((i & 0xFF000000) >>> 24);
	    byte byte2 = (byte) ((i & 0x00FF0000) >>> 16);
	    byte byte1 = (byte) ((i & 0x0000FF00) >>> 8);
	    byte byte0 = (byte) ((i & 0x000000FF));
		return(new byte[]{byte3, byte2, byte1, byte0});
	}

	/**
	 * Encode an array of bytes into another array of bytes at a supplied offset
	 * 
	 * @param image : Array of data representing an image
	 * @param dataToAddList : Array of data to add to the supplied image data array
	 * @param offset : The offset into the image array to add the addition data
	 * @return data array of merged image and addition data
	 */
	private byte[] encodeData(byte[] image, byte[] dataToAddList, int offset) {
		// check that the data + offset will fit in the image
		if((dataToAddList.length * 8) + offset > image.length) {
			throw new IllegalArgumentException("File not long enough!");
		}

		for(int i = 0; i < dataToAddList.length; ++i) {
			int dataToAdd = dataToAddList[i];
			for(int bit = 7; bit >= 0; --bit, ++offset) {
				// assign an integer to b, shifted by bit spaces AND 1
				// get a single bit of the current byte
				int b = (dataToAdd >>> bit) & 1;
				
				// assign the bit by taking: [(previous byte value) AND 0xFE] OR bit to add
				// changes the last bit of the byte in the image to be the bit of addition
				image[offset] = (byte)((image[offset] & 0xFE) | b);
			}
		}
		return image;
	}

	/**
	 * Save an image file
	 *
	 * @param image : The image file to save
	 * @param file : File  to save the image to
	 * @param ext : The extension and thus format of the file to be saved
	 * @return true if the save is successful
	 * @throws IOException when the image writing fails
	 */
	private boolean saveImage(BufferedImage image, File file, String ext) throws IOException {
		 //delete resources if already exists
		file.delete();
		ImageIO.write(image, ext, file);
		return true;
	}
}

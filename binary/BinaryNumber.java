package binary;

/**
 * Binary Number
 * 
 * @author Robert Schaedler III I pledge my honor that I have abided by the
 *         Stevens Honor System. CS 284A
 */

public class BinaryNumber {

    // Instance variables
    private int[] data;
    private int length;

    // Contructors
    /**
     * Creates a binary number of length <code>length</code> and consisting only of
     * zeros.
     * 
     * @param length the length of the binary number.
     */
    public BinaryNumber(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Invlaid Length: length must be greater than 0.");
        }
        int[] bn = new int[length];
        data = bn;
        this.length = length;
    }

    /**
     * Creates a binary number given a string. If invalid bits are given, they are
     * set to 0.
     * 
     * @param str the string representation of the binary number to create.
     */
    public BinaryNumber(String str) {
        int len = str.length();
        int[] bn = new int[len];

        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            int bit = Character.getNumericValue(c);
            if (bit == 1) {
                bn[i] = bit;
            } else if (bit == 0) {
                // Do nothing
            } else {
                throw new IllegalArgumentException("Invalid bit given '" + c + "'.");
            }
        }
        this.data = bn;
        this.length = len;
    }

    // Methods

    /**
     * Gets the length of the BinaryNumber.
     * 
     * @return integer return length
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Gets the integer array representing the BinaryNumber.
     * 
     * @return data
     */
    public int[] getInnerArray() {
        return this.data;
    }

    /**
     * Gets the bit at a given index.
     * 
     * @param index the bit to get
     * @return bit at index
     */
    public int getDigit(int index) {
        try {
            return data[index];
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new IllegalArgumentException("index is out of bounds");
        }

    }

    /**
     * Returns the decimal representation of a BinaryNumber.
     * 
     * @return decimal of binary number
     */
    public int toDecimal() {
        int decimal = 0;
        double power = length - 1;
        for (int i = 0; i < length; i++) {
            decimal += data[i] * (int) Math.pow(2.0, power);
            power--;
        }
        return decimal;
    }

    public String toString() {
        String result = new String();
        for (int i = 0; i < length; i++) {
            result += Integer.toString(data[i]);
        }
        return result;
    }

    /**
     * Shifts all digits in a binary number any number of places to the left or
     * right. The direction parameter indicates a left shift when the value is -1.
     * When direction is given the value 1, the shift should be to the right. Any
     * other value for direction should be seen as invalid. The amount parameter
     * specifies how many digits the BinaryNumber will be shifted, and is only valid
     * when it is nonnegative.
     * 
     * @param direction - direction to shift
     * @param amount    - number of places to shift
     */
    public void bitShift(int direction, int amount) {
        if (amount >= 0) {
            int[] newData;
            switch (direction) {
            case -1:
                // Shift Left
                newData = new int[amount + this.getLength()];
                for (int i = 0; i < this.getLength(); i++) {
                    newData[i] = this.getDigit(i);
                }
                this.data = newData;
                this.length = newData.length;
                break;
            case 1:
                // Shift Right
                if (amount < this.length) {
                    newData = new int[this.getLength() - amount];
                    System.arraycopy(data, 0, newData, 0, newData.length);
                    this.length = newData.length;
                    this.data = newData;
                } else {
                    this.length = 1;
                    this.data = new int[1];
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: direction must be 1 or -1.");
            }
        } else {
            throw new IllegalArgumentException("Invalid amount: amount cannot be less than 0.");
        }

    }

    /**
     * Adds a BinaryNumber to the current BinaryNumber.
     * 
     * @param aBinaryNumber to add
     */
    public void add(BinaryNumber aBinaryNumber) {

        // Copy aBinaryNumber to prevent cleanup of prepended bits
        aBinaryNumber = new BinaryNumber(aBinaryNumber.toString());
        int difference = this.getLength() - aBinaryNumber.getLength();

        if (difference < 0) {
            this.prepend(-1 * difference);
        } else if (difference > 0) {
            aBinaryNumber.prepend(difference);
        }

        // Add the binary numbers, keep track of carried bit
        int[] sum = new int[this.getLength()];
        int carriedBit = 0;
        for (int i = this.getLength() - 1; i >= 0; i--) {

            int bit = this.getDigit(i);
            int aBit = aBinaryNumber.getDigit(i);

            // Add the bits, create sum, carry bit
            switch (bit + aBit + carriedBit) {
            case 1:
                sum[i] = 1;
                carriedBit = 0;
                continue;
            case 2:
                sum[i] = 0;
                carriedBit = 1;
                continue;
            case 3:
                sum[i] = 1;
                carriedBit = 1;
                continue;
            default:
                continue;
            }
        }

        this.data = sum;

        // Add most significant carried bit
        if (carriedBit == 1) {
            this.prepend(1);
            this.data[0] = 1;
        }

    }

    /**
     * Prepends a given number of 0's to the beginning of a binary number.
     * 
     * @param amount of 0's to add
     */
    private void prepend(int amount) {
        int[] newData = new int[amount + this.getLength()];
        for (int i = amount; i < newData.length; i++) {
            newData[i] = this.getDigit(i - amount);
        }
        data = newData;
        length = newData.length;
    }

    /**
     * Computes the bitwise 'or' of the two numbers.
     * 
     * @param bn1
     * @param bn2
     * @return integer array representing the bitwise 'or' of two binary numbers.
     */
    public static int[] bwor(BinaryNumber bn1, BinaryNumber bn2) {
        // validate input
        if (bn1.getLength() == bn2.getLength()) {
            // "or" the binary numbers
            int[] bitwiseOr = new int[bn1.getLength()];
            for (int i = 0; i < bn1.getLength(); i++) {
                if (bn1.getDigit(i) == 1 || bn2.getDigit(i) == 1) {
                    bitwiseOr[i] = 1;
                }
            }
            return bitwiseOr;
        } else {
            throw new IllegalArgumentException("Unequal length: binary numbers are not equal in length.");
        }
    }

    /**
     * computes the bitwise 'and' of the two numbers.
     * 
     * @param bn1
     * @param bn2
     * @return integer array representing the bitwise 'and' of two binary numbers.
     */
    public static int[] bwand(BinaryNumber bn1, BinaryNumber bn2) {
        // validate input
        if (bn1.getLength() == bn2.getLength()) {
            // "and" the binary numbers
            int[] bitwiseAnd = new int[bn1.getLength()];
            for (int i = 0; i < bn1.getLength(); i++) {
                if (bn1.getDigit(i) == 1 && bn2.getDigit(i) == 1) {
                    bitwiseAnd[i] = 1;
                }
            }
            return bitwiseAnd;
        } else {
            throw new IllegalArgumentException("Binary numbers are not equal in length.");
        }
    }
}

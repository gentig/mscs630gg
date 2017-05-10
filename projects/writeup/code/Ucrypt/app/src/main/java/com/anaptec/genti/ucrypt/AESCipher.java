package com.anaptec.genti.ucrypt;

/**
 * file: AESCipher.java
 * author: Gentjan Gjeci
 * course: MSCS630
 * assignment: lab4
 * due date: 04-05-2017
 * version: 1.0
 * <p>
 * This file contains the declaration of the
 * roundKeysHex to construct and display all round keys.
 */

import java.util.regex.Pattern;

/**
 * AESCipher
 *
 * This class implements the functions to calculate the round keys for AES
 */
public class AESCipher {
    //Fields
    private static final int KEY_MATRIX = 4;
    private int[][] allKeys;
    private String msgHex;
    private static int[][] sBox = {
            {0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76},
            {0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0},
            {0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15},
            {0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75},
            {0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84},
            {0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF},
            {0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8},
            {0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2},
            {0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73},
            {0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB},
            {0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79},
            {0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08},
            {0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A},
            {0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E},
            {0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF},
            {0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16}
    };

    private static int rcon[] = {
            0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
            0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
            0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
            0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
            0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
            0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
            0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b,
            0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
            0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
            0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
            0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
            0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
            0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
            0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
            0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd,
            0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d
    };

    private int [][] multByTwo = {
            {0x00,0x02,0x04,0x06,0x08,0x0a,0x0c,0x0e,0x10,0x12,0x14,0x16,0x18,0x1a,0x1c,0x1e},
            {0x20,0x22,0x24,0x26,0x28,0x2a,0x2c,0x2e,0x30,0x32,0x34,0x36,0x38,0x3a,0x3c,0x3e},
            {0x40,0x42,0x44,0x46,0x48,0x4a,0x4c,0x4e,0x50,0x52,0x54,0x56,0x58,0x5a,0x5c,0x5e},
            {0x60,0x62,0x64,0x66,0x68,0x6a,0x6c,0x6e,0x70,0x72,0x74,0x76,0x78,0x7a,0x7c,0x7e},
            {0x80,0x82,0x84,0x86,0x88,0x8a,0x8c,0x8e,0x90,0x92,0x94,0x96,0x98,0x9a,0x9c,0x9e},
            {0xa0,0xa2,0xa4,0xa6,0xa8,0xaa,0xac,0xae,0xb0,0xb2,0xb4,0xb6,0xb8,0xba,0xbc,0xbe},
            {0xc0,0xc2,0xc4,0xc6,0xc8,0xca,0xcc,0xce,0xd0,0xd2,0xd4,0xd6,0xd8,0xda,0xdc,0xde},
            {0xe0,0xe2,0xe4,0xe6,0xe8,0xea,0xec,0xee,0xf0,0xf2,0xf4,0xf6,0xf8,0xfa,0xfc,0xfe},
            {0x1b,0x19,0x1f,0x1d,0x13,0x11,0x17,0x15,0x0b,0x09,0x0f,0x0d,0x03,0x01,0x07,0x05},
            {0x3b,0x39,0x3f,0x3d,0x33,0x31,0x37,0x35,0x2b,0x29,0x2f,0x2d,0x23,0x21,0x27,0x25},
            {0x5b,0x59,0x5f,0x5d,0x53,0x51,0x57,0x55,0x4b,0x49,0x4f,0x4d,0x43,0x41,0x47,0x45},
            {0x7b,0x79,0x7f,0x7d,0x73,0x71,0x77,0x75,0x6b,0x69,0x6f,0x6d,0x63,0x61,0x67,0x65},
            {0x9b,0x99,0x9f,0x9d,0x93,0x91,0x97,0x95,0x8b,0x89,0x8f,0x8d,0x83,0x81,0x87,0x85},
            {0xbb,0xb9,0xbf,0xbd,0xb3,0xb1,0xb7,0xb5,0xab,0xa9,0xaf,0xad,0xa3,0xa1,0xa7,0xa5},
            {0xdb,0xd9,0xdf,0xdd,0xd3,0xd1,0xd7,0xd5,0xcb,0xc9,0xcf,0xcd,0xc3,0xc1,0xc7,0xc5},
            {0xfb,0xf9,0xff,0xfd,0xf3,0xf1,0xf7,0xf5,0xeb,0xe9,0xef,0xed,0xe3,0xe1,0xe7,0xe5}
    };

    private int[][] multByThree = {
            {0x00,0x03,0x06,0x05,0x0c,0x0f,0x0a,0x09,0x18,0x1b,0x1e,0x1d,0x14,0x17,0x12,0x11},
            {0x30,0x33,0x36,0x35,0x3c,0x3f,0x3a,0x39,0x28,0x2b,0x2e,0x2d,0x24,0x27,0x22,0x21},
            {0x60,0x63,0x66,0x65,0x6c,0x6f,0x6a,0x69,0x78,0x7b,0x7e,0x7d,0x74,0x77,0x72,0x71},
            {0x50,0x53,0x56,0x55,0x5c,0x5f,0x5a,0x59,0x48,0x4b,0x4e,0x4d,0x44,0x47,0x42,0x41},
            {0xc0,0xc3,0xc6,0xc5,0xcc,0xcf,0xca,0xc9,0xd8,0xdb,0xde,0xdd,0xd4,0xd7,0xd2,0xd1},
            {0xf0,0xf3,0xf6,0xf5,0xfc,0xff,0xfa,0xf9,0xe8,0xeb,0xee,0xed,0xe4,0xe7,0xe2,0xe1},
            {0xa0,0xa3,0xa6,0xa5,0xac,0xaf,0xaa,0xa9,0xb8,0xbb,0xbe,0xbd,0xb4,0xb7,0xb2,0xb1},
            {0x90,0x93,0x96,0x95,0x9c,0x9f,0x9a,0x99,0x88,0x8b,0x8e,0x8d,0x84,0x87,0x82,0x81},
            {0x9b,0x98,0x9d,0x9e,0x97,0x94,0x91,0x92,0x83,0x80,0x85,0x86,0x8f,0x8c,0x89,0x8a},
            {0xab,0xa8,0xad,0xae,0xa7,0xa4,0xa1,0xa2,0xb3,0xb0,0xb5,0xb6,0xbf,0xbc,0xb9,0xba},
            {0xfb,0xf8,0xfd,0xfe,0xf7,0xf4,0xf1,0xf2,0xe3,0xe0,0xe5,0xe6,0xef,0xec,0xe9,0xea},
            {0xcb,0xc8,0xcd,0xce,0xc7,0xc4,0xc1,0xc2,0xd3,0xd0,0xd5,0xd6,0xdf,0xdc,0xd9,0xda},
            {0x5b,0x58,0x5d,0x5e,0x57,0x54,0x51,0x52,0x43,0x40,0x45,0x46,0x4f,0x4c,0x49,0x4a},
            {0x6b,0x68,0x6d,0x6e,0x67,0x64,0x61,0x62,0x73,0x70,0x75,0x76,0x7f,0x7c,0x79,0x7a},
            {0x3b,0x38,0x3d,0x3e,0x37,0x34,0x31,0x32,0x23,0x20,0x25,0x26,0x2f,0x2c,0x29,0x2a},
            {0x0b,0x08,0x0d,0x0e,0x07,0x04,0x01,0x02,0x13,0x10,0x15,0x16,0x1f,0x1c,0x19,0x1a}
    };

    //Functions

    /**
     * init
     *
     * This function initializes generation of round keys
     */
    public void init(String userInputKey,String sHex){
        int stringLength = userInputKey.trim().length();
        int sHexLength = sHex.trim().length();
        if(sHexLength != 32){
            System.out.println("User plaintext length is not 16 bytes!");
            return;
        }
        if(stringLength != 32){
            System.out.println("User key length is not 16 bytes!");
            return;
        }
        String [] roundKeysHex = aesRoundKeys(userInputKey);
        for (int g = 0; g < roundKeysHex.length; g++){
            //Log.d("GG", roundKeysHex[g]);
        }
        int[][] stepTwoNibbleSub;
        int[][] shiftRows;
        String sFin;
        String sFinShift;
        //We have round keys, xor plaintext with key, Step 1, Add key
        int[][] stepOneXOR = AESStateXOR(sHex, roundKeysHex[0]);
        for(int i = 1; i < 11; i++){
            if(i == 10){
                stepTwoNibbleSub = AESNibbleSub(stepOneXOR);
                sFinShift = shiftRowsFin(stepTwoNibbleSub);
                stepOneXOR = AESStateXOR(sFinShift, roundKeysHex[i]);
                this.msgHex = matrixToString2(stepOneXOR);
            }else{
                //Step two, nibble sub
                stepTwoNibbleSub = AESNibbleSub(stepOneXOR);
                //Step three, shift rows
                shiftRows = shiftRows(stepTwoNibbleSub);
                //Step four, mix columns
                sFin = AESMixColumn(shiftRows);
                //Add key
                String shexRound = roundKeysHex[i];
                stepOneXOR = AESStateXOR(sFin, shexRound);
            }
        }
    /*--------------------Original Lab4------------------*/
        //for(String str: roundKeysHex){
        //  System.out.println(str);
        //}
    /*-------------------Original Lab4-------------------*/
    }

    public String getMessage(){
        return this.msgHex;
    }

    /**
     * roundKeysHex
     *
     * This function creates 11 round keys, one for every element in the array
     *
     * Parameters:
     *   KeyHex: String input, the key input from user
     *
     * Return: String array
     */
    private String[] aesRoundKeys(String KeyHex) {
        int[][] w = new int[4][44];
        int[][] kMatrix = kMatrix(KeyHex);//initial key matrix
        String [] keys;
        //Copy the original key to w
        copyTempMatrixToOriginal(w,kMatrix);//First round
        //We need 10 round keys, the first was done above
        for (int j = 4; j < 44; j++){
            //Do calculation if j NOT a multiple of 4
            if (j % 4 != 0){
                //w(j) = w(j-4) xor w(j-1)
                int [] jMinusOne = matrixColumn(w,j-1);
                int [] jMinusFour = matrixColumn(w,j-4);
                for (int i = 0; i < 4; i++){
                    w[i][j] = jMinusFour[i]^jMinusOne[i];//XOR
                }
            }else{
                //Do calculations if j is a multiple of 4
                //get previous column
                int[] vector = matrixColumn(w, j-1);
                //Perform shift
                int firstElement = vector[0];
                System.arraycopy(vector, 1, vector, 0, vector.length - 1);
                vector[vector.length -1] = firstElement;
                subBytes(vector);//Nibble Substitution
                subByteOne(vector, (int)Math.floor(j/4));// int round = (int)Math.floor(j/4) gives us the round 1,2,..,11
                int [] jMinusFour = matrixColumn(w,j-4);
                for (int i = 0; i < 4; i++ ){
                    w[i][j] = jMinusFour[i]^vector[i];
                }
            }
        }
        allKeys = w;//all keys are in the w matrix
        keys = matrixToString(allKeys);
        return keys;
    }

    /**
     * aesSBox
     *
     * This function reads value from sBox
     *
     * Parameters:
     *   inHex: int value index
     *
     * Return: int
     */
    private int aesSBox(int inHex) {
        return sBox[inHex/16][inHex%16];
    }
    /**
     * aesBoxMBT
     *
     * This function reads value from multiplyByTwo
     *
     * Parameters:
     *   inHex: int value index
     *
     * Return: int
     */
    private int aesBoxMBT(int inHex) {
        return multByTwo[inHex/16][inHex%16];
    }
    /**
     * aesBoxMBThree
     *
     * This function reads value from aesBoxMBThree
     *
     * Parameters:
     *   inHex: int value index
     *
     * Return: int
     */
    private int aesBoxMBThree(int inHex) {
        return multByThree[inHex/16][inHex%16];
    }

    /**
     * subBytes
     *
     * Replaces all elements in the passed array with values in sBox[][].
     *
     * Parameters:
     *    vector Array whose value will be replaced
     * Return: vector Array with substituted data
     */
    private void subBytes(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            int hex = vector[i];
            vector[i] = aesSBox(hex);
        }
    }

    /**
     * subByteOne
     *
     * Replaces the first elements in the passed vector array.
     *
     * Parameters:
     *    vector: Array whose value will be replaced
     *    round: int round for key generation
     */
    private void subByteOne(int[] vector, int round){
        vector[0] = vector[0]^aesRcon(round);
    }

    /**
     * aesRcon
     *
     * This function reads value from rcom array
     *
     * Parameters:
     *   round: int value index
     * Return: int
     */
    private int aesRcon(int round) {
        return rcon[round];
    }

    /**
     * kMatrix
     *
     * This function reads value from the user
     * and builds the key matrix
     *
     * Parameters:
     *   userString: String input
     * Return: 4x4 matrix key
     */
    private int[][] kMatrix(String userString){
        int [][] kMatrix = new int[KEY_MATRIX][KEY_MATRIX];
        //String[] userStringArr =  userString.split("(?<=\\G.{2})");
        String[] userStringArr =  splitStringEvery(userString,2);
        int counter = 0;
        //check if userStringArr length is 16 (128 bits)
        //We have the correct length, build matrix
        for(int i = 0; i < KEY_MATRIX; i++){
            for(int j = 0; j < KEY_MATRIX; j++){
                kMatrix[j][i] = Integer.decode("0x"+userStringArr[counter].trim());
                //counter goes up to 16 but it is ok because the increment happens after it is used for array index
                counter++;
            }
        }
        return kMatrix;
    }

    public String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }

    /**
     * copyTempMatrixToOriginal
     *
     * This function copies the original 4x4 matrix to the bigger w matrix 4x44
     *
     * Parameters:
     *   wOriginal: int original matrix
     *   wTemp: int temp matrix
     *   offset: int offset
     */
    private void copyTempMatrixToOriginal(int[][] wOriginal, int[][] wTemp){
        for (int i = 0; i < KEY_MATRIX; i++) {
            for (int j = 0; j < KEY_MATRIX; j++) {
                wOriginal[i][j] = wTemp[i][j];
            }
        }
    }

    /**
     * matrixColumn
     *
     * This function gets a specific column from a matrix at index
     *
     * Parameters:
     *   kMatrix: int matrix
     *   index: int index
     * Return: array column at index
     */
    private int[] matrixColumn(int[][] kMatrix, int index){
        int[] column = new int[4];
        for (int j = 0; j < KEY_MATRIX; j++) {
            column[j] = kMatrix[j][index];
        }
        return column;
    }

    /**
     * matrixColumn
     *
     * This function gets a specific column from a matrix at index
     *
     * Parameters:
     *   kMatrix: int matrix
     *   index: int index
     * Return: array column at index
     */
    private int[] matrixRow(int[][] kMatrix, int index){
        int[] column = new int[4];
        for (int j = 0; j < KEY_MATRIX; j++) {
            column[j] = kMatrix[index][j];
        }
        return column;
    }
    /**
     * matrixToString
     *
     * This function loops over the matrix and
     * saves the round keys.
     *
     * Parameters:
     *   n: the number on which to compute the factorial
     *
     * Return value: the factorial of n, or 1 if n <= 0.
     */
    private String[] matrixToString(int[][] aMtr){
        String strApend, strFinal;
        String[] sA;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 44; i++) {
            for (int j = 0; j < 4; j++) {
                //StringBuilder.
                strApend = String.format("%02x",aMtr[j][i]);
                sb.append(strApend);
            }
        }
        strFinal = sb.toString();
        strFinal = strFinal.toUpperCase();//Make all uper case hex
        //sA =  strFinal.split("(?<=\\G.{32})");
        sA = splitStringEvery(strFinal,32);
        return sA;
    }

    /**
     * matrixToString
     *
     * This function loops over the matrix and
     * saves the round keys.
     *
     * Parameters:
     *   n: the number on which to compute the factorial
     *
     * Return value: the factorial of n, or 1 if n <= 0.
     */
    private String matrixToString2(int[][] aMtr){
        String strApend, strFinal;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //StringBuilder.
                strApend = String.format("%02x",aMtr[j][i]);
                sb.append(strApend);
            }
        }
        strFinal = sb.toString();
        strFinal = strFinal.toUpperCase();
        return strFinal;
    }

    /**
     * Printing a 4x4 matrix. Final answer
     *
     * Parameters:
     *   matrix: int matrix
     */
    private void printFourByFourMatrixRow(int[][] matrix){
        //print matrix here
        for(int m = 0; m < 4; m++){
            for(int n = 0; n < 4; n++){
                System.out.print(String.format("%02x",matrix[n][m]).toUpperCase());
            }
        }
        System.out.println();
    }
  /*======Step 1======*/
    /**
     * AESStateXOR
     *
     * This function will XOR two two dimensional matrices
     *
     * Parameters:
     *   sHex: plain text and key
     *   sKeyHex: key to XOR plain text
     *
     * Return value: matrix.
     */
    private int[][] AESStateXOR(String sHex, String sKeyHex){
        int[][] sHexMatrix = kMatrix(sHex);
        int[][] sKeyHexMatrix = kMatrix(sKeyHex);
        int[][] xOr = new int[4][4];
        //XOR two two dimensional arrays
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                xOr[i][j] = sHexMatrix[i][j]^sKeyHexMatrix[i][j];
            }
        }
        return xOr;
    }
  /*======Step 2======*/
    /**
     * AESNibbleSub
     *
     * This function will do a Nibble Substitution
     *
     * Parameters:
     *   inStateHex: int matrix
     *
     * Return value: matrix
     */
    private int[][] AESNibbleSub(int [][] inStateHex){
        //For each element in the matrix do a sBox/Nibble substitution
        for(int i = 0; i < inStateHex.length; i++){
            for(int j = 0; j < inStateHex.length; j++){
                inStateHex[i][j] = nibbleVal(inStateHex[i][j]);
            }
        }
        return inStateHex;
    }
    /**
     * nibbleVal
     *
     * This function will do a Nibble Substitution
     *
     * Parameters:
     *   inHex: int value
     *
     * Return value: int
     */
    private int nibbleVal(int inHex){
        int nibbleSub;
        nibbleSub = aesSBox(inHex);
        return nibbleSub;
    }
  /*======Step 3======*/
    /**
     * shiftRows
     *
     * This function will shift rows in a matrix
     *
     * Parameters:
     *   matrix: 4x4 matrix
     *
     * Return value: matrix
     */
    private int [][] shiftRows(int[][] matrix){
        for (int i = 0; i < KEY_MATRIX; i++) {
            if(i == 0){
                continue;
            }
            int[] temp = matrixRow(matrix, i);
            int[] shifted = shiftNTimes(temp, i);
            //shift offset is i
            for (int j = 0; j < KEY_MATRIX; j++) {
                matrix[i][j] = shifted[j];
            }
        }
        return matrix;
    }

    /**
     * shiftRowsFin
     *
     * This function will shift rows in a matrix
     *
     * Parameters:
     *   matrix: 4x4 matrix
     *
     * Return value: String
     */
    private String shiftRowsFin(int[][] matrix){
        for (int i = 0; i < KEY_MATRIX; i++) {
            if(i == 0){
                continue;
            }
            int[] temp = matrixRow(matrix, i);
            int[] shifted = shiftNTimes(temp, i);
            //shift offset is i
            for (int j = 0; j < KEY_MATRIX; j++) {
                matrix[i][j] = shifted[j];
            }
        }
        return matrixToString2(matrix);
    }

    /**
     * shiftNTimes
     *
     * This function will shift rows multiple times
     * to get the needed result, shifting by 1, 2 etc
     *
     * Parameters:
     *   array: array
     *   numShifts: int
     *
     * Return value: matrix
     */
    private int[] shiftNTimes(int[] array, int numShifts) {
        int timesShifted = 0;
        while (timesShifted < numShifts) {
            int temp = array[0];
            for (int i = 0; i < array.length - 1; i++) {
                array[i] = array[i + 1];
            }
            array[array.length - 1] = temp;
            timesShifted++;
        }
        return array;
    }

  /*======Step 4======*/
    /**
     * AESMixColumn
     *
     * This function will shift rows multiple times
     * to get the needed result, shifting by 1, 2 etc
     *
     * Parameters:
     *   array: array
     *   numShifts: int
     *
     * Return value: matrix
     */
    private String AESMixColumn(int[][] matrix){
        int[] column;
        int[] columnMixed = new int[4];
        for (int i = 0; i < matrix.length; i++){
            column =  matrixColumn(matrix,i);
            columnMixed[0] = aesBoxMBT(column[0])^aesBoxMBThree(column[1])^column[2]^column[3];
            columnMixed[1] = column[0]^aesBoxMBT(column[1])^aesBoxMBThree(column[2])^column[3];
            columnMixed[2] = column[0]^column[1]^aesBoxMBT(column[2])^aesBoxMBThree(column[3]);
            columnMixed[3] = aesBoxMBThree(column[0])^column[1]^column[2]^aesBoxMBT(column[3]);
            for (int j = 0; j < matrix.length; j++) {
                matrix[j][i] = columnMixed[j];
            }
        }
        return matrixToString2(matrix);
    }
}

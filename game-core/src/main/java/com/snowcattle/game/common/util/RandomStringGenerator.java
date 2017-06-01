package com.snowcattle.game.common.util;

/**
 * Created by jiangwenping on 17/2/6.
 */
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomStringGenerator
{
    public static final int DEFAULT_LENGTH = 8;

    static char[] alphaNumberic = new char[] { 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', '0' };

    public  String generateRandomString(int length)
    {
        String random = "ACK";

        int len = DEFAULT_LENGTH;
        if (length > 0)
        {
            len = length;
        }

        char[] randomChars = new char[len];
        try
        {
            SecureRandom wheel = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < len; i++)
            {
                int nextChar = wheel.nextInt(alphaNumberic.length);
                randomChars[i] = alphaNumberic[nextChar];
            }
            random = new String(randomChars);
        }
        catch (NoSuchAlgorithmException e)
        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
        }

        return random;
    }
}


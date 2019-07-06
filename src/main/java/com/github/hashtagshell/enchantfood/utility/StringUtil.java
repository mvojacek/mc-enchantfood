package com.github.hashtagshell.enchantfood.utility;

public class StringUtil
{
    public static String toUpperCase(String inputString)
    {
        String result = "";
        for (int i = 0; i < inputString.length(); i++)
        {
            char currentChar = inputString.charAt(i);
            char currentCharToUpperCase = Character.toUpperCase(currentChar);
            result = result + currentCharToUpperCase;
        }
        return result;
    }

    public static String toLowerCase(String inputString)
    {
        String result = "";
        for (int i = 0; i < inputString.length(); i++)
        {
            char currentChar = inputString.charAt(i);
            char currentCharToLowerCase = Character.toLowerCase(currentChar);
            result = result + currentCharToLowerCase;
        }
        return result;
    }

    public static String toToggleCase(String inputString)
    {
        String result = "";
        for (int i = 0; i < inputString.length(); i++)
        {
            char currentChar = inputString.charAt(i);
            if (Character.isUpperCase(currentChar))
            {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
            else
            {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            }
        }
        return result;
    }

    public static String toCamelCase(String inputString)
    {
        String result = "";
        if (inputString.length() == 0)
        {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++)
        {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (("" + previousChar).matches("[^\\w]|_"))
            {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            }
            else
            {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    public static String toSentenceCase(String inputString)
    {
        String result = "";
        if (inputString.length() == 0) return result;

        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        boolean terminalCharacterEncountered = false;
        char[] terminalCharacters = {'.', '?', '!', '"', '\'', ':', '(', ')'};
        for (int i = 1; i < inputString.length(); i++)
        {
            char currentChar = inputString.charAt(i);
            if (terminalCharacterEncountered)
            {
                if (currentChar == ' ')
                {
                    result = result + currentChar;
                }
                else
                {
                    char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result = result + currentCharToUpperCase;
                    terminalCharacterEncountered = false;
                }
            }
            else
            {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
            for (char terminalCharacter : terminalCharacters)
            {
                if (currentChar == terminalCharacter)
                {
                    terminalCharacterEncountered = true;
                    break;
                }
            }
        }
        return result;
    }

    public static char toggleCharCase(char ch)
    {
        if (Character.isUpperCase(ch)) return Character.toLowerCase(ch);
        return Character.toUpperCase(ch);
    }

    public static String toUpperCaseCharsAt(String inputString, int... indexes)
    {
        String result = "";
        if (inputString.length() == 0) return result;

        char[] chars = inputString.toCharArray();
        for (int index : indexes) chars[index] = Character.toUpperCase(chars[index]);
        for (char ch : chars) result += ch;

        return result;
    }

    public static String toLowerCaseCharsAt(String inputString, int... indexes)
    {
        String result = "";
        if (inputString.length() == 0) return result;

        char[] chars = inputString.toCharArray();
        for (int index : indexes) chars[index] = Character.toLowerCase(chars[index]);
        for (char ch : chars) result += ch;

        return result;
    }

    public static String toggleCaseCharsAt(String inputString, int... indexes)
    {
        String result = "";
        if (inputString.length() == 0) return result;

        char[] chars = inputString.toCharArray();
        for (int index : indexes) chars[index] = toggleCharCase(chars[index]);
        for (char ch : chars) result += ch;

        return result;
    }

    public static String[] splitByMax(String in, int maxLen)
    {
        if (maxLen < 1) throw new IllegalArgumentException("maxLen cannot be less than or equal to zero.");
        int resultSize = (int) Math.ceil(in.length() / (double) maxLen);
        String[] result = new String[resultSize];
        for (int i = 0; i < resultSize; i++)
        {
            result[i] = in.substring(i * maxLen, Math.min((i + 1) * maxLen, in.length()));
        }
        return result;
    }
}

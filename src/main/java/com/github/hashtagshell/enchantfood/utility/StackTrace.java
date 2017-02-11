package com.github.hashtagshell.enchantfood.utility;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public final class StackTrace
{

    public static String getStackTrace(Throwable throwable)
    {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        throwable.printStackTrace(printWriter);
        return result.toString();
    }
}


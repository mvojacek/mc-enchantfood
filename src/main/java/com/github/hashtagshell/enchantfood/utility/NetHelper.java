package com.github.hashtagshell.enchantfood.utility;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;

public class NetHelper
{
    public static String getWANAddress() throws IOException
    {
        URL checkIP;
        BufferedReader in;

        String result;
        checkIP = new URL("http://checkip.amazonaws.com");
        in = new BufferedReader(new InputStreamReader(checkIP.openStream()));
        result = in.readLine();

        return result;
    }

    public static String getLANAddress() throws UnknownHostException, SocketException
    {
        String hostIP = InetAddress.getLocalHost().getHostAddress();
        if (!hostIP.matches("127(.)*"))
        {
            return hostIP;
        }

        /*
         * Above method often returns "127.0.0.1", In this case we need to
         * check all the available network interfaces
         */
        Enumeration<NetworkInterface> nInterfaces = NetworkInterface.getNetworkInterfaces();
        while (nInterfaces.hasMoreElements())
        {
            Enumeration<InetAddress> inetAddresses = nInterfaces.nextElement().getInetAddresses();
            while (inetAddresses.hasMoreElements())
            {
                String address = inetAddresses.nextElement().getHostAddress();
                if (!address.matches("127(.)*") && !address.contains(":"))
                {
                    return address;
                }
            }
        }
        return null;
    }

}

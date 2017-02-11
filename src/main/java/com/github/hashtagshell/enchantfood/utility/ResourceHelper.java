package com.github.hashtagshell.enchantfood.utility;


import net.minecraft.util.ResourceLocation;
import org.w3c.dom.Document;

import com.github.hashtagshell.enchantfood.reference.Ref;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class ResourceHelper
{

    public static ResourceLocation getResourceLocation(String modId, String path)
    {
        return new ResourceLocation(modId, path);
    }

    public static ResourceLocation getResourceLocation(String path)
    {
        return getResourceLocation(Ref.Mod.ID.toLowerCase(), path);
    }

    public static void downloadFromUrl(URL url, File local) throws IOException
    {
        //noinspection ResultOfMethodCallIgnored
        local.getParentFile().mkdirs();
        if (!local.exists())
            //noinspection ResultOfMethodCallIgnored
            local.createNewFile();

        URLConnection urlConn = url.openConnection();//connect

        try (InputStream is = urlConn.getInputStream(); FileOutputStream fos = new FileOutputStream(local))
        {
            byte[] buffer = new byte[40960];              //declare 40KB buffer
            int len;

            //while we have available data, continue downloading and storing to local file
            while ((len = is.read(buffer)) > 0)
            {
                fos.write(buffer, 0, len);
            }
        }
    }

    public static String getRunDir()
    {
        return Paths.get(".").toAbsolutePath().normalize().toString();
    }

    public static Document parseXML(String xml)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputStream is = new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8")));
        Document doc;
        try
        {
            doc = factory.newDocumentBuilder().parse(is);
        } catch (Exception e)
        {
            Log.errorex(e, "Couldn't parse XML");
            return null;
        }
        return doc;
    }


}
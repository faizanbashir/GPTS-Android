package api;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by DELL on 16-Sep-15.
 */
public class CopyCat {

    private String _CLASSNAME;
    private String _FILE;

    public CopyCat(String name, String filename){
        _CLASSNAME = name;
        _FILE = filename;
        Log.d("Copy from Class: " + _CLASSNAME, "Filename: " + _FILE);
    }

    public void CopyCat(InputStream in, OutputStream out) throws IOException{
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

}

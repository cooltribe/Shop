package com.searun.shop.util;

import android.content.Context;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SaveString {
	public static void stringSave(String tag , Context context, String str) {
		try {
			FileOutputStream fOut = context.openFileOutput(tag + "textfile.txt", context.MODE_WORLD_READABLE);
			
			OutputStreamWriter osw = new OutputStreamWriter(fOut);

			// ---write the string to the file---
			osw.write(str);
			osw.flush();
			osw.close();

			// ---display file saved message---
			Toast.makeText(context, "File saved successfully!", Toast.LENGTH_SHORT).show();

			// ---clears the EditText---
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

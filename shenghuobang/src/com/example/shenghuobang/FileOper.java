package com.example.shenghuobang;

import java.io.File;


public class FileOper {
	
	public void deleteFile(File file) {
		if (file.exists()) { 
			if (file.isFile()) { 
				file.delete(); 
			}else if(file.isDirectory()) {
				File files[] = file.listFiles();
				
				for (int i = 0; i < files.length; i++) {
					this.deleteFile(files[i]); 
				}
			}
			
			file.delete();
			
		} else {
		
		}
	}
}

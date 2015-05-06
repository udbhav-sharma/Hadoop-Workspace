package test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class MainTest {
	public static void main(String args[]) throws IOException{
		double x = 1;
		double y = 1;
		double m = 5;
		double n = 15;
		double w=1;
		PrintWriter pw = new PrintWriter(new File("res/input3/roads1"));
		
		for(double i=x;i<m;i++)
			for(double j=y;j<n;j++){
				pw.println(i+" "+j+" "+(i+1)+" "+j+" "+w);
				pw.println(i+" "+j+" "+i+" "+(j+1)+" "+w);
			}
		pw.flush();
		pw.close();
	}
}
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import com.google.gson.Gson;


public class Main {
	public static void main(String args[]) throws IOException, ClassNotFoundException{
		
		String byteString;
		byte[] byteArray = BytesUtil.toByteArray(new A(3,4));
		System.out.println(byteArray.toString());
		A a = (A)BytesUtil.toObject(byteArray);
		System.out.println(a);
		
		byte firstCharNumber = (byte) 10;
		byte secondCharNumber = (byte) 12;
		
		byte b = (byte)((firstCharNumber<<4) | secondCharNumber);
		
		System.out.println(b);
		
		/*Gson gson = new Gson();
		A a = new A(3,4);
		String json = gson.toJson(a);
		System.out.println("JSON String:\n"+json);
		A ob = gson.fromJson(json, A.class);
		System.out.println(ob);*/
	}
}

class A implements Serializable{
	int x;
	B b;
	A(){
		x=0;
		b = new B();
	}
	A(int x,int y){
		this.x=x;
		b = new B(y);
	}
	public String toString(){
		return "A:\nx:"+x+"\n"+b;
	}
}

class B implements Serializable{
	int y;
	B(){
		y=0;
	}
	B(int y){
		this.y=y;
	}
	public String toString(){
		return "B:\ny:"+y;
	}
}

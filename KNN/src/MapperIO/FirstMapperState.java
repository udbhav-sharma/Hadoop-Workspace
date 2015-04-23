package MapperIO;

import java.io.Serializable;

import VoronoiDiagram.Graph;


public class FirstMapperState implements Serializable {
	private static final long serialVersionUID = -8750823852430429802L;
	public int mapperKey;
	public Graph G;
	
	public FirstMapperState( int mapperKey, Graph G ){
		this.mapperKey = mapperKey;
		this.G = G;
	}
	
	public String toString(){
		String output = "-----Mapper State-----\n";
		output += "Mapper Key: "+mapperKey+"\n";
		output += G.toString();
		
		return output;
	}
}

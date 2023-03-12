	import static org.lwjgl.opengl.GL44.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;
public class Buffer {
private int _typeBuffer;
private int _idBuffer;
private FloatBuffer _floatsBuffer;
private float[] _floatData;
public float[] GetFloatData() {return _floatData;}
public int GetIdBuffer() {return _idBuffer;}
public int GetTypeBuffer() {return _typeBuffer;}
public Buffer() {
	_idBuffer=GenBuffer();
}
public Buffer(int typeBuffer)  {
	
	_typeBuffer=typeBuffer;	
	_idBuffer=GenBuffer();
}
public void InitFloatData(float[] bufferFloats) {
	_floatData=bufferFloats;
	_floatsBuffer = MemoryUtil.memAllocFloat(bufferFloats.length);
	_floatsBuffer.put(bufferFloats).flip();	
	BindBuffer();
	glBufferData(GL_ARRAY_BUFFER, _floatsBuffer, GL_STATIC_DRAW);
	MemoryUtil.memFree(_floatsBuffer);
	UnBindBuffer();
}
protected int GenBuffer() {return glGenBuffers();}
public void UnBindBuffer() {
	glBindBuffer(_typeBuffer,0);
 
}
public void DeleteBuffer() {
	glDeleteBuffers(_idBuffer);
	
	//System.out.print("FREE");
}
public void BindBuffer() {
	
	glBindBuffer(_typeBuffer,_idBuffer);
	
	
}
}

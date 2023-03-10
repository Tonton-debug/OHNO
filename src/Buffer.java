	import static org.lwjgl.opengl.GL44.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;
public class Buffer {
private int _typeBuffer;
private int _idBuffer;
private FloatBuffer _floatsBuffer;
public FloatBuffer GetFloatsBuffer() {return _floatsBuffer;}
public int GetIdBuffer() {return _idBuffer;}
public int GetTypeBuffer() {return _typeBuffer;}
public Buffer(int typeBuffer)  {
	
	_typeBuffer=typeBuffer;	
	_idBuffer=GenBuffer();
}
public void InitFloatData(float[] bufferFloats) {
	_floatsBuffer = MemoryUtil.memAllocFloat(bufferFloats.length);
	_floatsBuffer.put(bufferFloats).flip();	
}
protected int GenBuffer() {return glGenBuffers();}
public void UnBindBuffer() {
	glBindBuffer(_typeBuffer,0);
}
public void BindBuffer() {
	
	glBindBuffer(_typeBuffer,_idBuffer);
	glBufferData(GL_ARRAY_BUFFER, _floatsBuffer, GL_STATIC_DRAW);
	MemoryUtil.memFree(_floatsBuffer);
	
}
}

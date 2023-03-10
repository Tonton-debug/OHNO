import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL44.*;

import org.lwjgl.system.MemoryUtil;
public class VertexBuffer extends Buffer{
private int _countCoordinate;
public VertexBuffer(int countCoordinate)  {
	
	super(-1);

	_countCoordinate=countCoordinate;

}
@Override
public int GenBuffer() {
	
	return glGenVertexArrays();
}

public void EnableBuffer() {
	glVertexAttribPointer(0, _countCoordinate, GL_FLOAT, false, 0, 0);	
	 glEnableVertexAttribArray(0);
}
@Override
public void UnBindBuffer() {
	glBindVertexArray(0);
}
@Override
public void BindBuffer() {
	glBindVertexArray(GetIdBuffer());
	
}
}

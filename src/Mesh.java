import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

public class Mesh {
private VertexBuffer _vertexBuffer;
private Buffer _vboBuffer;
public Mesh(VertexBuffer vertexBuffer,Buffer vboBuffer,float[] floatData) {
	_vertexBuffer=vertexBuffer;
	_vboBuffer=vboBuffer;
	_vboBuffer.InitFloatData(floatData);

}
private int GetCountPoints() { return (_vboBuffer.GetFloatData().length/_vertexBuffer.GetCountCoordinate());}
public void DeleteBuffers() {
	_vboBuffer.DeleteBuffer();
	_vertexBuffer.DeleteBuffer();
}
public void DrawMesh(ShaderProgram shader) {
	shader.Use();

	_vertexBuffer.BindBuffer();
	_vboBuffer.BindBuffer();
	_vertexBuffer.EnableBuffer();

	  glDrawArrays(GL_TRIANGLES, 0, GetCountPoints());
	_vboBuffer.UnBindBuffer();
	_vertexBuffer.DisableBuffer();
}
}

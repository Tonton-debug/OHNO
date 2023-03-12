import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL44.*;

public abstract class GameObject {
private Vector3f _position=new Vector3f(0,0,0);
private Vector3f _scale=new Vector3f(1,1,1);
private Vector3f _rotate=new Vector3f(0,0,0);
private Vector4f _color=new Vector4f(0,0,0,1);
private Mesh _mesh;
private ShaderProgram _shader;
public ShaderProgram GetShaderProgram() {return _shader;}
public Vector3f GetPosition() {return _position;}
public Vector3f GetScale() {return _scale;}
public Vector3f GetRotate() {return _rotate;}
public Vector4f GetColor() {return _color;}
public void SetPosition(Vector3f position) {_position=position;}
public void SetScale(Vector3f scale) {_scale=scale;}
public void SetRotate(Vector3f rotate) {_rotate=rotate;}
public void SetColor(Vector4f color) {_color=color;}
public GameObject(float[] vertexs,ShaderProgram shader) {
	_mesh=new Mesh(new VertexBuffer(3),new Buffer(GL_ARRAY_BUFFER),vertexs);
	_shader=shader;
}

protected void Draw() {
try {
_mesh.DrawMesh(_shader);
}catch(Exception ex){
	
}
}
public void Delete() {
	_mesh.DeleteBuffers();
}
}

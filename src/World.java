import org.joml.Matrix4f;

public class World {
private static World _mainWorld;
private Matrix4f _perpective;
public Matrix4f GetPerspective() {return _perpective;}
public World() {
	
}
public static World Instance() {
	if(_mainWorld==null) {
		_mainWorld=new World();
	}
	return _mainWorld;
}

public void CreatePerpective(float fov,float z_near,float z_far,float aspectRatio) {
	_perpective=new Matrix4f().perspective(fov, aspectRatio, z_near, z_far);
}
}

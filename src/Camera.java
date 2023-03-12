import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f _position;

    private Vector3f _rotation;

    public Camera() {
    	_position = new Vector3f(0, 0, 0);
    	_rotation = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f position, Vector3f rotation) {
    	_position = position;
    	_rotation = rotation;
    }

    public Vector3f GetPosition() {
        return _position;
    }

    public void SetPosition(Vector3f position) {
    	_position=position;
    }
    public Vector3f getRotation() {
        return _rotation;
    }
    public Matrix4f getViewMatrix() {
    	Matrix4f viewMatrix=new Matrix4f().identity();
        // First do the rotation so camera rotates over its position
        viewMatrix.rotate((float)Math.toRadians(_rotation.x), new Vector3f(1, 0, 0))
            .rotate((float)Math.toRadians(_rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        viewMatrix.translate(-_position.x, -_position.y, -_position.z);
        return viewMatrix;
    }

    public void SetRotation(Vector3f rotate) {
    	_rotation = rotate;
    }
}
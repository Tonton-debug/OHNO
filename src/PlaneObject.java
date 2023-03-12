
public class PlaneObject extends GameObject {
	private final static float[] _vertexs= new float[]{
		    -1,  1, 1,
		    1, 1,1,
		     -1, -1, 1,
		     1,-1,1,
		     1, 1,1,
		     -1, -1,1,
		     
			    -1,  1, -1,
			    1, 1,-1,
			     -1, -1, -1,
			     1,-1,-1,
			     1, 1,-1,
			     -1, -1,-1,
			     
			     
				    -1,  1, 1,
				    -1, 1,-1,
				     -1, -1, -1,
				     -1,-1,-1,
				     -1, -1,1,
				     -1, 1,1,
				     
					    1,  1, 1,
					    1, 1,-1,
					     1, -1, -1,
					     1,-1,-1,
					     1, -1,1,
					     1, 1,1,
					     
					     1,1,1,
					     1,1,-1,
					     -1,1,-1,
					     -1,1,1,
					     -1,1,-1,
					     1,1,1,-1
	};

	public PlaneObject( ShaderProgram shader) {
		super(_vertexs, shader);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void Draw() {
		GetShaderProgram().SetUniform("projectionMatrix", World.Instance().GetPerspective());
		GetShaderProgram().SetUniform("worldMatrix", World.Instance().GetWorldMatrix(this));
		GetShaderProgram().SetUniform("color", GetColor());
		super.Draw();
	}
}

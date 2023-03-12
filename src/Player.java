import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import org.joml.Vector3f;
import org.joml.Vector4f;
//TODO: create interface
public class Player extends Entity  {


private int _patronCount=5;
private int _shootTimer=30;
private int _reloadTimer=50;
private TriangleObject _patronObject;


private void DrawPatron() {
	
	_patronObject.SetScale(new Vector3f(0.1f,0.1f,0.1f));
	_patronObject.SetColor(new Vector4f(1,0,0,1));
	_patronObject.SetPosition(new Vector3f(GetGameObject().GetPosition().x-0.35f,GetGameObject().GetPosition().y+0.6f,GetGameObject().GetPosition().z));
	
	DrawArrayObjects(_patronObject,_patronCount,new Vector3f(0.2f,0,0));
}
@Override
public void Draw() {
	if(_shootTimer>=0)
		_shootTimer-=0.1f;
	if(_reloadTimer>=0)
		_reloadTimer-=1f;
	else {
		_reloadTimer=50;
		if(_patronCount!=5)
			_patronCount+=1;
	}
	DrawPatron();
	super.Draw();
}
public Player() {
	super(new TriangleObject(World.Instance().GetMainShader()),5,0.1f);
	_patronObject=new TriangleObject(World.Instance().GetMainShader());
	GetGameObject().SetPosition(new Vector3f(0,-1.5f,-2));
	GetGameObject().SetScale(new Vector3f(0.5f,0.5f,0.3f));
}
public void Shot() {

	if(_patronCount>0 && _shootTimer<=0) {
		
		_patronCount-=1;
		_shootTimer=30;
		Bullet bullet=new Bullet(new TriangleObject(World.Instance().GetMainShader()),1,-0.5f);
		bullet.GetGameObject().SetPosition(new Vector3f(GetGameObject().GetPosition().x,GetGameObject().GetPosition().y,GetGameObject().GetPosition().z));
		World.Instance().AddBullet(bullet);
		World.Instance().Shot.Play();
	}
}
public void Move(Vector3f moveVector) {
	Vector3f resultVector=new Vector3f(GetGameObject().GetPosition().x+moveVector.x,GetGameObject().GetPosition().y+moveVector.y,GetGameObject().GetPosition().z+moveVector.z);
	if(resultVector.x>-3f&&resultVector.x<3)
		GetGameObject().SetPosition(resultVector);
}

@Override
public void Damage(int damage) {
	if(GetHealth()-damage<=0)
	glfwSetWindowShouldClose(MainWindow.window, true); 
	else
		super.Damage(damage);
}
}

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class World {
private static World _mainWorld;
private Matrix4f _perpective;
private ShaderProgram _mainShader;
private Player _player;
public final AudioSource Music;
public final AudioSource Fun;
public final AudioSource Shot;
public final AudioSource Die;
public final AudioSource Hit;
public final AudioSource HitPlayer;
private Camera _camera=new Camera(new Vector3f(0,2,2),new Vector3f(20,0,0));
private ArrayList<Enemy> _enemies=new ArrayList<Enemy>();
private ArrayList<Bullet> _bullets=new ArrayList<Bullet>();
public Camera GetCamera() {return _camera;}
public Player GetPlayer() {return _player;}
public Matrix4f GetPerspective() {return _perpective;}
public World() {
	Music=new AudioSource("music.ogg", true);
	Fun=new AudioSource("fun.ogg",false);
	Music.Play();
	Shot=new AudioSource("shot.ogg", false);
	Die=new AudioSource("die.ogg", false);
	Hit=new AudioSource("hit.ogg", false);
	HitPlayer=new AudioSource("hit_player.ogg",false);
}
private Enemy GetNearestEnemy(Vector3f position,float minDistance) {
	Enemy returnEnemy=null;
	for(Enemy enemy:_enemies) {
		if(Vector3f.distance(position.x, position.y, position.z, enemy.GetGameObject().GetPosition().x, enemy.GetGameObject().GetPosition().y, enemy.GetGameObject().GetPosition().z)<minDistance) {
			returnEnemy=enemy;
		}
		
	}
	return returnEnemy;
}
private void UpdateBullets() {
	 ArrayList<Bullet> bulletsRemove=new ArrayList<Bullet>();
	for(Bullet bullet:_bullets) {
		if(bullet.GetHealth()<=0) {
			bulletsRemove.add(bullet);
			continue;
		}
		bullet.Draw();
		
		Enemy nearestEnemy=GetNearestEnemy(bullet.GetGameObject().GetPosition(),0.5f);
		if(nearestEnemy!=null) {
			bullet.OnCollision(nearestEnemy);
			bulletsRemove.add(bullet);
		}
		bullet.Move();
	}
	for(Bullet bullet:bulletsRemove) {
		RemoveBullet(bullet);
	}
}
private void UpdateEnemies() {
	 ArrayList<Enemy> enemiesRemove=new ArrayList<Enemy>();
	for(Enemy enemy:_enemies) {
		if(enemy.GetHealth()<=0) {
			
			enemiesRemove.add(enemy);
		}else {
		if(enemy.GetGameObject().GetPosition().z>-700)
		enemy.Draw();
		
		enemy.Move();
		if(Vector3f.distance(enemy.GetGameObject().GetPosition().x, enemy.GetGameObject().GetPosition().y, enemy.GetGameObject().GetPosition().z,
_player.GetGameObject().GetPosition().x, _player.GetGameObject().GetPosition().y, _player.GetGameObject().GetPosition().z)<1)
			((Soldier) enemy).OnCollisionPlayer();
		}
	}
	for(Enemy enemy:enemiesRemove) {
		RemoveEnemy(enemy);
	}
}
public void UpdateWorld() {
	UpdateEnemies();
	UpdateBullets();
}
public void RemoveBullet(Bullet bullet) {

_enemies.remove(bullet);
}
public void AddBullet(Bullet bullet) {
	_bullets.add(bullet);
}
public void RemoveEnemy(Enemy enemy) {
	_enemies.remove(enemy);
}
public void AddEnemy(Enemy enemy) {
	_enemies.add(enemy);
}
public void CreateNewPlayer() {
	_player=new Player();
}
public void CreateMainShader(ShaderProgram shaderProgram) {
	_mainShader=shaderProgram;
}
public ShaderProgram GetMainShader() {
	return _mainShader;
}
public static World Instance() {
	if(_mainWorld==null) {
		_mainWorld=new World();
	}
	return _mainWorld;
}
public Matrix4f GetWorldMatrix(GameObject gameObject) {
	Matrix4f worldMatrix= new Matrix4f().identity().translate(gameObject.GetPosition()).
            rotateX((float)Math.toRadians(gameObject.GetRotate().x)).
            rotateY((float)Math.toRadians(gameObject.GetRotate().y)).
            rotateZ((float)Math.toRadians(gameObject.GetRotate().z)).
            scale(gameObject.GetScale());
	
	return _camera.getViewMatrix().mul(worldMatrix);
}
public void CreatePerpective(float fov,float z_near,float z_far,float aspectRatio) {
	_perpective=new Matrix4f().perspective(fov, aspectRatio, z_near, z_far);
}
}

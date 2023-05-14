import org.joml.Math;
import org.joml.Vector3f;

public class Soldier extends Enemy {

	public Soldier(EnemyType type,int health,float speed) {
		super(type,new PlaneObject(World.Instance().GetMainShader()),health,speed);
		// TODO Auto-generated constructor stub
		GetGameObject().SetPosition(new Vector3f(0,-1.5f,-10));
		GetGameObject().SetScale(new Vector3f(0.5f,0.5f,0.5f));
	}

	@Override
	public void Move() {

		Vector3f startPosition=GetGameObject().GetPosition();
	/*	Player player=World.Instance().GetPlayer();
		if(Math.abs(startPosition.x-player.GetGameObject().GetPosition().x)>0.2f) {
			if(startPosition.x>player.GetGameObject().GetPosition().x)
				startPosition.x-=GetSpeed()*2;
			else
				startPosition.x+=GetSpeed()*2;
		}*/
		startPosition.z+=GetSpeed();
		GetGameObject().SetPosition(startPosition);
		if(startPosition.z>2) {
			Destroy();
			return;
		}
	
	}

	
	public void OnCollisionPlayer() {
		// TODO Auto-generated method stub
		if(GetTypeEnemy()==EnemyType.Health) {
			World.Instance().GetPlayer().AddHealth(1);
			World.Instance().Fun.Play();
		}
		if(GetTypeEnemy()==EnemyType.Soldier) {
		World.Instance().GetPlayer().Damage(1);
		
		World.Instance().HitPlayer.Play();
		}
		Destroy();
	}

}

import org.joml.Vector3f;

public class Bullet extends Entity {

	public Bullet(GameObject gameObject, int health, float speed) {
		super(gameObject, health, speed);
		gameObject.SetScale(new Vector3f(0.2f,0.2f,0.2f));
		// TODO Auto-generated constructor stub
	}
	public void Draw() {

		GetGameObject().Draw();
	}
	public void Move() {
		GetGameObject().GetPosition().z+=GetSpeed();
		if(GetGameObject().GetPosition().z<-70) {

			Destroy();
			
		}
	}
	public void OnCollision(Enemy enemy) {
		enemy.Damage(1);
		if(enemy.GetHealth()<=0)
			World.Instance().Die.Play();
		else
		World.Instance().Hit.Play();
		Destroy();
	}

}

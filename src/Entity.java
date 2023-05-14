import org.joml.Vector3f;
import org.joml.Vector4f;

public class Entity {
	private GameObject _healthObject;
	private GameObject _gameObject;
	public GameObject GetGameObject() {return _gameObject;}
    private int _health;
    private float _speed;
    public void AddHealth(int c) {_health+=c;}
    public int GetHealth() {return _health;}
    public float GetSpeed() {return _speed;}
    public void Destroy() {
 		_gameObject.Delete();
	//w	_healthObject.Delete();
    	_health=-1;
    }
    public void Damage(int damage) {
    	_health-=damage;
    	if(_health<=0) {
    		Destroy();
   
    	}
	}
    public Entity(GameObject gameObject,int health, float speed) {
    	_health=health;
    	_speed=speed;
    	_gameObject=gameObject;
    	_healthObject=new PlaneObject(World.Instance().GetMainShader());
    	
    }
    protected void DrawArrayObjects(GameObject obj,int value,Vector3f nearest) {
		Vector3f startPosition=obj.GetPosition();
	for (int i=1;i<=value; i++) {
			
		obj.SetPosition(startPosition);
		obj.Draw();
			startPosition.x+=nearest.x;
			startPosition.y+=nearest.y;
			startPosition.z+=nearest.z;
		}
	}
    public void Draw() {
    	DrawHealth();
    	
    	GetGameObject().Draw();
    }
	protected void DrawHealth() {
		_healthObject.SetPosition(new Vector3f(_gameObject.GetPosition().x-0.4f,_gameObject.GetPosition().y+1f,_gameObject.GetPosition().z-1));
		_healthObject.SetScale(new Vector3f(0.2f,0.2f,0.2f));
		_healthObject.SetColor(new Vector4f(1,0,0,1));
		DrawArrayObjects(_healthObject,_health,new Vector3f(0.2f,0,0));
	}
}

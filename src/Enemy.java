
public abstract class Enemy extends Entity {
private EnemyType _typeEnemy;
public EnemyType GetTypeEnemy() {return _typeEnemy;}
public Enemy(EnemyType type,GameObject gameObject,int health,float speed) {
	super(gameObject,health,speed);
	_typeEnemy=type;
}
public abstract void  Move();


}

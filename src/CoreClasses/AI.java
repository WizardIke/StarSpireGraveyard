package CoreClasses;

import Components.Category;
import Components.GameObject;
import Components.Position;

import java.util.List;


/**
 * Created by Isaac on 14/12/2016.
 */
public class AI {
    public static class Target {
        public GameObject creature;
        public float dispacementX, dispacementY;
        public float distance;
    }
    public static Target findTarget(GameObject creature, List<GameObject> creatures) {
        Position creaturePosition = creature.getPosition();
        float x = creaturePosition.x;
        float y = creaturePosition.y;
        float min = Float.MAX_VALUE;
        GameObject target = null;
        float distanceX = 0, distanceY = 0;
        for (GameObject gameObject : creatures) {
            if(gameObject.getCategory() == Category.Creature && creature.getFaction().isEnemy(gameObject.getFaction())) {
                Position enemyCreaturePosition = gameObject.getPosition();
                distanceX = enemyCreaturePosition.x - x;
                distanceY = enemyCreaturePosition.y - y;
                float distance = distanceX * distanceX + distanceY * distanceY;
                if(distance < min) {
                    min = distance;
                    target = gameObject;
                }
            }
        }
        if(target != null) {
            Target ret = new Target();
            ret.creature = target;
            ret.dispacementX = distanceX;
            ret.dispacementY = distanceY;
            ret.distance = (float)Math.sqrt(min);
            return ret;
        } else return null;
    }
}

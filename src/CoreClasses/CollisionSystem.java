package CoreClasses;

import Components.*;

import static java.lang.Math.sqrt;

/**
 * Created by Isaac on 13/12/2016.
 */
public class CollisionSystem {
    private static boolean check(CircleHitbox circle1, CircleHitbox circle2) {
        return ((circle1.position.x - circle2.position.x) * (circle1.position.x - circle2.position.x) +
                (circle1.position.y - circle2.position.y) * (circle1.position.y - circle2.position.y) <
                (circle1.radius + circle2.radius) * (circle1.radius + circle2.radius));
    }

    public static boolean check(AlignedRectangleHitbox alignedRectangle1, AlignedRectangleHitbox alignedRectangle2) {
        return (alignedRectangle1.position.x > alignedRectangle2.position.x &&
                alignedRectangle1.position.x < alignedRectangle2.position.x + alignedRectangle2.width &&
                alignedRectangle1.position.y > alignedRectangle2.position.y &&
                alignedRectangle1.position.y < alignedRectangle2.position.y + alignedRectangle2.height);
    }

    private static boolean check(CircleHitbox circle, AlignedRectangleHitbox rect)
    {
        double rectHalfWidth = rect.width * 0.5;
        double circleDistX = Math.abs(circle.position.x - (rect.position.x + rectHalfWidth));
        double distX = rectHalfWidth + circle.radius;
        if (circleDistX > distX)
            return false;
        double rectHalfHeight = rect.height * 0.5;
        double circleDistY = Math.abs(circle.position.y - (rect.position.y + rectHalfHeight));
        double distY = rectHalfHeight + circle.radius;
        if (circleDistY > distY)
            return false;
        if (circleDistX <= rectHalfWidth || circleDistY <= rectHalfHeight)
            return true;
        double xCornerDistSq = circleDistX - rectHalfWidth;
        xCornerDistSq *= xCornerDistSq;
        double yCornerDistSq = circleDistY - rectHalfHeight;
        yCornerDistSq *= yCornerDistSq;
        double maxCornerDistSq = circle.radius * circle.radius;
        return xCornerDistSq + yCornerDistSq <= maxCornerDistSq;
    }

    public static Dispacement distanceBetweenCircleAndCircle(CircleHitbox circle1, CircleHitbox circle2){
        float directionX = circle1.position.x - circle2.position.x;
        float directionY = circle1.position.y - circle2.position.y;
        if(directionX == 0.0f && directionY == 0.0f){
            directionX = 0.0001f;
            directionY = 0.0001f;
        }
        float distance = (float)Math.sqrt(directionX * directionX + directionY * directionY);

        directionX /= distance;
        directionY /= distance;
        distance = circle1.radius + circle2.radius - distance;
        return new Dispacement(directionX * distance, directionY * distance);
    }

    public static Dispacement distanceBetweenCircleAndRectangle(CircleHitbox circle, AlignedRectangleHitbox rectangle) {
        float distanceX, distanceY;
        float rectangleMaxX = rectangle.position.x + rectangle.width;
        float rectangleMaxY = rectangle.position.y + rectangle.height;
        if(circle.position.y > rectangle.position.y && circle.position.y < rectangleMaxY) {
            distanceX = ((rectangle.position.x + rectangleMaxX) * 0.5f) - circle.position.x;
            if(distanceX > 0.0f) {
                distanceX -= ((( rectangleMaxX - rectangle.position.x) * 0.5f) + circle.radius);
            }
            else {
                distanceX += ((( rectangleMaxX - rectangle.position.x) * 0.5f) + circle.radius);
            }
            distanceY = 0.0f;
        }
        else if(circle.position.x > rectangle.position.x && circle.position.x < rectangleMaxX){
            distanceY = ((rectangle.position.y + rectangleMaxY) * 0.5f) - circle.position.y;
            if(distanceY > 0.0f) {
                distanceY -= (((rectangleMaxY - rectangle.position.y) * 0.5f) + circle.radius);
            }
            else {
                distanceY += (((rectangleMaxY - rectangle.position.y) * 0.5f) + circle.radius);
            }
            distanceX = 0.0f;
        }
        else {
            if(circle.position.x > rectangleMaxX && circle.position.y > rectangleMaxY) {
                float dirX = circle.position.x - rectangleMaxX;
                float dirY = circle.position.y - rectangleMaxY;
                float distance = (float)sqrt(dirX * dirX + dirY * dirY);
                dirX /= distance;
                dirY /= distance;
                distance = circle.radius - distance;
                distanceX = dirX * distance;
                distanceY = dirY * distance;
            }
            else if(circle.position.x > rectangleMaxX && circle.position.y < rectangle.position.y){
                float dirX = circle.position.x - rectangleMaxX;
                float dirY = circle.position.y - rectangle.position.y;
                float distance = (float)sqrt(dirX * dirX + dirY * dirY);
                dirX /= distance;
                dirY /= distance;
                distance = circle.radius - distance;
                distanceX = dirX * distance;
                distanceY = dirY * distance;
            }
            else if(circle.position.y > rectangleMaxY){
                float dirX = circle.position.x - rectangle.position.x;
                float dirY = circle.position.y - rectangleMaxY;
                float distance = (float)sqrt(dirX * dirX + dirY * dirY);
                dirX /= distance;
                dirY /= distance;
                distance = circle.radius - distance;
                distanceX = dirX * distance;
                distanceY = dirY * distance;
            }
            else{
                float dirX = circle.position.x - rectangle.position.x;
                float dirY = circle.position.y - rectangle.position.y;
                float distance = (float)sqrt(dirX * dirX + dirY * dirY);
                dirX /= distance;
                dirY /= distance;
                distance = circle.radius - distance;
                distanceX = dirX * distance;
                distanceY = dirY * distance;
            }
        }
        return new Dispacement(distanceX, distanceY);
    }

    public static Dispacement distanceBetweenRectangleAndRectangle(AlignedRectangleHitbox r1, AlignedRectangleHitbox r2) {
        return new Dispacement(0, 0); //TODO fix
    }

    private static void resolvePenitration(Dispacement dispacement, Position position1, float mass1, Position position2, float mass2) {
        float distanceX = dispacement.x, distanceY = dispacement.y;
        if (mass1 != Float.POSITIVE_INFINITY) {
            distanceX *= (mass1 / (mass1 * mass2));
            distanceY *= (mass1 / (mass1 * mass2));
        }
        position2.x -= distanceX;
        position2.y -= distanceY;

        distanceX = dispacement.x;
        distanceY = dispacement.y;
        if (mass2 != Float.POSITIVE_INFINITY) {
            distanceX *= (mass2 / (mass2 * mass1));
            distanceY *= (mass2 / (mass2 * mass1));
        }
        position1.x += distanceX;
        position1.y += distanceY;
    }

    public static void collideCircleAndCircle(Main main, CircleHitbox c1, CircleHitbox c2) {
        if(check(c1, c2)) {
            Dispacement dispacement = distanceBetweenCircleAndCircle(c1, c2);
            resolvePenitration(dispacement, c1.position, c1.mass, c2.position, c2.mass);
        }
    }

    public static void collideCircleAndAlignedRect(Main main, CircleHitbox c1, AlignedRectangleHitbox r1) {
        if(check(c1, r1)) {
            Dispacement dispacement = distanceBetweenCircleAndRectangle(c1, r1);
            resolvePenitration(dispacement, c1.position, c1.mass, r1.position, r1.mass);
        }
    }

    public static void collideAlignedRectAndAlignedRect(Main main, AlignedRectangleHitbox r1, AlignedRectangleHitbox r2) {
        if(check(r1, r2)) {
            Dispacement dispacement = distanceBetweenRectangleAndRectangle(r1, r2);
            resolvePenitration(dispacement, r1.position, r1.mass, r2.position, r2.mass);
        }
    }

    public static void collideSkeletonAndAlignedRect(Main main, TriggerHitBox c1, AlignedRectangleHitbox r1) {
        if(check(c1, r1)) {
            Dispacement dispacement = distanceBetweenCircleAndRectangle(c1, r1);
            resolvePenitration(dispacement, c1.position, c1.mass, r1.position, r1.mass);
            c1.handleCollision(main, c1.owner, r1.owner);
        }
    }

    public static void collideSkeletonAndCircle(Main main, TriggerHitBox c1, CircleHitbox c2) {
        if(check(c1, c2)) {
            Dispacement dispacement = distanceBetweenCircleAndCircle(c1, c2);
            resolvePenitration(dispacement, c1.position, c1.mass, c2.position, c2.mass);
            c1.handleCollision(main, c1.owner, c2.owner);
        }
    }

    public static void collideSkeletonAndSkeleton(Main main, TriggerHitBox c1, TriggerHitBox c2) {
        if(check(c1, c2)) {
            Dispacement dispacement = distanceBetweenCircleAndCircle(c1, c2);
            resolvePenitration(dispacement, c1.position, c1.mass, c2.position, c2.mass);
            c1.handleCollision(main, c1.owner, c2.owner);
            c2.handleCollision(main, c2.owner, c1.owner);
        }
    }

    public static void update(Main main) {
        for(int i = 0; i < main.gameObjects.size(); ++i) {
            int oldLength = main.gameObjects.size();
            for(int j = main.gameObjects.size() - 1; j > i; --j) {
                HitBox h1 = main.gameObjects.get(i).getHitbox();
                HitBox h2 = main.gameObjects.get(j).getHitbox();
                if(h1 != null && h2 != null) {
                    h1.collide(main, h2);
                }
                if(main.gameObjects.size() != oldLength) {
                    break;
                }
            }
        }
    }
}

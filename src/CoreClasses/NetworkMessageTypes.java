package CoreClasses;

/**
 * Created by Isaac on 25/01/2017.
 */
public class NetworkMessageTypes {
    public static final byte startGame = (byte)0;
    public static final byte connectionLost = (byte)1;
    public static final byte gameObject = (byte)2;

    public static final byte castSpell = (byte)0;
    public static final byte raiseSkeleton = (byte)1;
    public static final byte raiseSkeletons = (byte)7;
    public static final byte summonSkeletonHorde = (byte)8;
    public static final byte fireBolt = (byte)12;

    public static final byte setAwesomeness = (byte)2;
    public static final byte setX = (byte)3;
    public static final byte setY = (byte)4;
    public static final byte die = (byte)5;
    public static final byte setName = (byte)6;
    public static final byte setHealth = (byte)9;
    public static final byte setPos = (byte)10;
    public static final byte explode = (byte)11;
}

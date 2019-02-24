package CoreClasses;

import java.awt.geom.AffineTransform;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 24/01/2017.
 */
public class Matrix2d extends AffineTransform {
    public Matrix2d(){
        super();
    }

    public Matrix2d(double m00, double m10, double m01, double m11, double m02, double m12){
        super(m00, m10, m01, m11, m02, m12);
    }

    public Matrix2d(DataInputStream saveData) throws java.io.IOException{
        super(saveData.readDouble(), saveData.readDouble(), saveData.readDouble(), saveData.readDouble(),
                saveData.readDouble(), saveData.readDouble());
    }

    public void save(DataOutputStream saveData) throws IOException{
        double[] view = new double[6];
        this.getMatrix(view);
        saveData.writeDouble(view[0]);
        saveData.writeDouble(view[1]);
        saveData.writeDouble(view[2]);
        saveData.writeDouble(view[3]);
        saveData.writeDouble(view[4]);
        saveData.writeDouble(view[5]);
    }
}

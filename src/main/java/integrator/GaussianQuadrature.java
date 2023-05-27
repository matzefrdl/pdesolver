package integrator;

import galerkin.WeakForm;
import galerkin.basefunctions.BaseFunction;
import maths.Vec2;
import maths.Vec3;

public class GaussianQuadrature {

    public static float integrate(Vec2 v0, Vec2 v1, Vec2 v2, WeakForm wf, BaseFunction test, BaseFunction basis) {
        float[] u = {
                0.0571041961f,
                0.2768430136f,
                0.5835904324f,
                0.8602401357f,
                0.0571041961f,
                0.2768430136f,
                0.5835904324f,
                0.8602401357f,
                0.0571041961f,
                0.2768430136f,
                0.5835904324f,
                0.8602401357f,
                0.0571041961f,
                0.2768430136f,
                0.5835904324f,
                0.8602401357f,
        };

        float[] v = {
                0.06546699455602246f,
                0.05021012321401679f,
                0.02891208422223085f,
                0.009703785123906346f,
                0.3111645522491480f,
                0.2386486597440242f,
                0.1374191041243166f,
                0.04612207989200404f,
                0.6317312516508520f,
                0.4845083266559759f,
                0.2789904634756834f,
                0.09363778440799593f,
                0.8774288093439775f,
                0.6729468631859832f,
                0.3874974833777692f,
                0.1300560791760936f
        };
        float[] g = {
                0.04713673637581137f,
                0.07077613579259895f,
                0.04516809856187617f,
                0.01084645180365496f,
                0.08837017702418863f,
                0.1326884322074010f,
                0.08467944903812383f,
                0.02033451909634504f,
                0.08837017702418863f,
                0.1326884322074010f,
                0.08467944903812383f,
                0.02033451909634504f,
                0.04713673637581137f,
                0.07077613579259895f,
                0.04516809856187617f,
                0.01084645180365496f
        };
        //clockwise
        float sum = 0;
        float area = (v1.x - v0.x) * (v2.y - v0.y) - (v2.x - v0.x) * (v1.y - v0.y);
        for(int i = 0; i < g.length; i++) {
            //float x = u[i];
            //float y = v[i];
            float x = v0.x + (v1.x - v0.x) * u[i] + (v2.x - v0.x) * v[i];
            float y = v0.y + (v1.y - v0.y) * u[i] + (v2.y - v0.y) * v[i];
            sum += g[i] * wf.eval(test, basis, new Vec3(x,y, 0));
        }

        return 0.5f * Math.abs(area) * sum;
    }

}

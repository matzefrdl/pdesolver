package galerkin.meshes;

import galerkin.PDE;
import galerkin.elements.Element;
import galerkin.elements.Triangle;
import maths.Matrix;
import maths.Vec2;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.triangulate.ConformingDelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.w3c.dom.ls.LSException;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mesh2D extends Mesh {
    public Mesh2D(PDE pde, int N, float a, float b) {
        super(pde);
        float h = (b-a) / (N-1);

        DelaunayTriangulationBuilder dtb = new DelaunayTriangulationBuilder();

        List<Coordinate> coords = new ArrayList<>();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(i == 0 || i == N-1 || j == 0 || j == N-1)
                    coords.add(new Coordinate(i * h,j * h));
                else
                    coords.add(new Coordinate((i * h +Math.random() * h/2),j * h +Math.random() * h/2));
            }
        }

        this.numKnots = coords.size();
        dtb.setSites(coords);

        GeometryCollection triangles = (GeometryCollection) dtb.getTriangles(new GeometryFactory());

        this.elements = new Element[triangles.getNumGeometries()];
        for(int i = 0; i < triangles.getNumGeometries(); i++) {
            Geometry geo = triangles.getGeometryN(i);
            int k0 = coords.indexOf(geo.getCoordinates()[0]);
            int k1 = coords.indexOf(geo.getCoordinates()[1]);
            int k2 = coords.indexOf(geo.getCoordinates()[2]);
            Vec2 v0 = new Vec2((float) geo.getCoordinates()[0].x, (float) geo.getCoordinates()[0].y);
            Vec2 v1 = new Vec2((float) geo.getCoordinates()[1].x, (float) geo.getCoordinates()[1].y);
            Vec2 v2 = new Vec2((float) geo.getCoordinates()[2].x, (float) geo.getCoordinates()[2].y);
            elements[i] = new Triangle(v0, v1, v2, k0, k1, k2);
        }
    }
}

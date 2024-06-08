package lehjr.numina.client.gui.geometry;

import lehjr.numina.common.math.Color;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class GradientAndArcCalculator {

    // based on code from: http://www.java2s.com/example/java/javax.media.opengl/draw-sphere-with-opengl.html
    public static FloatBuffer getSphereVertices(int detail, float radius) {
        int stacks = detail;
        int slices =  detail;
        FloatBuffer vertices = BufferUtils.createFloatBuffer(stacks * (slices << 1) * 6); // * 6 because 2 pairs of xyz per loop
        float r0, r1, alpha0, alpha1, x0, x1, y0, y1, z0, z1, beta;
        float stackStep = (float) (Math.PI / stacks);
        float sliceStep = (float) (Math.PI / slices);
        for (int i = 0; i < stacks; ++i) {
            alpha0 = (float) (-Math.PI / 2 + (float)i * stackStep);
            alpha1 = alpha0 + stackStep;
            r0 = (float) (radius * Math.cos(alpha0));
            r1 = (float) (radius * Math.cos(alpha1));

            y0 = (float) (radius * Math.sin(alpha0));
            y1 = (float) (radius * Math.sin(alpha1));

            for (int j = 0; j < (slices << 1); ++j) {
                beta = j * sliceStep;//  www.java2s.com
                x0 = (float) (r0 * Math.cos(beta));
                x1 = (float) (r1 * Math.cos(beta));

                z0 = (float) (-r0 * Math.sin(beta));
                z1 = (float) (-r1 * Math.sin(beta));

                vertices.put(x0);
                vertices.put(y0);
                vertices.put(z0);

                vertices.put(x1);
                vertices.put(y1);
                vertices.put(z1);
            }
        }
        vertices.flip();
        return vertices;
    }

    /**
     * Efficient algorithm for drawing circles and arcs in pure opengl!
     *
     * @param startangle Start angle in radians
     * @param endangle   End angle in radians
     * @param radius     Radius of the circle (used in calculating number of segments to draw as well as size of the arc)
     * @param xoffset    Convenience parameter, added to every vertex
     * @param yoffset    Convenience parameter, added to every vertex
     * @param zoffset    Convenience parameter, added to every vertex
     * @return
     */
    public static FloatBuffer getArcPoints(float startangle, float endangle, float radius, float xoffset, float yoffset, float zoffset) {
        int numVertices = (int) Math.ceil(Math.abs((endangle - startangle) * 2 * Math.PI));
        float theta = (endangle - startangle) / numVertices;
        FloatBuffer buffer = BufferUtils.createFloatBuffer(numVertices * 3);

        float x = radius * (float) Math.sin(startangle);
        float y = radius * (float) Math.cos(startangle);
        float tf = (float) Math.tan(theta); // precompute tangent factor: how much to move along the tangent line each iteration
        float rf = (float) Math.cos(theta); // precompute radial factor: how much to move back towards the origin each iteration
        float tx;
        float ty;

        for (int i = 0; i < numVertices; i++) {
            buffer.put(x + xoffset);
            buffer.put(y + yoffset);
            buffer.put(zoffset);
            tx = y; // compute tangent lines
            ty = -x;
            x += tx * tf; // add tangent line * tangent factor
            y += ty * tf;
            x *= rf;
            y *= rf;
        }
        buffer.flip();

        return buffer;
    }

    /**
     * Efficient algorithm for drawing circles and arcs in pure opengl!
     * Note: this version does not add z values!!!
     *
     * @param startangle Start angle in radians
     * @param endangle   End angle in radians
     * @param radius     Radius of the circle (used in calculating number of segments to draw as well as size of the arc)
     * @param xoffset    Convenience parameter, added to every vertex
     * @param yoffset    Convenience parameter, added to every vertex
     * @return
     */
    public static FloatBuffer getArcPoints(float startangle, float endangle, float radius, float xoffset, float yoffset) {
        int numVertices = (int) Math.ceil(Math.abs((endangle - startangle) * 2 * Math.PI));
        float theta = (endangle - startangle) / numVertices;
        FloatBuffer buffer = BufferUtils.createFloatBuffer(numVertices * 2);

        float x = radius * (float) Math.sin(startangle);
        float y = radius * (float) Math.cos(startangle);
        float tf = (float) Math.tan(theta); // precompute tangent factor: how much to move along the tangent line each iteration
        float rf = (float) Math.cos(theta); // precompute radial factor: how much to move back towards the origin each iteration
        float tx;
        float ty;

        for (int i = 0; i < numVertices; i++) {
            buffer.put(x + xoffset);
            buffer.put(y + yoffset);
            tx = y; // compute tangent lines
            ty = -x;
            x += tx * tf; // add tangent line * tangent factor
            y += ty * tf;
            x *= rf;
            y *= rf;
        }
        buffer.flip();

        return buffer;
    }

    /**
     * Creates a list of points linearly interpolated between points a and b noninclusive.
     *
     * @return A list of num points
     */
    public static List<MusePoint2D> pointsInLine(int num, MusePoint2D a, MusePoint2D b) {
        List<MusePoint2D> points = new ArrayList<>();
        switch (num) {
            case -1:
                break;
            case 0:
                break;
            case 1:
                points.add(b.minus(a).times(0.5F).plus(a));
                break;
            default:
                MusePoint2D step = b.minus(a).times(1.0F / (num + 1));
                for (int i = 0; i < num; i++) {
                    points.add(a.plus(step.times(i + 1)));
                }
        }
        return points;
    }

    /**
     * Creates a list of points linearly interpolated between points a and b with a min spacing parameter for x and y.
     * Caution, this means that these points may extend beyond point b. This is useful in scrollable frames where a minimum
     * spacing is required in the case where multiple rendered objects are not desired to overlap
     *
     * @return A list of num points
     */
    public static List<MusePoint2D> pointsInLine(int num, MusePoint2D a, MusePoint2D b, double minSpacingX, double minSpacingY) {
        List<MusePoint2D> points = new ArrayList<>();
        switch (num) {
            case -1:
                break;
            case 0:
                break;
            case 1:
                // midpoint
                points.add(b.minus(a).times(0.5F).plus(a));
                break;
            default:
                // distance / num spaces
                MusePoint2D step = b.minus(a).times(1.0F / (num + 1));
                if (step.x() < 0) {
                    step.setX(Math.min(-minSpacingX, step.x()));
                } else {
                    step.setX(Math.max(minSpacingX, step.x()));
                }

                if (step.y() < 0) {
                    step.setY(Math.min(-minSpacingY, step.y()));
                } else {
                    step.setY(Math.max(minSpacingY, step.y()));
                }

                for (int i = 0; i < num; i++) {
                    points.add(a.plus(step.times(i + 1)));
                }
        }
        return points;
    }

    /**
     * Returns a DoubleBuffer full of colors that are gradually interpolated
     *
     * @param c1
     * @param c2
     * @param numsegments
     * @return
     */
    public static FloatBuffer getColorGradient(Color c1, Color c2, int numsegments) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(numsegments * 4);
        if (c1.equals(c2)) {
            for (double i = 0; i < numsegments; i++) {
                buffer.put(c1.r);
                buffer.put(c1.g);
                buffer.put(c1.b);
                buffer.put(c1.a);
            }
        } else {
            for (double i = 0; i < numsegments; i++) {
                // declaring "i" as an int instead of double is what broke the swirly circle.
                Color c3 = c1.interpolate(c2, (float) (i / (double)numsegments));
                buffer.put(c3.r);
                buffer.put(c3.g);
                buffer.put(c3.b);
                buffer.put(c3.a);
            }
        }
        buffer.flip();
        return buffer;
    }
}

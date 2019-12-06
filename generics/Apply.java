/**
 * generics/Apply.java
 * {main: ApplyTest}
 * @author jiyong.me
 * @since 2019-12-03
 */

import java.lang.reflect.*;
import java.util.*;
import static net.mindview.util.Print.*;

/**
 * Define Apply
 */
public class Apply {
    /**
     *
     * @param seq   list
     * @param f     the method to call
     * @param args  params used in f
     * @param <T>   <T>
     * @param <S>
     */
    public static <T, S extends Iterable<? extends T>> void apply(S seq, Method f, Object... args) {
        try {
            for(T t: seq) {
                f.invoke(t, args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 * Define Shape
 */
class Shape {
    /**
     * Rotate
     */
    public void rotate() {
        print( this + " rotate");
    }

    /**
     * reset size
     * @param newSize
     */
    public void resize(int newSize) {
        print(this + " resize " + newSize );
    }
}

/**
 * Define Square
 */
class Square extends Shape {}

/**
 * Define a list
 * @param <T>
 */
class FilledList<T> extends ArrayList<T> {
    public FilledList(Class<? extends T> type, int size) {
        try {
            for(int i=0; i < size; i++) {
                add(type.newInstance());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 * Test Apply Class
 */
class ApplyTest {
    public static void main(String[] args) throws Exception {

        //define a Shape list
        List<Shape> shapes = new ArrayList<Shape>();
        for(int i = 0; i < 10; i++) {
            shapes.add(new Shape());
        }

        //use reflect call Shape methods
        Apply.apply(shapes, Shape.class.getMethod("rotate"));
        Apply.apply(shapes,
                Shape.class.getMethod("resize", int.class),
                5);

        //define a Square list
        List<Square> squares = new ArrayList<Square>();
        for(int i = 0; i < 10; i++) {
            squares.add(new Square());
        }

        //use reflect call Square methods
        Apply.apply(squares, Shape.class.getMethod("rotate"));
        Apply.apply(squares,
                Shape.class.getMethod("resize", int.class),
                5);

        //use FilledList generate Shape list ,and call Shape method rotate
        Apply.apply(new FilledList<Shape>(Shape.class, 10),
                Shape.class.getMethod("rotate"));
        //use FilledList generate Square list, and call Square method rotate
        Apply.apply(new FilledList<Shape>(Square.class, 10),
                Square.class.getMethod("rotate"));
        //use FilledList generate Square list, and call Square method resize
        Apply.apply(new FilledList<Shape>(Square.class, 10),
                Shape.class.getMethod("resize", int.class),
                111);

        ArrayDeque<Shape> shapeQ = new ArrayDeque<Shape>();
        for(int i = 0; i < 5; i++) {
            shapeQ.add(new Shape());
            shapeQ.add(new Square());
        }
        Apply.apply(shapeQ, Shape.class.getMethod("rotate"));

    }
}
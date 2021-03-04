package io;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.util.*;
import static net.mindview.util.Print.*;

/**
 * Demonstrates object serialization.
 *
 * @author jiyong.me
 * @date 2021/3/4
 */

class Data implements Serializable {
    private int n;

    public Data(int n) { this.n = n; }

    public String toString() { return Integer.toString(n); }
}


public class Worm implements Serializable {
    //随机数随机因子
    private static Random rand = new Random(47);

    //存的数
    private Data[] d = {
        new Data(rand.nextInt(10)),
        new Data(rand.nextInt(10)),
        new Data(rand.nextInt(10))
    };

    //链接的对象
    private Worm next;

    //链接的标记
    private char c;

    //Value of i ==  number of segments
    //递归的创建
    public Worm(int i, char x) {
        print("Worm constructor: " + i);
        c = x;
        if(--i > 0) {
            next = new Worm(i, (char)(x + 1));
        }
    }

    public Worm() {
        print("Default constructor");
    }

    //toString把属性值都表现出来 c(d)next
    //:c(123)
    public String toString() {
        StringBuilder result = new StringBuilder(":");

        //标识
        result.append(c);

        //数字值
        result.append("(");
        for(Data dat : d) {
            result.append(dat);
        }
        result.append(")");

        if(next != null) {
            result.append(next);
        }

        return result.toString();
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Worm w = new Worm(6, 'a');
        print("w = " + w);

        //文件流写入
        ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream("worm.out")
        );
        out.writeObject("Worm storage\n");
        out.writeObject(w);
        out.close();//Also flushes output

        //文件流读出来看看
        ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("worm.out")
        );
        String s = (String) in.readObject();
        Worm w2 = (Worm) in.readObject();
        print(s + "w2 = " + w2);

        //比特流写入
        ByteOutputStream bout = new ByteOutputStream();
        ObjectOutputStream out2 = new ObjectOutputStream(bout);
        out2.writeObject("Worm storage\n");
        out2.writeObject(w);
        out2.flush();

        //比特流读出来看看
        ObjectInputStream in2 = new ObjectInputStream(
            new ByteArrayInputStream(bout.toByteArray())
        );
        s = (String) in2.readObject();
        Worm w3 = (Worm) in2.readObject();
        print(s + "w3 = " + w3);
    }
}

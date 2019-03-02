package bwjava.util;

import org.junit.Test;

/**
 * @author chenjing
 * @date 2019-02-24 09:58
 */
public class SnowFlakeTest {
    @Test
    public void test10w() {
        SnowFlake snowFlake = new SnowFlake(2, 3);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            System.out.println(snowFlake.nextId());
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}
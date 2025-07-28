package com.minigod.zero.trade.utils;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class NameBasedIdGenerator {
    // 时间戳精度控制（使用纳秒级）
    private static final int TIMESTAMP_BITS = 15; // 时间戳位数（15位为毫秒级+微秒截断）
    private static final int RANDOM_BITS = 5;     // 随机数位数

    // 上一次的时间戳和序列号（用于同一时间戳内的自增）
    private static volatile long lastTimestamp = 0L;
    private static final AtomicLong sequence = new AtomicLong(0);

    public static synchronized String generateId() {
        long timestamp = getHighPrecisionTimestamp();

        // 处理同一时间戳内的并发
        if (timestamp == lastTimestamp) {
            sequence.incrementAndGet();
        } else {
            sequence.set(0);
            lastTimestamp = timestamp;
        }

        // 生成随机数并拼接
        long randomNum = ThreadLocalRandom.current().nextLong((long) Math.pow(10, RANDOM_BITS));
        String timePart = String.format("%0" + TIMESTAMP_BITS + "d", timestamp);
        String randomPart = String.format("%0" + RANDOM_BITS + "d", randomNum);

        return   timePart + randomPart;
    }

    private static long getHighPrecisionTimestamp() {
        long nanoTimestamp = Instant.now().getNano(); // 纳秒级（9位）
        long millisTimestamp = System.currentTimeMillis();
        // 合并毫秒和纳秒后3位，组成15位时间戳
        return (millisTimestamp % 1000000000000L) * 1000 + (nanoTimestamp % 1000);
    }

    // 测试
//    public static void main(String[] args) {
//        System.out.println(generateId()); // 示例输出: ORDER_16213456789012345
//    }
}

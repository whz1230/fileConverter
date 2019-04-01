package converter;

import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.UUID;

public class UUIDGenerator {
    private static SecureRandom seederStatic = null;
    private static byte[] addr = null;
    private static String midValueStatic = null;
    private String midValue = null;
    private SecureRandom seeder = null;
    private static long prevMillis = 0L;
    private static byte[] addrBytes = null;

    static {
        try {
            addr = InetAddress.getLocalHost().getAddress();
            addrBytes = InetAddress.getLocalHost().getAddress();
            StringBuffer buffer = new StringBuffer(8);
            buffer.append(toHex(toInt(addr), 8));
            midValueStatic = buffer.toString();
            seederStatic = new SecureRandom();
            seederStatic.nextInt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public UUIDGenerator() {
        StringBuffer buffer = new StringBuffer(16);
        buffer.append(midValueStatic);
        buffer.append(toHex(System.identityHashCode(this), 8));
        this.midValue = buffer.toString();
        this.seeder = new SecureRandom();
        this.seeder.nextInt();
    }

    public static String generate(Object obj) {
        StringBuffer uid = new StringBuffer(32);
        long currentTimeMillis = System.currentTimeMillis();
        uid.append(toHex((int) (currentTimeMillis & 0xFFFFFFFF), 8));
        uid.append(midValueStatic);
        uid.append(toHex(System.identityHashCode(obj), 8));
        uid.append(toHex(getRandom(), 8));
        return uid.toString();
    }

    public String generate() {
        StringBuffer uid = new StringBuffer(32);
        long currentTimeMillis = System.currentTimeMillis();
        uid.append(toHex((int) (currentTimeMillis & 0xFFFFFFFF), 8));
        uid.append(this.midValue);
        uid.append(toHex(this.seeder.nextInt(), 8));
        return uid.toString();
    }

    private static String toHex(int value, int length) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer buffer = new StringBuffer(length);
        int shift = length - 1 << 2;
        int i = -1;
        do {
            buffer.append(hexDigits[(value >> shift & 0xF)]);
            value <<= 4;
            i++;
        } while (i < length);
        return buffer.toString();
    }

    private static int toInt(byte[] bytes) {
        int value = 0;
        int i = 0;
        do {
            value <<= 8;
            value |= bytes[i];
            i++;
        } while (i < bytes.length);
        return value;
    }

    private static synchronized int getRandom() {
        return seederStatic.nextInt();
    }

    private static synchronized long getSystemTimeMillis() {
        long millis = System.currentTimeMillis();
        if (millis > prevMillis) {
            prevMillis = millis;
        } else {
            prevMillis += 1L;
        }
        return prevMillis;
    }

    public static Long getUniqueLong() {
        long l = getSystemTimeMillis();
        l *= 1000L;
        long b1 = addrBytes[3] & 0xFF;
        l += b1;
        return Long.valueOf(l);
    }

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        StringBuilder sb = new StringBuilder(32);
        sb.append(uuid.substring(0, 8));
        sb.append(uuid.substring(9, 13));
        sb.append(uuid.substring(14, 18));
        sb.append(uuid.substring(19, 23));
        sb.append(uuid.substring(24));
        return sb.toString().toUpperCase();
    }

}
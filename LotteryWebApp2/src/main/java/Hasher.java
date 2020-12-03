import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    private static String hashString(String value, String algorithm) throws Exception {
        try{
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            // get array of bytes from string `value`
            byte[] hashed = digest.digest(value.getBytes(StandardCharsets.UTF_8));

            // pass `hashed` bytes array to `toHexString` method
            return toHexString(hashed);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    private static String toHexString(byte[] arrayOfBytes) {
        StringBuffer buffer = new StringBuffer();

        for (int i=0; i<arrayOfBytes.length; i++){
            // converting each number to hexadecimal representation and adding it to buffer
            buffer.append(Integer.toString((arrayOfBytes[i] & 0xff) + 0x100, 16)).substring(1);
        }
        return buffer.toString();
    }

    public static String generateSHA2(String value) throws Exception {
        // use SHA-256 hashing algorithm in `hashString` method
        return hashString(value, "SHA-256");
    }
}

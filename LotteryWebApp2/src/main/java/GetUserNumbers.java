import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/GetUserNumbers")
public class GetUserNumbers extends HttpServlet {

    private KeyPair pair;
    private Cipher cipher;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        pair = (KeyPair) session.getAttribute("keypair");

        String hashedPassword = (String) session.getAttribute("uHashedPassword");
        String shortenedPassword = hashedPassword.substring(0, 20);

        List<byte[]> bytesArr = bytesReader(shortenedPassword);

        String[] draws = decryptData(bytesArr, pair);
        System.out.println(Arrays.toString(draws));

        request.getSession().setAttribute("draws", draws);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
        request.setAttribute("message", "Here are your previous draws");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private String[] decryptData(List<byte[]> data, KeyPair pair) {
        String[] draws = new String[data.size()];
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
            for (int i = 0; i < data.size(); i++){
                byte[] encryptedText = data.get(i);
                byte[] decipheredText = cipher.doFinal(encryptedText);
                draws[i] = new String(decipheredText, "UTF-8");
            }
            return draws;
        }
        catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException |
                NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<byte[]> bytesReader(String filename) throws IOException {
        // CHANGE TO YOUR ABSOLUTE PATH!
        String path = String.format("/main/Temp/%s.txt", filename);

        // List with results, ready to pass to decryptData() method
        List<byte[]> numbers = new ArrayList();

        byte[] arr = Files.readAllBytes(Paths.get(path));

        int cntBeginning = 0;
        int cntEnd = 256;
        int NUMS_ONE_COMBINATION = 256;
        for (int i = 0; i < arr.length/ NUMS_ONE_COMBINATION; i++){
            numbers.add(Arrays.copyOfRange(arr, cntBeginning, cntEnd));
            cntBeginning = cntBeginning + 256;
            cntEnd = cntEnd + 256;
        }
        return numbers;
       }
}


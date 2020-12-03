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
import java.io.*;
import java.security.*;


@WebServlet("/AddUserNumbers")
public class AddUserNumbers extends HttpServlet {
    private KeyPair pair;

    private static Cipher cipher;
    File file;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");
    // array of digits inputted by a client
    String[] arr = request.getParameterValues("digit");
    String allDigits = String.join(",", arr);


        try {
            HttpSession session = request.getSession();
            if (null == session.getAttribute("keypair")){
                pair = keyGenerator("RSA");
                request.getSession().setAttribute("keypair", pair);
            }
            String hashedPassword = (String) session.getAttribute("uHashedPassword");
            String filename = hashedPassword.substring(0, 20);

            byte[] scrambledArr = encryptData(allDigits);
            fileWriter(filename, scrambledArr);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/account.jsp");
            request.setAttribute("message", "You have successfully submitted the numbers!");
            requestDispatcher.forward(request, response);

        } catch (BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


        /**
         * Encrypts data by generating 256 bits key
         * @data
         */
    private byte[] encryptData(String data) throws BadPaddingException, IllegalBlockSizeException {
        try {
            PublicKey publicKey = pair.getPublic();
            // Transformation string describing operations inside getInstance
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // Setting cipher to encryption mode, passing keys
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // Converting data to array of bytes and passing it to cipher
            cipher.update(data.getBytes());

            // Generates 256 bits key
            return cipher.doFinal();

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void fileWriter(String filename, byte[] content) throws IOException {

        // CHANGE TO YOUR ABSOLUTE PATH
        String tempLocation = "./main/Temp/";
        file = new File(tempLocation + filename + ".txt");

        try{
            FileOutputStream out = new FileOutputStream(file, true);
            out.write(content);
            out.flush();
            out.close();

        } catch (IOException e){
            e.printStackTrace();
        }
        file.deleteOnExit();
    }

    private KeyPair keyGenerator(String algorithm){
        try {
            // Generate key using RSA encryption algorithm
            KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance(algorithm);

            return pairGenerator.generateKeyPair();

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }

}

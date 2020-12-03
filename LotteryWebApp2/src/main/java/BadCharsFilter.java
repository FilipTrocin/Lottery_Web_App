import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@WebFilter("/BadCharsFilter")
public class BadCharsFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        boolean invalid = false;
        Map params = req.getParameterMap();

        if (params != null){
            // iterate through keys in a map
            Iterator iter = params.keySet().iterator();
            while (iter.hasNext()){
                String key = (String) iter.next();
                String[] values = (String[]) params.get(key);

                for(int i=0; i < values.length; i++){
                    // if one of the characters from a values array is invalid then break
                    if(checker(values[i])){
                        invalid = true;
                        break;
                    }
                }
                if (invalid) {break;}
            }
        }
        if(invalid){
            try{
                // display the following message if invalid character was found
                req.setAttribute("message", "An error occurred while processing your request");
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else{
            // if none of the invalid characters were found display `success`
            req.setAttribute("message", "success");
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public static boolean checker(String value) {
        boolean invalid = false;
        String[] forbidden = {"<", ">", "!", "{", "}", "insert", "into", "where", "script", "delete", "input"};

        for (int i = 0; i < forbidden.length; i++) {
            if (value.contains(forbidden[i])) {
                invalid = true;
                break;
            }
        }
        return invalid;

    }
}

package servlets;

import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AllRequestsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = createPageVariablesMap(request);
        System.out.println(request.getContextPath());
        //String s = "";//Files.walk(request.getContextPath()).findAny().get().getFileName().toString();
        String s = "";
        File dir = new File("./templates/images/");
        File[] images = dir.listFiles();
        Random rand = new Random();
        int position = 0;
        if(images.length!=0){
            while(position==0){
                position = rand.nextInt(images.length);
            }
        }
        File image = images[position];
        int size = (int) Files.lines(Paths.get("./templates/Quotes.txt")).count();
        pageVariables.put("image", dir + File.separator + image.getName());
        pageVariables.put("number",
                Files.lines(Paths.get("./templates/Quotes.txt"))
                .toArray()[rand.nextInt(size)]);
        //response.getWriter().println(request.getParameter("key"));
        response.getWriter().println(PageGenerator.instance().getPage("index.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request);

        //String message = request.getParameter("message");

        response.setContentType("text/html;charset=utf-8");

        /*if (message == null || message.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }*/
        response.setStatus(HttpServletResponse.SC_OK);

        //pageVariables.put("message", message == null ? "" : message);

        response.getWriter().println(PageGenerator.instance().getPage("index.html", pageVariables));
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("pathInfo", request.getPathInfo());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());
        return pageVariables;
    }
}

package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import servlets.AllRequestsServlet;

public class Main {
    public static void main(String[] args) throws Exception {


        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();


        String warUrlString = "templates";
        WebAppContext wac = new WebAppContext();
        wac.setResourceBase(warUrlString);
        wac.setContextPath("/templates");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase(warUrlString);
        context.addServlet(new ServletHolder(allRequestsServlet), "/*");
        Server server = new Server(8080);
        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{wac, context});
        server.setHandler(handlerList);
        //server.setHandler(context);
        server.start();
        System.out.println("Server started");
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();
    }
}

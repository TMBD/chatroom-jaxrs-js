package chatroom;

import utils.CorsFilter;

import java.lang.System.Logger.Level;
import java.net.URI;

import javax.inject.Singleton;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class ChatRoomLauncher {
	public static final URI BASE_URI = getBaseURI();

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/api/").port(9991).build();
    }

    public static void main(String[] args) {
        ResourceConfig rc = new ResourceConfig();
        rc.register(ChatRoomResource.class);
        rc.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, Level.WARNING.getName());
        rc.register(new CorsFilter());
        rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(ChatRoom.class)
                .in(Singleton.class);
            }
        });
        
        
        try {
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
            server.start();

            System.out.println(String.format(
                    "Chatroom server started successfully. \nWADL is available at " + "%sapplication.wadl\nHit enter to stop it...",
                    BASE_URI, BASE_URI));

            System.in.read();
            server.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

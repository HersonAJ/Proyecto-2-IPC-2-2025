package com.mycompany.salondebelleza;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */

/*
@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {
    
}
*/

@ApplicationPath("resources")
public class JakartaRestConfiguration extends ResourceConfig {
    
    public JakartaRestConfiguration() {
        packages("com.mycompany.salondebelleza", "Controllers").register(MultiPartFeature.class);
        register(CORSFilter.class);
    }
    
}

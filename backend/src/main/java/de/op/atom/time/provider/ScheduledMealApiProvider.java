package de.op.atom.time.provider;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.op.atom.gen.api.NotFoundException;

@ApplicationScoped
public class ScheduledMealApiProvider {

    public Response getScheduledMeal(SecurityContext securityContext) throws NotFoundException {
        // TODO Auto-generated method stub
        return null;
    }
}

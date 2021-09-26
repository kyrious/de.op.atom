package de.op.atom.time;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.op.atom.gen.api.NotFoundException;
import de.op.atom.gen.api.ScheduleApiService;
import de.op.atom.time.provider.ScheduledMealApiProvider;

@ApplicationScoped
public class ScheduledApiProviderImpl implements ScheduleApiService {

    @Inject 
    ScheduledMealApiProvider scheduledMealProvider;
    
    @Override
    public Response getScheduledMeal(SecurityContext securityContext) throws NotFoundException {
        return scheduledMealProvider.getScheduledMeal(securityContext);
    }

}

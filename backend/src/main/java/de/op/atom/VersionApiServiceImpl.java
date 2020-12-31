package de.op.atom;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.op.atom.gen.api.NotFoundException;
import de.op.atom.gen.api.VersionApiService;

@ApplicationScoped
public class VersionApiServiceImpl implements VersionApiService {

    @Override
    public Response getVersion(SecurityContext securityContext) throws NotFoundException {
        return Response.ok("0.0.1")
                       .build();
    }

}

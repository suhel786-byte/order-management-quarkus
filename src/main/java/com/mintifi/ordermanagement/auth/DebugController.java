package com.mintifi.ordermanagement.auth;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/v1/debug")
@RolesAllowed("ADMIN")
public class DebugController {

    @Inject
    SecurityIdentity identity;

    @GET
    public String debug() {

        return "User=" + identity.getPrincipal().getName()
                + ", Roles=" + identity.getRoles();
    }
}
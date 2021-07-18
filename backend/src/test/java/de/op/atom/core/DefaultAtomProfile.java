package de.op.atom.core;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.ApplicationPath;

import de.op.atom.gen.inv.RestApplication;
import io.quarkus.test.junit.QuarkusTestProfile;

public class DefaultAtomProfile implements QuarkusTestProfile {
    @Override
    public String getConfigProfile() {
        return "DefaulAtomProfile";
    }

    @Override
    public Map<String, String> getConfigOverrides() {
        return Collections.singletonMap("quarkus.resteasy.path",
                                        RestApplication.class.getAnnotation(ApplicationPath.class)
                                                             .value());
    }
}

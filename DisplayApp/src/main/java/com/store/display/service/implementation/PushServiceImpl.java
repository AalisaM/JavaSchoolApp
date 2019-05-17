package com.store.display.service.implementation;

import com.store.display.service.PushService;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Service responsible for reloading front
 */
@Named
@ApplicationScoped
public class PushServiceImpl extends PushService implements Serializable {

    @Inject
    @Push(channel = "clock")
    private PushContext push;

    /**
     * sends message to front
     */
    @Override
    public void reload() {
        push.send("reload");
    }
}

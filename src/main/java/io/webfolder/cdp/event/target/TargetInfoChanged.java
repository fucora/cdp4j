package io.webfolder.cdp.event.target;

import com.vimeo.stag.UseStag;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.type.target.TargetInfo;

/**
 * Issued when some information about a target has changed
 * This only happens between
 * <code>targetCreated</code> and <code>targetDestroyed</code>
 */
@Domain("Target")
@EventName("targetInfoChanged")
@UseStag
public class TargetInfoChanged {
    private TargetInfo targetInfo;

    public TargetInfo getTargetInfo() {
        return targetInfo;
    }

    public void setTargetInfo(TargetInfo targetInfo) {
        this.targetInfo = targetInfo;
    }
}

package io.webfolder.cdp.event.target;

import com.vimeo.stag.UseStag;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.type.target.TargetInfo;

/**
 * Issued when a possible inspection target is created
 */
@Domain("Target")
@EventName("targetCreated")
@UseStag
public class TargetCreated {
    private TargetInfo targetInfo;

    public TargetInfo getTargetInfo() {
        return targetInfo;
    }

    public void setTargetInfo(TargetInfo targetInfo) {
        this.targetInfo = targetInfo;
    }
}

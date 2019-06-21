package io.webfolder.cdp.type.constant;

import com.google.gson.annotations.SerializedName;

public enum PdfTransferMode {

    @SerializedName("ReturnAsBase64")
    ReturnAsBase64("ReturnAsBase64"),

    @SerializedName("ReturnAsStream")
    ReturnAsStream("ReturnAsStream");

    public final String value;

    PdfTransferMode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

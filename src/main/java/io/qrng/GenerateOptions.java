package io.qrng;

public class GenerateOptions {
    private int bytes = 32;
    private String format = "hex";
    private String method;
    private String signatureType;

    public int getBytes() { return bytes; }
    public void setBytes(int bytes) { this.bytes = bytes; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getSignatureType() { return signatureType; }
    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }
}

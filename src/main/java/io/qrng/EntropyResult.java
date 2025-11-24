package io.qrng;

import java.util.Map;

public class EntropyResult {
    private String data;
    private String proofId;
    private String signature;
    private String publicKey;
    private String signatureType;
    private Map<String, Object> metadata;

    public String getData() { return data; }
    public String getProofId() { return proofId; }
    public String getSignature() { return signature; }
    public String getPublicKey() { return publicKey; }
    public String getSignatureType() { return signatureType; }
    public Map<String, Object> getMetadata() { return metadata; }
}

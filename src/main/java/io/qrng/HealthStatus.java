package io.qrng;

import java.util.Map;

public class HealthStatus {
    private String status;
    private Map<String, Object> metrics;
    private String timestamp;

    public String getStatus() { return status; }
    public Map<String, Object> getMetrics() { return metrics; }
    public String getTimestamp() { return timestamp; }
}

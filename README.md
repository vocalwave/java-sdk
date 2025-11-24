# QRNG API - Java SDK

Official Java client library for the QRNG API.

## Installation

### Maven

```xml
<dependency>
    <groupId>io.qrng</groupId>
    <artifactId>qrng-api-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'io.qrng:qrng-api-sdk:1.0.0'
```

## Quick Start

```java
import io.qrng.*;

public class Example {
    public static void main(String[] args) {
        QRNGClient client = new QRNGClient("your-api-key");
        
        try {
            GenerateOptions options = new GenerateOptions();
            options.setBytes(32);
            options.setFormat("hex");
            
            EntropyResult result = client.generate(options);
            System.out.println("Random data: " + result.getData());
            System.out.println("Proof ID: " + result.getProofId());
        } catch (QRNGException e) {
            e.printStackTrace();
        }
    }
}
```

## Usage

### Basic Generation

```java
QRNGClient client = new QRNGClient("your-api-key");

GenerateOptions options = new GenerateOptions();
options.setBytes(32);
options.setFormat("hex");

EntropyResult result = client.generate(options);
System.out.println(result.getData());
```

### Quantum Methods

```java
GenerateOptions options = new GenerateOptions();
options.setBytes(32);
options.setFormat("hex");
options.setMethod("photon"); // photon, tunneling, vacuum, simulator

EntropyResult result = client.generate(options);
```

### Post-Quantum Signatures

```java
// Pro tier: Dilithium2
GenerateOptions options = new GenerateOptions();
options.setBytes(32);
options.setSignatureType("dilithium2");

EntropyResult result = client.generate(options);

// Enterprise tier: Dilithium3/5
options.setSignatureType("dilithium3");
```

### Health Check

```java
HealthStatus health = client.health();
System.out.println("Status: " + health.getStatus());
```

### Error Handling

```java
try {
    EntropyResult result = client.generate(new GenerateOptions());
} catch (AuthenticationException e) {
    System.err.println("Invalid API key");
} catch (RateLimitException e) {
    System.err.println("Rate limit exceeded");
} catch (QuotaExceededException e) {
    System.err.println("Quota exceeded");
} catch (QRNGException e) {
    System.err.println("Error: " + e.getMessage());
}
```

## Requirements

- Java 11 or higher

## License

MIT

## Support

- Documentation: https://qrngapi.com/docs
- GitHub: https://github.com/qrng-api/java-sdk
- Email: support@qrngapi.com

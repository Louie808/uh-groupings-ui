package edu.hawaii.its.groupings.exceptions;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.diagnostics.FailureAnalysis;

import static org.hamcrest.Matchers.startsWith;

public class ApiServerHandshakeAnalyzerTest {

    @Test
    public void construction() {
        ApiServerHandshakeAnalyzer analyzer = new ApiServerHandshakeAnalyzer();
        Assertions.assertNotNull(analyzer);

        FailureAnalysis fa = analyzer.analyze(null, null);
        Assertions.assertNotNull(fa);
        MatcherAssert.assertThat(fa.getDescription(), startsWith("Could not connect to the API server"));
        MatcherAssert.assertThat(fa.getAction(), startsWith("Start the UH Groupings API"));
    }
}

package platform.ads.trace;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import platform.ads.trace.logtrace.LogTrace;
import platform.ads.trace.logtrace.ThreadLocalLogTrace;

@Configuration
public class LogTraceConfig {

    public LogTraceConfig() {
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

}

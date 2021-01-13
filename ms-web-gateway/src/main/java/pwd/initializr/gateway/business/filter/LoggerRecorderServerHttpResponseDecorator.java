package pwd.initializr.gateway.business.filter;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * pwd.initializr.gateway.business.filter@ms-web-gateway
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2021-01-13 17:53
 *
 * @author DingPengwei[dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public class LoggerRecorderServerHttpResponseDecorator extends ServerHttpResponseDecorator {
    private LoggerDataBufferWrapper data = null;

    public LoggerRecorderServerHttpResponseDecorator(ServerHttpResponse delegate) {
        super(delegate);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        return LoggerDataBufferUtilFix.join(Flux.from(body))
            .doOnNext(d -> this.data = d)
            .flatMap(d -> super.writeWith(copy()));
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return writeWith(Flux.from(body)
            .flatMapSequential(p -> p));
    }

    public Flux<DataBuffer> copy() {
        //如果data为null 就出错了 正好可以调试
        DataBuffer buffer = this.data.newDataBuffer();
        if (buffer == null){
            return Flux.empty();

        }

        return Flux.just(buffer);
    }
}

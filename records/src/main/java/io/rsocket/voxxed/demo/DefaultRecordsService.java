package io.rsocket.voxxed.demo;

import java.util.concurrent.ThreadLocalRandom;

import io.netty.buffer.ByteBuf;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultRecordsService implements RecordsService {
  @Autowired
  private final RecordRepository recordRepository;

  private final Mono<Integer> total;

  public DefaultRecordsService(@Autowired RecordRepository recordRepository) {
    this.recordRepository = recordRepository;
    this.total = recordRepository.count()
        .map(Long::intValue)
        .cache();
  }

  @Override
  public Flux<Record> records(RecordsRequest request, ByteBuf metadata) {
    return total.log()
        .flatMapMany(c ->
            this.recordRepository.findAllByThumbnailNotNull(offset(request, c), request.getMaxResults())
                .map(DbRecord::toRecord));
  }

  private int offset(RecordsRequest req, int count) {
    int max = count - req.getMaxResults();
    return ThreadLocalRandom.current().nextInt(((max - req.getOffset()) + 1) + req.getOffset());
  }
}

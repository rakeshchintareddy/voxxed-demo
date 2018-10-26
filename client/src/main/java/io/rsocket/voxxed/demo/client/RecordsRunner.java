package io.rsocket.voxxed.demo.client;

import com.google.protobuf.util.JsonFormat;
import io.netifi.proteus.spring.core.annotation.Group;
import io.rsocket.voxxed.demo.Record;
import io.rsocket.voxxed.demo.RecordsRequest;
import io.rsocket.voxxed.demo.RecordsServiceClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RecordsRunner implements CommandLineRunner {
  private static final Logger logger = LogManager.getLogger(RecordsRunner.class);

  @Group("voxxed.demo.records")
  private RecordsServiceClient recordsService;

  @Override
  public void run(String... args) throws Exception {
    RecordsRequest request = RecordsRequest.newBuilder().setMaxResults(256).build();
    for (Record record: recordsService.records(request).toIterable()) {
      logger.info(JsonFormat.printer().print(record));
    }
  }
}

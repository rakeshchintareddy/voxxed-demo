package io.rsocket.voxxed.demo.client;

import com.google.protobuf.util.JsonFormat;
import io.netifi.proteus.spring.core.annotation.Group;
import io.rsocket.voxxed.demo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

//@Component
public class TournamentRunner implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(TournamentRunner.class);

    @Group("voxxed.demo.tournament")
    private TournamentServiceClient tournamentService;

    @Override
    public void run(String... args) throws Exception {
        RecordsRequest request = RecordsRequest.newBuilder().setMaxResults(40).build();

        Flux<RoundResult> round1 = tournamentService.tournament(request);

        for (RoundResult record: round1.toIterable()) {

            if (record.getRound() == 1){
            	logger.info(
            			"\n=----------------------------------------------------------="+
					            "\n< @_@ > SUPER WINNER < @_@ >  ===> "+record.getWinner().getSuperName()+
					            "\n=----------------------------------------------------------=");
            }
            else {
	            logger.info(JsonFormat.printer().print(record));
            }
        }
    }
}

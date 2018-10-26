package io.rsocket.voxxed.demo;

import org.springframework.data.r2dbc.repository.query.Query;
import reactor.core.publisher.Flux;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RecordRepository extends ReactiveCrudRepository<DbRecord, Long> {


	@Query("SELECT * FROM records WHERE thumbnail is not null" +
                " ORDER BY id OFFSET $1" +
                " LIMIT $2")
	Flux<DbRecord> findAllByThumbnailNotNull(int offset, int limit);
}

package com.atb.ninja.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.atb.ninja.models.documents.FluxData;


public interface BlockDao extends ReactiveMongoRepository<FluxData, String> {

}

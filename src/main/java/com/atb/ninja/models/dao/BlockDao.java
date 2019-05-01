package com.atb.ninja.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.atb.ninja.models.documents.FluxData;


public interface BlockDao extends ReactiveMongoRepository<FluxData, String> {

}

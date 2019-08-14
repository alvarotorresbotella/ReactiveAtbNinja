package com.atb.ninja;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.atb.ninja.models.dao.BlockDao;
import com.atb.ninja.models.documents.FluxData;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactorAtbApplication implements CommandLineRunner{
	
	private static final Logger log=LoggerFactory.getLogger(ReactorAtbApplication.class);

	@Autowired
	private BlockDao dao;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(ReactorAtbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		//Borramos el contenino de la collection 
		mongoTemplate.dropCollection("fluxdata").subscribe();
		
		//Creación de flujo mediante el método just
		Flux<FluxData> fluxDataFromJust=Flux.just(
				 new FluxData("DataFromFluxJust1"),
				 new FluxData("DataFromFluxJust2"),
				 new FluxData("DataFromFluxJust3")
				  );
		
		//Creación de flujo a partir de un iterable
		List<FluxData> listDatas=new ArrayList<>();
        listDatas.add(new FluxData("DataFromFluxList1"));
        listDatas.add(new FluxData("DataFromFluxList2"));
        listDatas.add(new FluxData("DataFromFluxList3")); 
		Flux<FluxData> fluxDataFromList=Flux.fromIterable(listDatas);
		
		//Creación de flujo a partir de un stream
		Flux<FluxData> fluxDataFromStream=Flux.fromStream(
				Arrays.asList(new FluxData("DataFromStream1"),
							  new FluxData("DataFromStream2"),
							  new FluxData("DataFromStream3"))
				.stream()
				.filter(flux -> !flux.getData().equals("DataFromStream2"))
				);
		
		//Creación de un flujo a partir de un Flux<String>
		Flux<FluxData> fluxDataFromStringFlux=
				Flux.just("DataFromStringFlux1","DataFromStringFlux2","DataFromStringFlux3")
				.filter(flux -> flux.equals("DataFromStringFlux2"))
				.map(flux -> new FluxData(flux));
		
		//Merge de todos los flujos
		Flux<FluxData> mergeFlux=Flux.merge(fluxDataFromJust,fluxDataFromList,fluxDataFromStream,fluxDataFromStringFlux)		
				  //filtrado del flujo resultante , se omiten los bloques que finalizan en 1 
				  .filter(flux -> !flux.getData().endsWith("1"))
				  //ordenando el flujo por el @Id de manera natural
				  .sort(Comparator.comparing(flux -> flux.getData()))
				  //transformación para persistir en Mongo reactive
				  .flatMap(flux ->dao.save(flux));

		//Subscripción del flujo con implementación loging ok,error y runnable		 
		mergeFlux.subscribe( block -> log.info(block.toString()),
				               error -> log.error(error.getMessage()),
				               new Runnable() {public void run() {
											log.info("Flux merged, inyected in mongo and susribed....");
										}
									}
				 ); 				
	}
}

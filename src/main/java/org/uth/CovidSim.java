package org.uth;

import java.util.Arrays;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/covid")
public class CovidSim 
{
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String runSim( @QueryParam("population") int population,
                        @QueryParam("infected") int infected,
                        @QueryParam("r") float r,
                        @QueryParam("deathrate") int deathrate,
                        @QueryParam("recoveryrate") int recoveryrate,
                        @QueryParam("generations") int generations )
  {
    int currentPopulation = population;
    int currentGeneration = 0;
    int currentInfected = infected;
    int currentRecovered = 0;
    int totalDeaths = 0;
    boolean stable = false;

    StringBuilder report = new StringBuilder();

    long start = System.currentTimeMillis();

    report.append( "Params:\n");
    report.append( "Population: " + population + "\n");
    report.append( "Infected: " + infected + "\n");
    report.append( "r: " + r + "\n");
    report.append( "Deathrate: " + deathrate + "\n");
    report.append( "RecoveryRate: " + recoveryrate + "\n");
    report.append( "Generations: " + generations + "\n");
    report.append( "-----------------------------------\n");

    while( currentPopulation != 0 && currentGeneration < generations && !stable )
    {
      // Perform generation:
      // 1: Apply deathrate to infected
      // 2: Apply recovery rate to infected
      // 3: Apply R to non-infected and non-recovered
      
      // 1: Apply deathrate to infected
      int expired = Math.round( (float)( ( currentInfected / 100 ) * deathrate ) );
     
      currentInfected -= expired;
      currentPopulation -= expired;
      totalDeaths += expired;

      if( currentInfected < 0 ) currentInfected = 0;
      if( currentPopulation < 0 ) currentPopulation = 0;

      // 2: Apply recovery rate to infected
      int recovered = Math.round( (float)( ( currentInfected /100 ) * recoveryrate ) );
      currentInfected -= recovered;
      currentRecovered += recovered;

      if( currentInfected < 0 ) currentInfected = 0;

      // 3: Apply R to non-infected and non-recovered
      int possibleInfectees = ( currentPopulation - currentInfected) - currentRecovered;
      int newInfections = Math.round( (float)currentInfected * r);

      // Sanity check
      if( currentPopulation - currentInfected == currentRecovered )
      {
        newInfections = 0;
        //stable = true;
      }

      currentInfected += ( newInfections > possibleInfectees ? possibleInfectees : newInfections );

      report.append( "Generation: " + currentGeneration + 
                     " Deaths: " + expired +
                     " TotDeaths: " + totalDeaths + 
                     " Rcvd: " + recovered + 
                     " TotRcvd: " + currentRecovered + 
                     " NewInf: " + newInfections + 
                     " CurrInf: " + currentInfected + 
                     " CurrPop: " + currentPopulation + "\n");

      currentGeneration++;
      //stable = ( currentRecovered + totalDeaths + currentInfected == population );
    }

    long end = System.currentTimeMillis();

    report.append( " Simulation running via Quarkus took " + ( end - start ) + "ms.\n");
    System.out.println( "Simulation completed in " + ( end - start ) + "ms.");
    
    return report.toString();
  }
}

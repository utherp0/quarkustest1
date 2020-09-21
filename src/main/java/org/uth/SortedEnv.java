package org.uth;

import java.util.Arrays;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/envs")
public class SortedEnv 
{
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String sortedEnvs()
  {
    long start = System.currentTimeMillis();

    Map<String,String> envs = System.getenv();
    String[] sortedList = new String[envs.keySet().size()];

    int pos = 0;
    for( String envName : envs.keySet())
    {
      sortedList[pos] = envName;
      pos++;
    }

    Arrays.parallelSort(sortedList);

    StringBuilder working = new StringBuilder();

    for( int loop = 0; loop < sortedList.length; loop++ )
    {
      working.append( sortedList[loop].toUpperCase() + ":" + System.getenv(sortedList[loop]) + "\n" );     
    }

    working.append( "Time Elapsed: " + ( System.currentTimeMillis() - start) + "ms.");

    return working.toString();
  }
  
}

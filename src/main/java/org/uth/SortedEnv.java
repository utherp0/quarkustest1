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
    final long start = System.currentTimeMillis();

    final Map<String, String> envs = System.getenv();
    final String[] sortedList = new String[envs.keySet().size()];

    int pos = 0;
    for (final String envName : envs.keySet()) {
      sortedList[pos] = envName;
      pos++;
    }

    Arrays.parallelSort(sortedList);

    final StringBuilder working = new StringBuilder();

    for( int loop = 0; loop < sortedList.length; loop++ )
    {
      working.append( sortedList[loop].toUpperCase() + ":" + System.getenv(sortedList[loop]) + "\n" );     
    }

    working.append( "Time Elapsed: " + ( System.currentTimeMillis() - start) + "ms.");

    return working.toString();
  }
  
}

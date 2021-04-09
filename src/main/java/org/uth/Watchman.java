package org.uth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.*;

public class Watchman 
{
  private List<String> _games = new ArrayList<>();
  private int _matchStarts = 0;
  private int _matchEnds = 0;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/watch")
  public String envtest( @QueryParam("payload") String payload ) 
  {
    System.out.println( payload );
    return "Received...";
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/reset")
  public String resetCounts()
  {
    _games = new ArrayList<>();
    _matchStarts = 0;
    _matchEnds = 0;
    return "Reset...";
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/counts")
  public String getCounts()
  {
    return "Starts: " + _matchStarts + " Ends: " + _matchEnds;
  }
}

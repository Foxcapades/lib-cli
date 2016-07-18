package io.vulpine.util.cli;

public class CliFlag extends CliArgument < Void >
{
  public CliFlag ( char shortKey, String longName, String description )
  {
    super(shortKey, longName, description, null, Param.NONE);
  }

  @Override
  public void parseParam ( String p ) throws IllegalArgumentException
  {
  }
}

package io.vulpine.util.cli;

public class CliFlag extends CliArgument < Void >
{
  public CliFlag ( final char shortKey, final String longName, final String description, final boolean required )
  {
    super(shortKey, longName, description, required, null);
  }

  @Override
  public void parseParam ( String p ) throws IllegalArgumentException
  {
  }
}

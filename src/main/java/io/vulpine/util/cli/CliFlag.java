package io.vulpine.util.cli;

public class CliFlag extends CliArgument < Void >
{
  public CliFlag ( char key, String name, String desc ) { super(key, name, desc, null); }

  public CliFlag ( char kay, String desc )              { super(kay, desc, null); }

  public CliFlag ( String name, String desc )           { super(name, desc, null); }
}

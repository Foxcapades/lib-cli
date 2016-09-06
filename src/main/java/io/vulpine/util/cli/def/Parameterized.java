package io.vulpine.util.cli.def;

public interface Parameterized
{
  boolean hasUnfilledParameter();

  void parseParameter( final String parameterValue );
}

package io.vulpine.util.cli;

import io.vulpine.util.cli.def.CliArgumentInterface;
import io.vulpine.util.cli.def.CliModeInterface;
import io.vulpine.util.cli.def.CliParameterInterface;
import io.vulpine.util.cli.def.HelpInterface;

import java.util.*;
import java.util.Map.Entry;

public class MultiApplication extends CliApplication
{

  protected final Map < String, CliModeInterface > modes;

  public MultiApplication ( final String[] args )
  {
    super(args);
    modes = new HashMap < String, CliModeInterface >();
  }

  public MultiApplication addAppMode ( final CliModeInterface... c )
  {
    for ( final CliModeInterface m : c ) { modes.put(m.getName(), m); }

    return this;
  }

  @Override
  public MultiApplication addParameter ( final CliParameterInterface... def )
  {
    for ( final CliParameterInterface p : def ) { parameters.offer(p); }

    return this;
  }

  @Override
  public void run ()
  {
    final CliModeInterface                                  mode;
    final Queue < String >                                  params;
    final Iterator < Entry < String, List < String > > >    nvIt;
    final Iterator < Entry < Character, List < String > > > kvIt;
    final Iterator < String >                               nfIt;
    final Iterator < Character >                            kfIt;

    mode = modes.get(parser.getCliMode());

    if ( mode == null && parser.hasFlag("help")) {
      if (helpFlag.wasUsed()) {
        for ( final String l : getHelpText() ) {
          System.out.println(l);
        }
        return;
      }
      System.out.println("Unrecognized application mode.");
      System.exit(1);
    }

    nvIt = parser.getArgumentsByName().entrySet().iterator();
    while ( nvIt.hasNext() ) {
      final Entry < String, List < String > > e = nvIt.next();
      testArg(e.getKey(), e.getValue(), mode);
    }

    kvIt = parser.getArgumentsByKey().entrySet().iterator();
    while ( kvIt.hasNext() ) {
      final Entry < Character, List < String > > e = kvIt.next();
      testArg(e.getKey(), e.getValue(), mode);
    }

    nfIt = parser.getFlagsByName().iterator();
    while ( nfIt.hasNext() ) {
      testArg(nfIt.next(), mode);
    }

    kfIt = parser.getFlagsByKey().iterator();
    while ( kfIt.hasNext() ) {
      testArg(kfIt.next(), mode);
    }

    params = parser.getParameters();

    while ( !params.isEmpty() ) {
      mode.parseParam(params.poll());
    }

    for ( final CliArgumentInterface a : arguments.getArguments() ) {
      if ( a.isRequired() && !a.wasUsed() ) {
        System.out.println(String.format("Argument %s|%s is required.", a.getKey(), a.getName()));
        System.exit(1);
      }
    }

    for ( final CliArgumentInterface a : mode.getArgumentSet().getArguments() ) {
      if ( a.isRequired() && !a.wasUsed() ) {
        System.out.println(String.format("Argument %s|%s is required.", a.getKey(), a.getName()));
        System.exit(1);
      }
    }

    if ( helpFlag.wasUsed() ) {
      for ( final String l : getHelpText() ) { System.out.println(l); }
    } else {
      mode.run(parser);
    }

  }

  @Override
  public String[] getHelpText ()
  {
    final Queue < String > lines = new LinkedList < String >();
    final String[]         out;

    for ( final CliModeInterface entry : this.modes.values() ) {
      lines.offer(entry.getName());
      lines.offer(HelpInterface.INDENT + entry.getDescription());
      for ( final String help : entry.getHelpText() ) {
        lines.offer(HelpInterface.INDENT + help);
      }
      lines.offer("");
    }

    out = new String[lines.size()];
    for ( int i = 0; i < out.length; i++ ) { out[i] = lines.poll(); }

    return out;
  }

  private void testArg ( final String e, final CliModeInterface mode )
  {
    final CliArgumentInterface a = arguments.getArgument(e);
    testFlag(null == a ? mode.getArgumentSet().getArgument(e) : a, e);
  }

  private void testArg ( final char e, final CliModeInterface mode )
  {
    final CliArgumentInterface a = arguments.getArgument(e);
    testFlag(null == a ? mode.getArgumentSet().getArgument(e) : a, e);
  }

  private void testArg ( final String name, final List < String > values, final CliModeInterface mode )
  {
    final CliArgumentInterface a = arguments.getArgument(name);
    nullCheckArg(null == a ? mode.getArgumentSet().getArgument(name) : a, name, values);
  }

  private void testArg ( final char key, final List < String > values, final CliModeInterface mode )
  {
    final CliArgumentInterface a = arguments.getArgument(key);
    nullCheckArg(null == a ? mode.getArgumentSet().getArgument(key) : a, key, values);
  }

  private void testFlag ( final CliArgumentInterface a, final Object e )
  {
    if ( null == a ) {
      System.out.println("Unrecognized flag " + e);
      System.exit(1);
    }

    if ( a.getParameter() != null && a.getParameter().isRequired() ) {
      System.out.println(String.format("Argument --%s requires a value.", e));
      System.exit(1);
    }

    a.use();
  }

  private void nullCheckArg ( final CliArgumentInterface a, final Object i, final List < String > v )
  {
    if ( null == a ) {
      System.out.println("Unrecognized Argument " + i);
      System.exit(1);
    }
    insertValues(a, v);
  }

  private void insertValues ( final CliArgumentInterface a, final List < String > values )
  {
    for ( final String s : values ) a.getParameter().parseValue(s);
    a.use();
  }
}

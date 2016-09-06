/*
 * Copyright 2016 Elizabeth Harper
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vulpine.util.cli;

import io.vulpine.util.cli.def.Argument;
import io.vulpine.util.cli.def.OperationMode;
import io.vulpine.util.cli.def.Parameter;
import io.vulpine.util.cli.def.Defined;

import java.util.*;
import java.util.Map.Entry;

public class MultiApplication extends AbstractApplication
{

  protected final Map < String, OperationMode > modes;

  public MultiApplication( final String[] args )
  {
    super(args);
    modes = new HashMap < String, OperationMode >();
  }

  public MultiApplication addAppMode( final OperationMode... c )
  {
    for ( final OperationMode m : c ) {
      modes.put(m.getName(), m);
    }

    return this;
  }

  @Override
  public MultiApplication addParameter( final Parameter... parameters )
  {
    for ( final Parameter p : parameters ) {
      this.parameters.offer(p);
    }

    return this;
  }

  @Override
  public void run()
  {
    final OperationMode                                     mode;
    final Queue < String >                                  params;
    final Iterator < Entry < String, List < String > > >    nvIt;
    final Iterator < Entry < Character, List < String > > > kvIt;
    final Iterator < String >                               nfIt;
    final Iterator < Character >                            kfIt;

    mode = modes.get(parser.getCliMode());

    if ( mode == null ) {
      if ( parser.hasFlag("help") ) {
        for ( final String l : getDefinition() ) {
          System.out.println(l);
        }
        return;
      }
      System.out.println("Unrecognized application mode.");
      return;
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

    for ( final Argument a : arguments.getArguments() ) {
      if ( a.isRequired() && !a.wasUsed() ) {
        System.out.println(String.format("Argument %s|%s is required.", a.getKey(), a.getName()));
        return;
      }
    }

    for ( final Argument a : mode.getArgumentSet().getArguments() ) {
      if ( a.isRequired() && !a.wasUsed() ) {
        System.out.println(String.format("Argument %s|%s is required.", a.getKey(), a.getName()));
        return;
      }
    }

    if ( helpFlag.wasUsed() ) {
      for ( final String l : getDefinition() ) {
        System.out.println(l);
      }
    } else {
      mode.run(parser);
    }

  }

  @Override
  public String[] getDefinition()
  {
    final Queue < String > lines = new LinkedList < String >();
    final String[]         out;

    for ( final OperationMode entry : this.modes.values() ) {
      for ( final String help : entry.getDefinition() ) {
        lines.offer(Defined.INDENT + help);
      }
      lines.offer("");
    }

    out = new String[lines.size()];
    for ( int i = 0; i < out.length; i++ ) {
      out[i] = lines.poll();
    }

    return out;
  }

  private void testArg( final String e, final OperationMode mode )
  {
    final Argument a = arguments.getArgument(e);
    testFlag(null == a ? mode.getArgumentSet().getArgument(e) : a, e);
  }

  private void testArg( final char e, final OperationMode mode )
  {
    final Argument a = arguments.getArgument(e);
    testFlag(null == a ? mode.getArgumentSet().getArgument(e) : a, e);
  }

  private void testArg( final String name, final List < String > values, final OperationMode mode )
  {
    final Argument a = arguments.getArgument(name);
    nullCheckArg(null == a ? mode.getArgumentSet().getArgument(name) : a, name, values);
  }

  private void testArg( final char key, final List < String > values, final OperationMode mode )
  {
    final Argument a = arguments.getArgument(key);
    nullCheckArg(null == a ? mode.getArgumentSet().getArgument(key) : a, key, values);
  }

  private void testFlag( final Argument a, final Object e )
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

  private void nullCheckArg( final Argument a, final Object i, final List < String > v )
  {
    if ( null == a ) {
      System.out.println("Unrecognized Argument " + i);
      System.exit(1);
    }
    insertValues(a, v);
  }

  private void insertValues( final Argument a, final List < String > values )
  {
    for ( final String s : values ) {
      a.getParameter().parseValue(s);
    }
    a.use();
  }
}

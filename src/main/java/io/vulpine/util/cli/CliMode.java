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
import io.vulpine.util.cli.def.Parameter;
import io.vulpine.util.cli.def.Defined;
import io.vulpine.util.cli.def.OperationMode;

import java.util.*;

public abstract class CliMode extends Common implements OperationMode
{
  /**
   * Parameters attached to this CLI Mode
   */
  protected final Queue < Parameter > parameters;

  /**
   * Arguments attached to this CLI Mode
   */
  protected final ArgumentSet arguments;

  public CliMode( final String n, final String d )
  {
    super(n, d);

    parameters = new LinkedList < Parameter >();
    arguments = new ArgumentSet();
  }

  @Override
  public CliMode addParameter( final Parameter... p )
  {
    for ( final Parameter q : p ) {
      parameters.offer(q);
    }

    return this;
  }

  @Override
  public CliMode addArgument( final Argument argument )
  {
    arguments.addArgument(argument);

    return this;
  }

  @Override
  public boolean hasUnfilledParam()
  {
    return null != parameters.peek();
  }

  @Override
  public CliMode parseParam( final String str )
  {
    parameters.poll().parseValue(str);

    return this;
  }

  @Override
  public ArgumentSet getArgumentSet()
  {
    return arguments;
  }

  @Override
  public String[] getDefinition()
  {
    final String[]      arg = this.arguments.getDefinition();
    final String[]      out = new String[arg.length + 3];
    final StringBuilder sb  = new StringBuilder(this.name);

    for ( final Parameter p : parameters ) {
      sb.append(" [").append(p.getName()).append(']');
    }

    out[0] = sb.toString();
    out[1] = Defined.INDENT + description;
    out[2] = "";

    for ( int i = 0; i < arg.length; i++ ) {
      out[i + 3] = Defined.INDENT + arg[i];
    }

    return out;
  }
}

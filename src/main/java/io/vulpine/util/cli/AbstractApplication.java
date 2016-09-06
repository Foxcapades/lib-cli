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
import io.vulpine.util.cli.def.ArgumentCollection;
import io.vulpine.util.cli.def.OperationMode;
import io.vulpine.util.cli.def.Parameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

abstract class AbstractApplication implements io.vulpine.util.cli.def.Application
{
  private final Collection < OperationMode > operationModes;

  protected final Argument < Void > helpFlag;

  protected final ArgumentSet arguments;

  protected final Queue < Parameter > parameters;

  protected final ArgumentParser parser;

  protected AbstractApplication( final char key, final String name, final String[] args )
  {
    arguments      = new ArgumentSet();
    parameters     = new LinkedList < Parameter >();
    parser         = new ArgumentParser(args, true);
    helpFlag       = new Flag(key, name, "Print help text for this application");
    operationModes = new ArrayList < OperationMode >();

    addArgument(helpFlag);
  }

  protected AbstractApplication( final String[] args )
  {
    this('\u0000', "help", args);
  }

  @Override
  public AbstractApplication addArgument( final Argument... arguments )
  {
    for ( final Argument arg : arguments )
      this.arguments.addArgument(arg);

    return this;
  }

  @Override
  public ArgumentCollection getArgumentSet()
  {
    return this.arguments;
  }

  @Override
  public Collection < OperationMode > getOperationModes()
  {
    return this.operationModes;
  }

  @Override
  public boolean hasUnfilledParameter()
  {
    final Iterator < Parameter > iterator = parameters.iterator();

    while ( iterator.hasNext() ) {
      if ( !iterator.next().isFilled() )
        return true;
    }

    return false;
  }

  @Override
  public void parseParameter( String parameterValue )
  {
    final Iterator < Parameter > iterator = parameters.iterator();

    while ( iterator.hasNext() ) {

      final Parameter param = iterator.next();

      if ( !param.isFilled() ) {
        param.parseValue(parameterValue);
        break;
      }
    }
  }
}

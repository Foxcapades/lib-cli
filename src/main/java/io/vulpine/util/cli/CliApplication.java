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

import io.vulpine.util.cli.def.CliApplicationInterface;
import io.vulpine.util.cli.def.CliArgumentInterface;
import io.vulpine.util.cli.def.CliParameterInterface;

import java.util.LinkedList;
import java.util.Queue;

abstract class CliApplication implements CliApplicationInterface
{
  protected final CliArgumentInterface< Void > helpFlag;

  protected final ArgumentSet arguments;

  protected final Queue< CliParameterInterface > parameters;

  protected final ArgumentParser                 parser;

  protected CliApplication ( final char key, final String name, final String[] args )
  {
    arguments = new ArgumentSet();
    parameters = new LinkedList< CliParameterInterface >();
    parser = new ArgumentParser(args, true);
    helpFlag = new CliFlag(key, name, "Print help text for this application");
    addArgument(helpFlag);
  }

  protected CliApplication( final String[] args )
  {
    this('\u0000', "help", args);
  }

  @Override
  public CliApplication addArgument ( final CliArgumentInterface... a )
  {
    for ( final CliArgumentInterface arg : a ) { arguments.addArgument(arg); }

    return this;
  }
}

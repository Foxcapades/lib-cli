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

import io.vulpine.util.cli.def.CliArgumentInterface;
import io.vulpine.util.cli.def.ArgumentSetInterface;
import io.vulpine.util.cli.def.HasHelpText;

import java.util.*;

public class ArgumentSet implements ArgumentSetInterface
{
  protected final Map < String, CliArgumentInterface > byName;

  protected final Map < Character, CliArgumentInterface > byKey;

  protected final Set < CliArgumentInterface > arguments;

  public ArgumentSet()
  {
    byKey = new HashMap < Character, CliArgumentInterface >();
    byName = new HashMap < String, CliArgumentInterface >();
    arguments = new HashSet < CliArgumentInterface >();
  }

  @Override
  public CliArgumentInterface getArgument( final String name )
  {
    return byName.get(name);
  }

  @Override
  public CliArgumentInterface getArgument( final char key )
  {
    return byKey.get(key);
  }

  @Override
  public Set < CliArgumentInterface > getArguments()
  {
    return this.arguments;
  }

  @Override
  public void addArgument( final CliArgumentInterface arg )
  {
    this.byKey.put(arg.getKey(), arg);
    this.byName.put(arg.getName(), arg);
    this.arguments.add(arg);
  }

  @Override
  public boolean hasArgument( final String name )
  {
    return byName.containsKey(name);
  }

  @Override
  public boolean hasArgument( final char key )
  {
    return byKey.containsKey(key);
  }

  @Override
  public String[] getHelpText()
  {
    final String[] out = new String[arguments.size() * 2 + 1];
    int i = 0;

    out[i++] = "Arguments:";

    for ( final CliArgumentInterface arg : this.arguments ) {
      for ( final String h : arg.getHelpText() ) { out[i++] = HasHelpText.INDENT + h; }
    }

    return out;
  }
}

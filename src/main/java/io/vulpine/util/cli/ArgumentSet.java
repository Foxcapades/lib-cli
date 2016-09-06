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
import io.vulpine.util.cli.def.Defined;

import java.util.*;

public class ArgumentSet implements ArgumentCollection
{
  protected final Map < String, Argument > byName;

  protected final Map < Character, Argument > byKey;

  protected final Set < Argument > arguments;

  public ArgumentSet()
  {
    byKey = new HashMap < Character, Argument >();
    byName = new HashMap < String, Argument >();
    arguments = new HashSet < Argument >();
  }

  @Override
  public Argument getArgument( final String name )
  {
    return byName.get(name);
  }

  @Override
  public Argument getArgument( final char key )
  {
    return byKey.get(key);
  }

  @Override
  public Set < Argument > getArguments()
  {
    return this.arguments;
  }

  @Override
  public void addArgument( final Argument arg )
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
  public String[] getDefinition()
  {
    final String[] out = new String[arguments.size() * 2 + 1];
    int i = 0;

    out[i++] = "Arguments:";

    for ( final Argument arg : this.arguments ) {
      for ( final String h : arg.getDefinition() ) { out[i++] = Defined.INDENT + h; }
    }

    return out;
  }

  @Override
  public boolean isEmpty()
  {
    return this.arguments.isEmpty();
  }
}

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

import io.vulpine.util.cli.def.CliParameterInterface;

public abstract class CliParameter < T > extends Common implements CliParameterInterface< T >
{
  protected T value;

  protected final boolean required;

  public CliParameter ( final String n, final String d, final boolean r )
  {
    super(n, d);
    this.required = r;
  }

  @Override
  public boolean isRequired ()
  {
    return required;
  }

  @Override
  public String[] getHelpText()
  {
    return new String[0];
  }

  @Override
  public T getValue ()
  {
    return value;
  }
}

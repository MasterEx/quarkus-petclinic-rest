/*
 * Copyright 2019 Periklis Ntanasis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.masterex.petclinic.util;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.eclipse.yasson.YassonProperties;
import org.eclipse.yasson.internal.JsonBindingBuilder;

/**
 *
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@Provider
public class JsonBConfiguration implements ContextResolver<Jsonb> {

    private final Jsonb jsonB;

    public JsonBConfiguration() {
        JsonbConfig config = new JsonbConfig();
        config.setProperty(YassonProperties.ZERO_TIME_PARSE_DEFAULTING, true);
        config.setProperty(JsonbConfig.DATE_FORMAT, "yyyy/MM/dd");
        jsonB = new JsonBindingBuilder().withConfig(config).build();
    }

    @Override
    public Jsonb getContext(Class type) {
        return jsonB;
    }

}

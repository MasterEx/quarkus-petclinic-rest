/*
 * Copyright 2019 Periklis Ntanasis
 * Copyright 2002-2013 the original author or authors.
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
package com.github.masterex.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Can be Cat, Dog, Hamster...
 *
 * @author Juergen Hoeller
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@Entity
@Table(name = "types")
public class PetType extends NamedEntity {

}

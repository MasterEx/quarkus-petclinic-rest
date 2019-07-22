/*
 * Copyright 2019 Periklis Ntanasis
 * Copyright 2018 the original author or authors.
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
package com.github.masterex.petclinic.repository.jpa;

import com.github.masterex.petclinic.model.User;
import com.github.masterex.petclinic.repository.UserRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@ApplicationScoped
public class JpaUserRepositoryImpl implements UserRepository {

    private final EntityManager em;

    @Inject
    public JpaUserRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(User user) {
        if (this.em.find(User.class, user.getUsername()) == null) {
            this.em.persist(user);
        } else {
            this.em.merge(user);
        }
    }

}

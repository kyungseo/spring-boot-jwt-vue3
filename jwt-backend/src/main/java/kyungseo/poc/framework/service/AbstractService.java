/* ============================================================================
 * KYUNGSEO.PoC > Development Templates for building Web Apps
 *
 * Copyright 2023 Kyungseo Park <Kyungseo.Park@gmail.com>
 * ----------------------------------------------------------------------------
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
 * ========================================================================= */

package kyungseo.poc.framework.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import kyungseo.poc.framework.persistence.IOperations;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Transactional
public abstract class AbstractService<T extends Serializable> implements IOperations<T> {

    // read - one
    @Override
    @Transactional(readOnly = true)
    public T findById(final long id) {
        return getDao().findById(id).orElse(null);
    }

    // read - all
    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

    @Override
    public Page<T> findPaginated(final int page, final int size) {
        return getDao().findAll(PageRequest.of(page, size));
    }

    @Override
    public T create(final T entity) {
        return getDao().save(entity);
    }

    @Override
    public T update(final T entity) {
        return getDao().save(entity);
    }

    @Override
    public void delete(final T entity) {
        getDao().delete(entity);
    }

    @Override
    public void deleteById(final long entityId) {
        getDao().deleteById(entityId);
    }

    protected abstract PagingAndSortingRepository<T, Long> getDao();

}

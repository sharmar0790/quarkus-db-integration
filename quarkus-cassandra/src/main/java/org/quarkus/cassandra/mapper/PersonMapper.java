package org.quarkus.cassandra.mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.quarkus.cassandra.dao.PersonDao;

@Mapper
public interface PersonMapper {

    @DaoFactory
    PersonDao personDao(@DaoKeyspace String keySpace, @DaoTable String table);
}

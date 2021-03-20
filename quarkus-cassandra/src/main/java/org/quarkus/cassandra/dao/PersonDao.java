package org.quarkus.cassandra.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import org.quarkus.cassandra.model.Person;

import java.util.Optional;

@Dao
public interface PersonDao {

    @Select(allowFiltering = true)
    PagingIterable<Person> getAllPersons();

    @Select(allowFiltering = true,customWhereClause = "name=:name")
    Optional<Person> getPersonsByName(@CqlName("name") final String name);

    @Insert
    void save(Person person);

    @Delete(entityClass = Person.class,
            customWhereClause = " id=:id")
    void deleteByName(@CqlName("id") final Integer id);

}

package com.example.dao.Impl;

import com.example.dao.MetricsDAO;
import com.example.dto.Metrics;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.MorphiaIterator;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ishaan
 */
@Repository
public class MetricsDAOImpl implements MetricsDAO {

    @Autowired
    Datastore datastore;

    public void create(Metrics metrics) {
        datastore.save(metrics);
    }

    public List<Metrics> getMetrics() {
        final Query<Metrics> query = datastore.createQuery(Metrics.class);
        final List<Metrics> listOfMetrics = query.asList();
        return listOfMetrics;
    }

    @Override
    public List<Metrics> getMetrics(Timestamp from, Timestamp to) {
        final MorphiaIterator<Metrics, Metrics> morphiaIterator = datastore.createQuery(Metrics.class).field("creationTimestamp").lessThan(to)
                .field("creationTimestamp").greaterThan(from).fetch();
        List<Metrics> resultList = new ArrayList<>();
        Iterator<Metrics> metricsIterator = morphiaIterator.iterator();
        while (metricsIterator.hasNext()) {
            Metrics metrics = metricsIterator.next();
            resultList.add(metrics);
        }
        return resultList;
    }
}

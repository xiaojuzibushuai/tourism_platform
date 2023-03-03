package cn.xiaojuzi.travel.search.service.impl;

import cn.xiaojuzi.travel.search.domain.TravelEs;
import cn.xiaojuzi.travel.search.repository.TravelEsRepository;
import cn.xiaojuzi.travel.search.service.ITravelEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * xiaojuzi
 */
@Service
public class TravelEsServiceImpl implements ITravelEsService {

    @Autowired
    private TravelEsRepository repository;

    @Override
    public void save(TravelEs travelEsEs) {
        //travelEsEs.setId(null);
        repository.save(travelEsEs);
    }

    @Override
    public void update(TravelEs travelEsEs) {
        repository.save(travelEsEs);
    }

    @Override
    public TravelEs get(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<TravelEs> list() {
        List<TravelEs> list = new ArrayList<>();
        Iterable<TravelEs> all = repository.findAll();
        all.forEach(a -> list.add(a));
        return list;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}

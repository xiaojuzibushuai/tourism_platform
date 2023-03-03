package cn.xiaojuzi.travel.search.service.impl;

import cn.xiaojuzi.travel.search.domain.DestinationEs;
import cn.xiaojuzi.travel.search.repository.DestinationEsRepository;
import cn.xiaojuzi.travel.search.service.IDestinationEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * xiaojuzi
 */
@Service
public class DestinationEsServiceImpl implements IDestinationEsService {

    @Autowired
    private DestinationEsRepository repository;

    @Override
    public void save(DestinationEs destinationEsEs) {
        //destinationEsEs.setId(null);
        repository.save(destinationEsEs);
    }

    @Override
    public void update(DestinationEs destinationEsEs) {
        repository.save(destinationEsEs);
    }

    @Override
    public DestinationEs get(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<DestinationEs> list() {
        List<DestinationEs> list = new ArrayList<>();
        Iterable<DestinationEs> all = repository.findAll();
        all.forEach(a -> list.add(a));
        return list;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}

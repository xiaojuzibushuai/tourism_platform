package cn.xiaojuzi.travel.search.service.impl;

import cn.xiaojuzi.travel.search.domain.UserInfoEs;
import cn.xiaojuzi.travel.search.repository.UserInfoEsRepository;
import cn.xiaojuzi.travel.search.service.IUserInfoEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * xiaojuzi
 */
@Service
public class UserInfoEsServiceImpl implements IUserInfoEsService {

    @Autowired
    private UserInfoEsRepository repository;

    @Override
    public void save(UserInfoEs userInfoEsEs) {
        //userInfoEsEs.setId(null);
        repository.save(userInfoEsEs);
    }

    @Override
    public void update(UserInfoEs userInfoEsEs) {
        repository.save(userInfoEsEs);
    }

    @Override
    public UserInfoEs get(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<UserInfoEs> list() {
        List<UserInfoEs> list = new ArrayList<>();
        Iterable<UserInfoEs> all = repository.findAll();
        all.forEach(a -> list.add(a));
        return list;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}

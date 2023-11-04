package com.haifeiedu.service.impl;

import com.haifeiedu.entity.Monster;
import com.haifeiedu.haifeispringmvc.annotation.Controller;
import com.haifeiedu.haifeispringmvc.annotation.Service;
import com.haifeiedu.service.MonsterService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonsterServiceImpl implements MonsterService {
    @Override
    public List<Monster> listMonster() {
        ArrayList<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(100, "cow king", "balance umbrella", 400));
        monsters.add(new Monster(200, "old cat", "catch mouses", 400));
        return monsters;
    }
}

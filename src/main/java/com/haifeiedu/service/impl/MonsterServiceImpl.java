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

    @Override
    public List<Monster> findMonsterByName(String name) {
        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(100, "cow king", "balance umbrella", 400));
        monsters.add(new Monster(200, "old cat", "catch mouses", 200));
        monsters.add(new Monster(300, "elephant man", "carry wood", 100));
        monsters.add(new Monster(400, "yellow cloth", "smoke", 300));
        monsters.add(new Monster(500, "bai man", "beauty method", 800));

        List<Monster> monstersFound = new ArrayList<>();
        for (Monster monster : monsters) {
            if (monster.getName().contains(name)) {
                monstersFound.add(monster);
            }
        }
        return monstersFound;
    }

    @Override
    public boolean login(String name) {

        // in actual situation, we will go to database to validate.
        if ("white sprite".equals(name)) {
            return true;
        }
        return false;
    }


}

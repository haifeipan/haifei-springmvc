package com.haifeiedu.service;

import com.haifeiedu.entity.Monster;

import java.util.List;

public interface MonsterService {

    List<Monster> listMonster();

    List<Monster> findMonsterByName(String name);

    boolean login(String name);

}

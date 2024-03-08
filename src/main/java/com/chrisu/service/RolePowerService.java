package com.chrisu.service;



import com.chrisu.POJO.RolePower;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RolePowerService {

  void save(RolePower rolePower);

  void delete(int rolePowerId);

  void update(RolePower rolePower);

  List<RolePower> findAll();

  RolePower findById(int rolePowerId);
}

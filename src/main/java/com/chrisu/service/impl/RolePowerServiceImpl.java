package com.chrisu.service.impl;



import com.chrisu.POJO.RolePower;
import com.chrisu.mapper.RolePowerMapper;
import com.chrisu.service.RolePowerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePowerServiceImpl implements RolePowerService {

  @Autowired
  private RolePowerMapper rolePowerMapper;

  @Override
  public void save(RolePower rolePower) {
    rolePowerMapper.save(rolePower);
  }

  @Override
  public void delete(int rolePowerId) {
    rolePowerMapper.delete(rolePowerId);
  }

  @Override
  public void update(RolePower rolePower) {
    rolePowerMapper.update(rolePower);
  }

  @Override
  public List<RolePower> findAll() {
    return rolePowerMapper.findAll();
  }

  @Override
  public RolePower findById(int rolePowerId) {
    return rolePowerMapper.findById(rolePowerId);
  }
}

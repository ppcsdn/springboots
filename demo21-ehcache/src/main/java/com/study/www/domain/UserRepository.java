package com.study.www.domain;

import org.springframework.cache.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 描述:
 *
 * @outhor Administrator
 * @create 2017-11-14 05:48
 */
@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<User,Long> {
    User findByName(String name);

    @Cacheable(key = "#p0")
    User findByAccount(String account);

    @CachePut(key = "#p0")
    @Modifying
    @Query("update User u set u.name=?name where u.account=?account")
    void updateNameByAccount(@Param("account") String account,@Param("name") String name);

    @Cacheable(key = "#p0", condition = "#p0.length()<4")
    User findByNameAndAccount(String name, String account);

    @Query(" from User u where u.id=:id")
    User findById(Long id);

    @CacheEvict(key = "#p0")
    void removeByAccount(String account);

    @Cacheable(key = "#p1", unless = "#result.name.length() >= 4")
    User findByIdAndAccount(Long id, String account);

}
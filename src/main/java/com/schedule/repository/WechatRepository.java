package com.schedule.repository;

import com.schedule.entity.Wechat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dell on 2017/8/14.
 */
@Repository
@Transactional
public interface WechatRepository extends JpaRepository<Wechat,Integer> {
    Wechat findAllByThirdsession(String thirdsession);
    Wechat findAllByUnionId(String unionId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update wechat wechat0 set wechat0.encryptedData =?1,wechat0.iv=?2,wechat0.nickname=?3,wechat0.rawDate=?4," +
            "wechat0.signature=?5,wechat0.unionId=?6 where wechat0.thirdsession=?7",nativeQuery = true)
    void updateWechatInfoByThirdsession(String encryptedData,String iv,String nickname,String rawDate,String signature,String unionId,String thirdsession);

}

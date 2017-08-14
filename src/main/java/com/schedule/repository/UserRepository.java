package com.schedule.repository;

import com.schedule.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dell on 2017/7/21.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users,Integer> {
   Users findAllByName(String name);
   Users findAllByStudentid(String studentid);
   Users findAllByTel(String tel);
   Users findAllByUserid(Integer userid);
   Users findAllByWechatid(String unionId);

   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value = "update users users0 set users0.tel =?1 where users0.userid=?2",nativeQuery = true)
   void updateTelByUserid(String tel, Integer userid);

   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value = "update users users0 set users0.wechatid =?1 where users0.userid=?2",nativeQuery = true)
   void updateUnionIdByUserid(String unionId, Integer userid);

   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value = "update users users0 set users0.param2 =?1,users0.param3=?2 where users0.userid=?3",nativeQuery = true)
   void updateUDIDAndTimeByUserid(String UDID,String settime, Integer userid);

   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value = "update users users0 set users0.college =?1 ,users0.major=?2,users0.param1=?3,users0.nickname=?4 where users0.userid=?5",nativeQuery = true)
   void updateUserInformationByUserid(String college, String major, String param1, String nickname, Integer userid);


}

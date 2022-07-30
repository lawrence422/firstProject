package com.intern.firstproject.mapper;

import com.intern.firstproject.mapper.sql.UserProfileSql;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserProfileMapper {
    @SelectProvider(type = UserProfileSql.class,method = "checkUsernameExistSql")
    int checkUsernameExist(String username);

    @InsertProvider(type = UserProfileSql.class,method = "insertUserSql")
    int insertUser(String username,String password);

    @InsertProvider(type = UserProfileSql.class,method = "insertEmailSql")
    int insertEmail(String username,String userEmail);

    @SelectProvider(type = UserProfileSql.class,method="getPasswordSql")
    String getPassword(String username);

    @DeleteProvider(type = UserProfileSql.class,method = "deleteUserSql")
    int deleteUser(String username);

    @SelectProvider(type = UserProfileSql.class,method = "getAuthoritySql")
    String getAuthority(String username);

}

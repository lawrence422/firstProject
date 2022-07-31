package com.intern.firstproject.mapper;

import com.intern.firstproject.mapper.sql.UserProfileSql;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserProfileMapper {
    @SelectProvider(type = UserProfileSql.class,method = "checkUsernameExistSql")
    int checkUsernameExist(String username);

    @InsertProvider(type = UserProfileSql.class,method = "insertUserSql")
    int insertUser(String username,String password);

    @UpdateProvider(type = UserProfileSql.class,method = "updateEmailSql")
    int updatetEmail(String username, String userEmail);

    @SelectProvider(type = UserProfileSql.class,method="getPasswordSql")
    String getPassword(String username);

    @DeleteProvider(type = UserProfileSql.class,method = "deleteUserSql")
    int deleteUser(String username);

    @SelectProvider(type = UserProfileSql.class,method = "getAuthoritySql")
    String getAuthority(String username);

    @InsertProvider(type = UserProfileSql.class,method = "updateAuthoritySql")
    int updateAuthority(String username, String authority);

}

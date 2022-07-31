package com.intern.firstproject.mapper.sql;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;


public class UserProfileSql {
    public String checkUsernameExistSql(String username) {
        return new SQL() {{
            SELECT("count(*)");
            FROM("fp_user");
            WHERE("user_name=#{username}");
            LIMIT("1");
        }}.toString();
    }

    public String insertUserSql(String username, String password){
        return new SQL(){{
            INSERT_INTO("fp_user");
            VALUES("user_name, user_password","#{username}, #{password}");
        }}.toString();
    }

    public String getPasswordSql(String username){
        return new SQL(){{
            SELECT("user_password");
            FROM("fp_user");
            WHERE("user_name=#{username}");
        }}.toString();
    }

    public String deleteUserSql(String username){
        return new SQL(){{
            DELETE_FROM("fp_user");
            WHERE("user_name=#{username}");
        }}.toString();
    }

    public String getAuthoritySql(String username){
        return new SQL(){{
            SELECT("user_authority");
            FROM("fp_user");
            WHERE("user_name=#{username}");
        }}.toString();
    }

    public String updateEmailSql(String username,String userEmail){
        return new SQL(){{
            UPDATE("fp_user");
            SET("user_email=#{userEmail}");
            WHERE("user_name=#{username}");
        }}.toString();
    }

    public String updateAuthoritySql(String username, String authority){
        return new SQL(){{
            UPDATE("fp_user");
            SET("user_authority=#{authority}");
            WHERE("user_name=#{username}");
        }}.toString();
    }
}

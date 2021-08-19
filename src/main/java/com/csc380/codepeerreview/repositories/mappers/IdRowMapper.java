package com.csc380.codepeerreview.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class IdRowMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet rs, int arg1) throws SQLException {
        return rs.getInt("id") + "";
    }

}

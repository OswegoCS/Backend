package com.csc380.codepeerreview.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.csc380.codepeerreview.responses.GetCommentLikesResponse;

public class GetLikesRowMapper implements RowMapper<GetCommentLikesResponse> {

    @Override
    public GetCommentLikesResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        GetCommentLikesResponse response = new GetCommentLikesResponse();
        response.setCommentId(rs.getInt("comment_id"));
        response.setUserId(rs.getInt("user_id"));
        return response;
    }

}

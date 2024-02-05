package com.example.webhookhandlingsystem.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

@Mapper
public interface WebhookDataMapper {

    @Insert("INSERT INTO webhook_data (id, type, asset, cents, status, updated_at) " +
            "VALUES (#{id}, #{type}, #{asset}, #{cents}, #{status}, #{updated_at})")
    int insert(WebhookData webhookData);

    @Select("SELECT id, type, asset, cents, status, updated_at FROM webhook_data WHERE id = #{id}")
    Optional<WebhookData> getById(String id);

    @Update("UPDATE webhook_data SET type=#{type}, asset=#{asset}, cents=#{cents}, status=#{status}, updated_at=#{updated_at} WHERE id=#{id}")
    int update(WebhookData webhookData);

}
